package com.scm.dashboard.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.scm.dashboard.service.ScMatchingRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.scm.dashboard.persistence.domain.TScMatchingRule;
import com.scm.dashboard.persistence.repo.TScMatchingRuleRepository;

@Service
public class ScMatchingRuleServiceImpl implements ScMatchingRuleService {
	
	@Autowired
	private TScMatchingRuleRepository issueRuleRepository;

	@Override
	public List<TScMatchingRule> findScRuleByProject(String projectName) {
		return issueRuleRepository.findScRuleByProject(projectName);
	}

	@Override
	@Cacheable(value = "scMatchingRule", key = "'scMatchingRule'+#projectName")
	public Map<String, String> getScRuleMap(String projectName) {
		Map<String, String> map = new HashMap<String, String>();
		List<TScMatchingRule> rules = findScRuleByProject(projectName);
		for (TScMatchingRule rule : rules) {
			map.put(rule.getErrorKeyword(), rule.getScName());
		}
		return map;
	}

	@Override
	public String parseScName(Map<String, String> map, String desc) {
		for (String key : map.keySet()) {
			if (desc.contains(key)) {
				return map.get(key);
			}
		}

		return null;
	}
}
