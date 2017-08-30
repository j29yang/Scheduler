
package com.scm.dashboard.persistence.repo;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.scm.dashboard.persistence.domain.TBuildIssue;
import com.scm.dashboard.persistence.domain.TIssues;

/**
 * @author j29yang
 *
 */
public interface TBuildIssueRepository extends JpaRepository<TBuildIssue, Long>{
	
	@Query("SELECT tbi.issue FROM TBuildIssue tbi WHERE tbi.build.jobId = :jobId "
			+ "AND tbi.issue.description = :description AND tbi.issue.status = :status")
	List<TIssues> findStuffedIssueByJobId(@Param("jobId")Long jobId, @Param("description")String description, 
			@Param("status")int status);
	
	@Query("SELECT tbi FROM TBuildIssue tbi WHERE tbi.issue.id = :issueId AND tbi.jobId = :jobId")
	TBuildIssue findLastRecordByIssueAndJob(@Param("issueId")Long issueId, @Param("jobId")Long jobId);

}
