package com.scm.dashboard.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.scm.dashboard.persistence.domain.TIssueMatchingRule;

public interface TIssueMatchingRuleRepository extends JpaRepository<TIssueMatchingRule, Long>{

	@Query( "SELECT rule FROM TIssueMatchingRule rule where rule.projectId =:projectId and rule.deleted=0 order by rule.regexOrder asc, rule.updateTime desc")
	List<TIssueMatchingRule> findMatchingRegexByProject(@Param("projectId") Long projectId);
	
}
