package com.scm.dashboard.service.impl;

import com.scm.dashboard.persistence.domain.TIssueMatchingRule;
import com.scm.dashboard.persistence.repo.TIssueMatchingRuleRepository;
import com.scm.dashboard.service.IssueMatchingRuleService;
import com.scm.dashboard.vo.MatchingRuleVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by amqu on 2017/4/19.
 */

@Service
public class IssueMatchingRuleServiceImpl implements IssueMatchingRuleService {
    private static final Logger logger = LoggerFactory.getLogger(IssueMatchingRuleService.class);

    @Autowired
    private TIssueMatchingRuleRepository issueRuleRepository;

    public List<TIssueMatchingRule> findMatchingRegexByProject(Long projectId) {
        return issueRuleRepository.findMatchingRegexByProject(projectId);
    }

    public List<TIssueMatchingRule> getMatchingRule(Long projectId) {        
    	List<TIssueMatchingRule> regexList = issueRuleRepository.findMatchingRegexByProject(projectId);
        return regexList;
    }

    public MatchingRuleVO parseDesc(Long projectId, String ctn) {
    	List<TIssueMatchingRule> regexList = findMatchingRegexByProject(projectId);
    	MatchingRuleVO matchingRule = new MatchingRuleVO();
    	//test parse time
    	long start = System.currentTimeMillis();
    	
    	for(TIssueMatchingRule rule : regexList){
    		String regex = rule.getRegex();
    		String desc = rule.getDescription();
            try {
                Pattern p = Pattern.compile("(.*" + regex + ".*)");
                Matcher m = p.matcher(ctn);
                if (m.find()) {
                	if(desc == null || desc.isEmpty()){
                		matchingRule.setDescription(m.group(1));
                	}else{
                		matchingRule.setDescription(desc);
                	}
                	matchingRule.setScId(rule.getComponent());
                	matchingRule.setIssueType(rule.getIssueType());
                	return matchingRule;
                }else{
                	continue;
                }
            } catch (Exception e) {
                logger.error("parse jenkins log exception: ",e);
                return null;
            }
    	}
        long end = System.currentTimeMillis();
        System.out.println("issue parse: "+(end-start));
        return null;
    }
}

