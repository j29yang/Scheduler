package com.scm.dashboard.service;

import com.scm.dashboard.persistence.domain.TBuild;
import com.scm.dashboard.persistence.domain.TIssues;
import com.scm.dashboard.persistence.domain.TJob;
import com.scm.dashboard.service.dto.BuildDTO;

import java.util.Date;
import java.util.List;

/**
 * Created by amqu on 2017/4/19.
 */
public interface BuildService {

    TBuild saveBuild(TBuild build) ;

    TBuild updateIssue(TBuild build, Long issueId);

    TBuild getLatestFinishBuildByJob(long jobId);

    TBuild findEarliestInBuildingBuildByJob(long jobId);
    
    List<TBuild> getBuildByTime(long jobId,String date);

    TBuild getBuildById(Long buildId);

    List<TBuild> findBuildIsBuilding(long jobId);

    TBuild findBuildByJobIdAndNumber(long jobId,Long number);

    List<TBuild> saveBuilds(List<TBuild> builds);

    TBuild getBuildWithIssueById(Long buildId);

    TBuild toTBuild(BuildDTO buildDto, TJob job,TBuild build);

    TBuild findUniqueBuild(long jobId,Long number,Date startTime);
    
    List<Long> findIssueIdsByJobId(long jobId);

}
