package com.scm.dashboard.service.impl;

import com.scm.dashboard.constant.CommonConstant;
import com.scm.dashboard.persistence.domain.*;
import com.scm.dashboard.persistence.repo.JobStageRepository;
import com.scm.dashboard.persistence.repo.PipeFlowDetailRepository;
import com.scm.dashboard.persistence.repo.PipeFlowRepository;
import com.scm.dashboard.service.*;
import com.scm.dashboard.service.dto.BuildDTO;
import com.scm.dashboard.service.dto.JobBuild;
import com.scm.dashboard.utils.JenkinsUrlUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by amqu on 2017/6/23.
 */
@Service
public class PipelineServiceImpl implements PipelineService {

    private static final Logger logger = LoggerFactory.getLogger(PipelineService.class);

    @Autowired
    private PipeFlowDetailRepository pipeFlowDetailRepository;

    @Autowired
    private PipeFlowRepository pipeFlowRepository;

    @Autowired
    private JobStageRepository jobStageRepository;

    @Autowired
    private JobService jobService;

    @Autowired
    private BuildService buildService;

    @Autowired
    private BranchService branchService;

    @Autowired
    private JenkinsService jenkinsService;
    
    @Autowired
    private MonitorService monitorService;
    
    @Autowired
	private RedisTemplate<String, String> redisTemplate;

    /**
     * @param build
     * @param job
     * @param branchList
     */
    @Override
    public void handlePipeFlow(TBuild build, TJob job, List<Long> branchList) {
        List<Long> branchIds = pipeFlowDetailRepository.findPipeFlowBranchIds(build.getId());
        if (null != branchIds && branchIds.containsAll(branchList)) {
            return;
        }
        if(CollectionUtils.isEmpty(branchList)){
            return;
        }
        handlePipeFlow(new JobBuild(job,build,branchList));
    }

    private List<TPipeFlow> handlePipeFlow(JobBuild jobBuild) {
        TBuild build = jobBuild.getBuild();
        String upstreamUrl = build.getUpstreamUrl();
        Long upstreamBuildNum = build.getUpstreamBuildNum();
        /** 1. save as a new pipe line if this build is a trigger build for some branches.*/
        if (upstreamUrl == null || upstreamBuildNum == null) {
            return saveTriggerBuild(jobBuild);
        }
        /**2. find the pipe line which belongs to by search the upstream builds */
        else {
            //1. Get the upstream build
            List<TPipeFlow> pipeFlowList = new ArrayList<>();
            JobBuild upstreamJobBuild = getUpstreamBuild(upstreamUrl,upstreamBuildNum,jobBuild.getJob());
            if(null == upstreamJobBuild){
                logger.info("Can't find pipeline for build num : "+ build.getNumber() +" job id: "+build.getJobId());
                return pipeFlowList;
            }
            if(upstreamJobBuild.isTriggerBuild()){
                return saveTriggerBuild(jobBuild);
            }
            TBuild upstreamBuild = upstreamJobBuild.getBuild();
            //2. Check the upstream build
            if (null != upstreamBuild && upstreamBuild.getId() > 0) {
                //if the upstream build has been stored
                List<TPipeFlowDetail> upstreamPipeFlowDetailList = pipeFlowDetailRepository.findPipeFlow(upstreamBuild.getId());
                //get the pipe flows for the build id
                if (CollectionUtils.isNotEmpty(upstreamPipeFlowDetailList)) {
                    Set<Long> pipelineBranchIdSet = new HashSet<>();
                    for (TPipeFlowDetail pipeFlowDetail : upstreamPipeFlowDetailList) {
                        TPipeFlow pipeFlow = pipeFlowRepository.findOne(pipeFlowDetail.getPipeFlowId());
                        if (null != pipeFlow) {
                            pipeFlowList.add(pipeFlow);
                            pipelineBranchIdSet.add(pipeFlow.getBranchId());
                        }
                    }
                    //判断上游job的build是否已经全部保存该build所对应的branch id
                    addPipeFlowList(upstreamJobBuild,pipelineBranchIdSet,pipeFlowList);

                }
                //go on search the upstream build
                else{
                    pipeFlowList = handlePipeFlow(upstreamJobBuild);
                }
                //2.该build对应的branch Id 与上游job对应的branch ID不一致,即某几个branch id，上游build没有。
                addPipeFlowList(jobBuild,upstreamJobBuild.getBranchIdList(),pipeFlowList);

                //3.save this time build info
                saveNewPipeFlowDetailList(jobBuild, pipeFlowList);
                
                //4.If this job is publish job which connect jobs by svn/git
                
            }
            return pipeFlowList;
        }
    }

    private void addPipeFlowList(JobBuild jobBuild,Collection<Long> filterBranch,List<TPipeFlow> pipeFlowList){
        Set<Long> noBranchIds = new HashSet<>();
        if(null == jobBuild || null == jobBuild.getBranchIdList()){
            return;
        }
        for (Long branchId : jobBuild.getBranchIdList()) {
            if (filterBranch.contains(branchId)) {
                continue;
            }
            noBranchIds.add(branchId);
        }
        if (CollectionUtils.isNotEmpty(noBranchIds)) {
            jobBuild.setBranchIdList(new ArrayList<>(noBranchIds));
            List<TPipeFlow> newPipeFlowList = saveTriggerBuild(jobBuild);
            pipeFlowList.addAll(newPipeFlowList);
        }
    }

    private List<TPipeFlow> saveTriggerBuild(JobBuild jobBuild){
        List<TPipeFlow> pipeFlowList = new ArrayList<>();
        TBuild triggerBuild = jobBuild.getBuild();
        TJob triggerJob = jobBuild.getJob();
        List<Long> branchIdList = jobBuild.getBranchIdList();
        for(Long branchId : branchIdList) {
            TJobStage jobStage = jobStageRepository.getByBranchIdAndJobId(branchId, triggerJob.getId());
            if (null != jobStage) {
                TPipeFlow pipeFlow = null;
                pipeFlow = handleSpecialSubscribeJob(jobBuild, jobStage);
                if (null == pipeFlow) {
                	pipeFlow = saveNewPipeFlow(triggerBuild, triggerJob, branchId);
                }
                saveNewPipeFlowDetail(jobBuild, pipeFlow,jobStage);
                pipeFlowList.add(pipeFlow);
            }
        }
        return pipeFlowList;
    }


    private JobBuild getUpstreamBuild(String upstreamUrl,Long upstreamBuildNum,TJob job){
        String jobName = upstreamUrl.substring(upstreamUrl.lastIndexOf("job/") + 4, upstreamUrl.length() - 1);
        if(StringUtils.isBlank(jobName)){
            return null;
        }
        TJob upstreamJob = jobService.findByJobNameAndProjectId(jobName, job.getProjectId(),job.getServer().getId());
        if (null == upstreamJob) {
            BuildDTO buildDto;
            try {
                String jenkinsBuildUrl = JenkinsUrlUtil.getJenkinsBuildURL(job, upstreamBuildNum, jobName);
                buildDto = jenkinsService.getBuildInfo(jenkinsBuildUrl);
            }catch (Exception e){
                return null;
            }
            if(StringUtils.isBlank(buildDto.getUpstreamUrl()) || buildDto.getUpstreamBuild() == null){
                JobBuild upstreamJobBuild = new JobBuild();
                upstreamJobBuild.setTriggerBuild(true);
                return upstreamJobBuild;
            }
            return getUpstreamBuild(buildDto.getUpstreamUrl(),buildDto.getUpstreamBuild(),job);
        }

        TBuild upstreamBuild = buildService.findBuildByJobIdAndNumber(upstreamJob.getId(), upstreamBuildNum);
        if(null == upstreamBuild) {
            try {
                String jenkinsBuildUrl = JenkinsUrlUtil.getJenkinsBuildURL(upstreamJob, upstreamBuildNum);
                BuildDTO buildDto = jenkinsService.getBuildInfo(jenkinsBuildUrl);
                upstreamBuild = buildService.toTBuild(buildDto, upstreamJob,null);
                upstreamBuild = buildService.saveBuild(upstreamBuild);
            }catch (Exception e) {
                upstreamBuild = buildService.findBuildByJobIdAndNumber(upstreamJob.getId(), upstreamBuildNum);
            }
        }
        // get the branch IDs for the build id
        if(null != upstreamBuild) {
            List<Long> upstreamJobBranchList = branchService.getBranchIdsForBuild(upstreamBuild.getVersion(), upstreamBuild.getJobId());
            return new JobBuild(upstreamJob,upstreamBuild,upstreamJobBranchList);
        }
        return null;
    }


    private TPipeFlow saveNewPipeFlow(TBuild triggerBuild, TJob triggerJob,Long branchId){
        TPipeFlow pipeFlow = pipeFlowRepository.getTPipeFlow(triggerBuild.getId(),branchId);
        if (null != pipeFlow) {
            return pipeFlow;
        } else {
            pipeFlow = new TPipeFlow(triggerBuild.getId(), triggerJob.getId(), branchId,
                    triggerJob.getProjectId(), triggerBuild.getStartTime());
            try {
                pipeFlow = pipeFlowRepository.save(pipeFlow);
            } catch (Exception e) {
                pipeFlow = pipeFlowRepository.getTPipeFlow(triggerBuild.getId(),branchId);
            }
            return pipeFlow;
        }
    }

    private void saveNewPipeFlowDetailList(JobBuild jobBuild, List<TPipeFlow> pipeFlowList){
        if(CollectionUtils.isEmpty(pipeFlowList)){
            return;
        }
        Map<Long,TPipeFlow> flowMap = new HashMap<>();
        for(TPipeFlow pipeFlow : pipeFlowList){
            flowMap.put(pipeFlow.getBranchId(),pipeFlow);
        }
        List<Long> branchIdList = jobBuild.getBranchIdList();
        for(Long branchId : branchIdList){
            TPipeFlow pipeFlow = flowMap.get(branchId);

            TJob flowJob = jobBuild.getJob();
            TBuild flowBuild = jobBuild.getBuild();
            long flowBuildId = flowBuild.getId();
            if(null != pipeFlowDetailRepository.findPipeFlow(flowBuildId,branchId)) {
                continue;
            }
            TJobStage jobStage = jobStageRepository.getByBranchIdAndJobId(branchId, flowJob.getId());
            if (null != jobStage) {
                try {
                    TPipeFlowDetail newPipeFlowDetail = new TPipeFlowDetail(flowBuildId, flowBuild.getNumber(), flowJob.getId(),
                            branchId, flowJob.getProjectId(), pipeFlow.getId(), jobStage.getId(), jobStage.isMetricsStatisticsFlag());
                    pipeFlowDetailRepository.save(newPipeFlowDetail);
                    handleSpecialPublishJob(jobBuild, jobStage, pipeFlow);
                }catch (Exception e){
                	logger.error(e.getMessage());
                }
            }
        }
    }

    private void saveNewPipeFlowDetail(JobBuild jobBuild, TPipeFlow pipeFlow,TJobStage jobStage){
        TJob flowJob = jobBuild.getJob();
        TBuild flowBuild = jobBuild.getBuild();
        try {
            TPipeFlowDetail newPipeFlowDetail = new TPipeFlowDetail(flowBuild.getId(), flowBuild.getNumber(), flowJob.getId(),
                    pipeFlow.getBranchId(), flowJob.getProjectId(), pipeFlow.getId(), jobStage.getId(), jobStage.isMetricsStatisticsFlag());
            pipeFlowDetailRepository.save(newPipeFlowDetail);
        }catch (Exception e){

        }
    }
    
    /**
     * cover pipeline connected by svn/git as subscribe side
     * @param jobBuild
     * @param jobStage
     * @return
     */
    private TPipeFlow handleSpecialSubscribeJob(JobBuild jobBuild, TJobStage jobStage) {
    	TPipeFlow pipeFlow = null;
    	if(jobStage.isSubscribeJob()) {
    		TBuild flowBuild = jobBuild.getBuild();
    		if (null == jobStage.getUpStream()) {
    			logger.error("subscribed Job must configue upstream job");
    		}else if(null == flowBuild.getUnifiedCommitId()) {
    			logger.error("unified CommitId can't be null");
    		}else{
    			Long upStreamJobStageId = parseUpstreamJob(jobStage.getUpStream());
    			String key = upStreamJobStageId + flowBuild.getUnifiedCommitId();
    			pipeFlow = getCommitId(key);
        		if(null == pipeFlow) {
        			Long upStreamJobId = jobStageRepository.findOne(upStreamJobStageId).getJobId();
            		TJob upStreamJob = jobService.getJobById(upStreamJobId);
            		monitorService.fetchJobBuilds(upStreamJob);
        		}
        		pipeFlow = getCommitId(key);
        		deleteCommitId(key);
    		}
    	}
		return pipeFlow;
    }
    
    /**
     * cover pipeline connected by svn/git as publish side
     * @param jobBuild
     * @param jobStage
     * @param pipeFlow
     */
    private void handleSpecialPublishJob(JobBuild jobBuild, TJobStage jobStage, TPipeFlow pipeFlow) {
    	if(jobStage.isPublishJob()) {
    		TBuild flowBuild = jobBuild.getBuild();
    		if(null == flowBuild.getUnifiedCommitId()) {
    			logger.error("unified CommitId can't be null");
    		}else {
    			String key = jobStage.getId() + flowBuild.getUnifiedCommitId();
    			setCommitId(key, pipeFlow);
    		}
    	}
    }
    
    private TPipeFlow getCommitId(String key) {
    	return (TPipeFlow) redisTemplate.opsForHash().get(CommonConstant.COMMITINFO, key);
    }
    
    public void setCommitId(String key, TPipeFlow value) {  
    	redisTemplate.opsForHash().put(CommonConstant.COMMITINFO, key, value);
    }
    
    private void deleteCommitId(String key) {
    	redisTemplate.opsForHash().delete(CommonConstant.COMMITINFO, key);
    }
    
    private Long parseUpstreamJob(String upStream) {
    	if(null != upStream && upStream.length() > 2) {
    		upStream = upStream.substring(1, upStream.length()-1);
    	}
    	return Long.valueOf(upStream);
    }
}
