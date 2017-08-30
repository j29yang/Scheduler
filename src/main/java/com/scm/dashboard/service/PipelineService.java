package com.scm.dashboard.service;

import com.scm.dashboard.persistence.domain.TBranch;
import com.scm.dashboard.persistence.domain.TBuild;
import com.scm.dashboard.persistence.domain.TJob;

import java.util.List;

/**
 * Created by amqu on 2017/6/23.
 */
public interface PipelineService {

    void handlePipeFlow(TBuild build, TJob job,List<Long> branchList);
}
