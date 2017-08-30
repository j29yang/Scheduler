package com.scm.dashboard.persistence.repo;

import com.scm.dashboard.persistence.domain.TJobStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author j29yang
 *
 */
public interface JobStageRepository extends JpaRepository<TJobStage, Long> {

	@Query("SELECT tjs FROM TJobStage tjs WHERE tjs.branchId = :branchId and tjs.stageId = :stageId and tjs.deleted = 0 order by tjs.order asc")
	List<TJobStage> getJobsByBranchIdAndStageId(@Param("branchId") Long branchId, @Param("stageId") Long stageId);

	@Query("SELECT tjs FROM TJobStage tjs WHERE tjs.branchId = :branchId AND tjs.jobId = :jobId AND tjs.deleted = 0")
	TJobStage getByBranchIdAndJobId(@Param("branchId") Long branchId, @Param("jobId") Long jobId);

	@Query(value = "SELECT * FROM t_job_stage WHERE branch_id = :branchId AND deleted = 0 ORDER BY flow_order ASC LIMIT 1", nativeQuery = true)
	TJobStage getFirstJobInBranch(@Param("branchId")Long branchId);


	@Query("SELECT tjs FROM TJobStage tjs WHERE tjs.jobId = :jobId AND tjs.deleted = 0")
	List<TJobStage> getJobsByJobId(@Param("jobId") Long jobId);

	@Transactional
	@Modifying
	@Query(value = "UPDATE TJobStage tjs SET tjs.jobId= :newJobId where tjs.jobId=:oldJobId")
	void updateJobStage(@Param("newJobId") Long newJobId,@Param("oldJobId") Long oldJobId);
}
