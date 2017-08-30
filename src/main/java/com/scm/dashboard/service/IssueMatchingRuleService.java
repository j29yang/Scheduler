package com.scm.dashboard.service;

import java.util.List;

import com.scm.dashboard.persistence.domain.TIssueMatchingRule;
import com.scm.dashboard.vo.MatchingRuleVO;

/**
 * Created by amqu on 2017/4/19.
 */
public interface IssueMatchingRuleService {
    List<TIssueMatchingRule> findMatchingRegexByProject(Long projectId);

    List<TIssueMatchingRule> getMatchingRule(Long projectId);

    MatchingRuleVO parseDesc(Long projectId, String ctn);
}
