package com.scm.dashboard.service;

import com.scm.dashboard.persistence.domain.TJobBranchRel;
import com.scm.dashboard.persistence.domain.TJob;

import java.util.List;

/**
 * Created by amqu on 2017/4/19.
 */
public interface JobService {

    List<TJob> getJobsNeedToBeWatched();

    TJob getJobById(Long jobId);

    List<TJob> getJobPage(int page, int size);

    TJob findByJobNameAndProjectId(String jobName, Long prjectId,Long serverId);

    TJob save(TJob job);

    List<TJob> getJobByBranchId(Long branchId);

    List<TJobBranchRel> getJobBranchRels(Long jobId);

}
