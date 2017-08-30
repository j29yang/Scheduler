package com.scm.dashboard.service;

import com.scm.dashboard.persistence.domain.TBuildIssue;
import com.scm.dashboard.persistence.domain.TIssues;

/**
 * Created by amqu on 2017/4/19.
 */
public interface BuildIssueService {
    TBuildIssue saveBuildIssue(TBuildIssue buildIssue);

    TIssues findStuffedIssueByJob(Long jobId);

    TBuildIssue findLastBuildIssue(TIssues issue, Long jobId);

}
