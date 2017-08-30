package com.scm.dashboard.utils;

import com.scm.dashboard.persistence.domain.TBranch;
import com.scm.dashboard.persistence.domain.TBuild;
import com.scm.dashboard.persistence.domain.TJob;
import com.scm.dashboard.service.*;
import com.scm.dashboard.service.dto.BuildDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by amqu on 2017/8/29.
 */
@Component
public class AddPipeLine {

    @Autowired
    private PipelineService pipelineService;

    @Autowired
    private BuildService buildService;

    @Autowired
    private JobService jobService;

    @Autowired
    private BranchService branchService;

    @Autowired
    private JenkinsService jenkinsService;

    public void addPipeline(){
        Long jobId = 62L;
//        List<TBuild> buildList = buildService.getBuildByTime(jobId,"2017-08-20 02:07:15");
        TJob tJob = jobService.getJobById(jobId);

//        for(TBuild tBuild : buildList) {
            TBuild tBuild = buildService.getBuildById(1178315L);//1176911L
            String jenkinsBuildUrl = JenkinsUrlUtil.getJenkinsBuildURL(tJob, tBuild.getNumber());
            try {
                BuildDTO buildDto = jenkinsService.getBuildInfo(jenkinsBuildUrl);
                if(null == buildDto){
//                    continue;
                    return;
                }
                tBuild.setUpstreamBuildNum(buildDto.getUpstreamBuild());
                tBuild.setUpstreamUrl(buildDto.getUpstreamUrl());
            }catch (Exception e){

            }
            List<Long> branchIdList = branchService.getBranchIdsForBuild(tBuild.getVersion(),jobId);
            pipelineService.handlePipeFlow(tBuild,tJob,branchIdList);
//       }



    }
}
