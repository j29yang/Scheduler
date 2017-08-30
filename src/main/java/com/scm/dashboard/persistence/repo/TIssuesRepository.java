/**
 * @author xiaopan
 * @created Aug 24, 2016 5:19:25 PM
 *
 */
package com.scm.dashboard.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.scm.dashboard.persistence.domain.TIssues;

/**
 * 
 */
public interface TIssuesRepository extends JpaRepository<TIssues, Long>{
	@Query( "SELECT issue FROM TIssues issue WHERE issue.description = :description "
			+ "AND issue.level = :level AND issue.status<>0")
	List<TIssues> checkDuplicatedIssue(@Param("description")String description, @Param("level")String level);

	@Query( "SELECT t.reference FROM TIssues t WHERE id = :id ")
	Long getRefCntByIssueId(@Param("id")Long id); 

    @Transactional
    @Modifying
    @Query("UPDATE TIssues SET reference = reference+1  WHERE id = :id")
    void updateIssueRefCnt(@Param("id")Long id);

	
}
