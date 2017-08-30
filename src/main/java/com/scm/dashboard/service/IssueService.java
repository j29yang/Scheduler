package com.scm.dashboard.service;

import com.scm.dashboard.persistence.domain.*;
import com.scm.dashboard.vo.MatchingRuleVO;

/**
 * Created by amqu on 2017/4/19.
 */
public interface IssueService {

    TIssues findOne(Long id);

    TIssues addIssue(TJenkinsLog log, MatchingRuleVO rule);

    TIssues save(TIssues issue);

    void updateIssueRefCnt(Long id);
}
