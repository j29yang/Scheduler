package com.scm.dashboard.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.scm.dashboard.persistence.domain.TScMatchingRule;

public interface TScMatchingRuleRepository extends JpaRepository<TScMatchingRule, Long> {
	
	@Query( "SELECT rule FROM TScMatchingRule rule where rule.project = :projectName")
	List<TScMatchingRule> findScRuleByProject(@Param("projectName")String projectName);
	
}
