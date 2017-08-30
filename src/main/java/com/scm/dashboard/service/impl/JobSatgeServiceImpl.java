package com.scm.dashboard.service.impl;

import com.scm.dashboard.persistence.domain.TJobStage;
import com.scm.dashboard.persistence.repo.JobStageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by amqu on 2017/7/28.
 */
@Service
public class JobSatgeServiceImpl implements JobSatgeService {

    @Autowired
    private JobStageRepository jobStageRepository;


    @Override
    public List<TJobStage> findByJobId(Long jobId) {
        return jobStageRepository.getJobsByJobId(jobId);
    }

    @Override
    public TJobStage findByJobIdAndBranchId(Long jobId, Long branchId) {
       return jobStageRepository.getByBranchIdAndJobId(branchId,jobId);
    }
}
