package com.scm.dashboard.utils;

import com.scm.dashboard.persistence.domain.*;
import com.scm.dashboard.persistence.repo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.ManyToOne;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by amqu on 2017/7/31.
 */
@Component
public class DataCorrect {

    private static final Logger logger = LoggerFactory.getLogger(DataCorrect.class);

    @Autowired
    private TJobRepository jobRepository;

    @Autowired
    private PipeFlowDetailRepository pipeFlowDetailRepository;

    @Autowired
    private PipeFlowRepository pipeFlowRepository;

    @Autowired
    private TBuildRepository buildRepository;

    @Autowired
    private JobStageRepository jobStageRepository;

    @Autowired
    private JobBranchRelRepository jobBranchRelRepository;



    public void data(){
        List<String> list =jobRepository.findJobName();
        for(String jobName : list){
           List<TJob> jobList = jobRepository.findByJobName(jobName);

           Long newJobId = jobList.get(0).getId();
           List<TBuild> buildList = buildRepository.findTBuildsByJobId(newJobId);
           Map<Long,Long> map = new HashMap<>();
           for(TBuild build:buildList){
               map.put(build.getNumber(),build.getId());
           }
//           logger.info("branch id: " + jobList.get(0).getBranchName());
           logger.info("common jobName: " + jobName +" common jobId: "+ newJobId);
           for(int i=1;i<jobList.size();i++){

               TJob job = jobList.get(i);
               Long jobId = job.getId();
               List<TPipeFlowDetail> pipeFlowDetailList = pipeFlowDetailRepository.findPipeFlowByJobId(job.getId());
               for(TPipeFlowDetail pipeFlowDetail:pipeFlowDetailList){
                   Long buildId = pipeFlowDetail.getBuildId();
                   Long newBuildId = map.get(pipeFlowDetail.getBuildNumber());
                   if(null == newBuildId){
                     TBuild build = buildRepository.findOne(buildId);
                    // TBuild newBuild = new TBuild();
                     build.setJobId(newJobId);
                     try {
                         build = buildRepository.save(build);
                         map.put(build.getNumber(),build.getId());
                     }catch (Exception e){
                         System.out.println("eeee");
                     }
                     newBuildId = build.getId();
                   }

                   pipeFlowDetail.setBuildId(newBuildId);
                   pipeFlowDetail.setJobId(newJobId);
                   pipeFlowDetailRepository.save(pipeFlowDetail);

                   TPipeFlow pipeFlow = pipeFlowRepository.findOne(pipeFlowDetail.getPipeFlowId());
                   if(pipeFlow.getTriggerBuildId() == buildId) {
                       pipeFlow.setTriggerBuildId(newBuildId);
                       pipeFlow.setTriggerJob(newJobId);
                       pipeFlowRepository.save(pipeFlow);
                   }
               }
               jobBranchRelRepository.updateJobId(newJobId,jobId);
               buildRepository.deleteTBuildByJobId(jobId);
               buildRepository.deleteTBuildsByJobId(jobId);
               jobStageRepository.updateJobStage(newJobId,jobId);
               jobRepository.delete(jobId);
//               logger.info("update branch name: " + job.getBranchName());
               logger.info(i +": delete jobId: " + jobId);
           }
            logger.info("==========================================================");
        }
        logger.info("Done!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
}
