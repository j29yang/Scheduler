package com.scm.dashboard.service;

import com.scm.dashboard.persistence.domain.TScMatchingRule;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

/**
 * Created by amqu on 2017/4/19.
 */
public interface ScMatchingRuleService {
    List<TScMatchingRule> findScRuleByProject(String projectName);

    Map<String, String> getScRuleMap(String projectName);

    String parseScName(Map<String, String> map, String desc);
}
