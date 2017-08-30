package com.scm.dashboard.service.impl;

import com.scm.dashboard.persistence.domain.TJobStage;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by amqu on 2017/7/28.
 */
public interface JobSatgeService {


    List<TJobStage> findByJobId(Long jobId);

    TJobStage findByJobIdAndBranchId(Long jobId,Long branchId);
}
