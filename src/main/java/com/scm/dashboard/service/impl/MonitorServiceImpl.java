package com.scm.dashboard.service.impl;

import com.scm.dashboard.constant.CommonConstant;
import com.scm.dashboard.persistence.domain.*;
import com.scm.dashboard.schedulers.ThreadPoolManager;
import com.scm.dashboard.service.*;
import com.scm.dashboard.service.dto.BuildDTO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 监控Job功能的相关service.
 * @author l58wang
 *
 */
@Service
public class MonitorServiceImpl implements MonitorService {

	private static final Logger logger = LoggerFactory.getLogger(MonitorServiceImpl.class);
	
	private static final Integer CLOSED = 0;
	
	@Autowired
	private BranchService branchService;

	@Autowired
	private BuildService buildService;
	
	@Autowired
	private IssueService issueService;

	@Autowired
	private FetchBuildsService fetchBuildsService;

	@Autowired
	private JenkinsLogService jenkinsLogService;

	@Autowired
	private PipelineService pipelineService;

	@Autowired
	private JobService jobService;

	@Autowired
	private JobSatgeService jobSatgeService;

	@Autowired
	private ThreadPoolManager threadPoolManager;

	public class FetchBuilds implements Runnable {

		private MonitorService monitorService;
		private TJob job;

		public FetchBuilds(MonitorService monitorService, TJob job) {
			this.monitorService = monitorService;
			this.job = job;
		}

		@Override
		public void run() {
			monitorService.fetchJobBuilds(job);
		}
	}

	/**
	 * 更新builds
	 */
	@Override
	public void fetchBuilds(List<TJob> jobs) {
		if(CollectionUtils.isEmpty(jobs)){
			return;
		}
	    jobs.forEach(job -> threadPoolManager.putThread(new FetchBuilds(this,job)));

//		jobs = jobService.getJobByBranchId(129l);
//		for(TJob job:jobs) {
//			fetchJobBuilds(job);
//		}
//		fetchJobBuilds(jobService.getJobById(324l));
	}
	

	@Override
	public void fetchJobBuilds(TJob job) {
		/**1. Get the baseTime from the build startTime*/
		long baseTime = getBaseTime(job);
		/**2. Fetch the new builds from jenkins server*/
		List<BuildDTO> buildDtos = fetchBuildsService.getBuilds(job, baseTime);
		/**3. Save these builds */
		saveBuilds(buildDtos,job);
	}

	private void saveBuilds(List<BuildDTO> buildDtos, TJob job) {
		Long jobId = job.getId();
		//1. Check
		if(CollectionUtils.isEmpty(buildDtos)){
			logger.info("There's no build update for jobId: " + jobId);
			return;
		}
		logger.info("There are new builds update for jobId: " + jobId + " Total: " + buildDtos.size());

		//2. save or update the build info
		for (BuildDTO buildDto : buildDtos) {
			try {
				TBuild build = buildService.findBuildByJobIdAndNumber(jobId, buildDto.getNumber());
				if (null == build || (null != build.getStartTimestamp() && !buildDto.getTimestamp().equals(build.getStartTimestamp()))) {
					build = buildService.toTBuild(buildDto,job,null);
				}else{
					build = buildService.toTBuild(buildDto,job,build);
				}
				List<Long> branchList = branchService.getBranchIdsForBuild(build.getVersion(),jobId);
				if (CommonConstant.BUILD_RESULT_FAILURE.equals(build.getResult())) {
					build = buildService.saveBuild(build);
					if (job.isParseIssue() && null == build.getIssueId()) {
						Long branchId = 0l;
						if(branchList.size() == 1){
							branchId = branchList.get(0);
						}
						try {
							addJenkinsLogForParse(build, job, branchId);
						}catch (Exception e){
							logger.error("add jenkins log failure",e);
						}
					}
				} else {
					build = buildService.saveBuild(build);
					if (!CommonConstant.BUILD_RESULT_ABORTED.equals(build.getResult())) {
						job.setIsAttach(0); // the issue in the branch is no need to set top
						jobService.save(job);
						
						// auto close flag is open
						if(job.isAutoClose()){
							logger.info("the job has opened the auto-close-issue flag... jobId="+ jobId);
							//if the current build result in this job is successful, the status of issues related to this job turn to be closed
							if(CommonConstant.BUILD_RESULT_SUCCESS.equals(build.getResult())){
								logger.info("the result of lastest build of job is successful, close all the issues under this job... jobId="+ jobId);
								List<Long> issueIds = buildService.findIssueIdsByJobId(jobId);
								for(Long issueId: issueIds){
									TIssues issue = issueService.findOne(issueId);
									// the status of current issue is not closed
									if(issue !=null && issue.getStatus() != CLOSED){
										issue.setStatus(CLOSED); // close the issue
										issue.setCloseTime(new Date()); //set the close time
										issue.setFollowUp("The issue is closed automatically"); // set the description in follow-up
										issueService.save(issue);
									}
								}
							}
						}
					}
				}
				build.setUpstreamUrl(buildDto.getUpstreamUrl());
				build.setUpstreamBuildNum(buildDto.getUpstreamBuild());
				build.setUnifiedCommitId(buildDto.getUnifiedCommitId());
				pipelineService.handlePipeFlow(build, job,branchList);

				logger.info("Save build number: " + buildDto.getNumber() + " for jobId: " + jobId);
			}catch (Throwable  e){
				logger.error("Fail to save build number: " + buildDto.getNumber() + " for jobId: " + jobId,e);
			}
		}

	}

	public List<TJobBranchRel> getCommonJob(Long jobId){
		return jobService.getJobBranchRels(jobId);
	}

	public boolean isCommonJob(Long jobId){
		List<TJobBranchRel> commonJobList = jobService.getJobBranchRels(jobId);
		if(null != commonJobList && commonJobList.size()>1){
			return true;
		}
		return false;
	}

	public List<TJobStage> getJobStage(Long jobId){
		return jobSatgeService.findByJobId(jobId);
	}




    /**
     *
     * @param job
     * @return
     */
	private Long getBaseTime(TJob job){
		//The latest finished build
		TBuild lfBuild = buildService.getLatestFinishBuildByJob(job.getId());
		//The earliest in building status build
		TBuild lbBuild = buildService.findEarliestInBuildingBuildByJob(job.getId());
		Long baseTime = 0l;
		if( lfBuild !=null){
			 Long lfTime = getStartTime(lfBuild);
			//if the start time of earliest in building status build is less than finished build's ,then use the in building one.
			if(lbBuild != null) {
                Long lbTime =getStartTime(lbBuild);
				baseTime = lbTime < lfTime ? lbTime : lfTime;
			}else{
				baseTime =lfTime;
			}
		}
		return baseTime;
	}

	private Long getStartTime(TBuild build ){
		//if the startTimestamp is null use startTime
		Long buildStartTimestamp = build.getStartTimestamp();
		return null == buildStartTimestamp? build.getStartTime().getTime():buildStartTimestamp;
	}

	

	private void addJenkinsLogForParse(TBuild build,TJob job,Long branchId){
		//getBuildWithIssueById();
		if(null == build){
			return;
		}
		Long issueId = build.getIssueId();
		if (null != issueId && issueId > 0) {
			return;
		}
		jenkinsLogService.addJenkinsLog(build,job,branchId);
		logger.info("Add jenkins log for build:" + build.getNumber() + " jobId:" + job.getId() + " to t_jenkins_log table for parse");
	}

}
