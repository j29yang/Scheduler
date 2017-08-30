package com.scm.dashboard.persistence.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.scm.dashboard.persistence.domain.TJob;
import org.springframework.transaction.annotation.Transactional;

public interface TJobRepository extends JpaRepository<TJob, Long> {

	@Query(value = "SELECT * FROM t_job WHERE project_Id = :pjId and watch = 1 and deleted=0", nativeQuery = true)
	List<TJob> findByWatchIsTrueAndProjectId(@Param(value = "pjId") Long pjId);


	@Query(value = "SELECT * FROM t_job WHERE watch = 1 and deleted=0", nativeQuery = true)
	List<TJob> findTJobsByWatchIsTrue();

	@Query(value = "SELECT job FROM TJob job WHERE job.watch = 1 and job.deleted=0")
	Page<TJob> findWatchTJobsByPaging(Pageable pageable);

	@Query(value = "SELECT job FROM TJob job join fetch job.server se WHERE  job.projectId =:projectId and job.jobName =:jobName and job.watch = 1 and se.id =:serverId and job.deleted=0  ")
	TJob findByJobNameAndprojectId(@Param("jobName") String jobName, @Param("projectId") Long projectId,@Param("serverId") Long serverId);

	@Query(value = "SELECT * FROM t_job WHERE branch_id=:branchId and deleted=0", nativeQuery = true)
	List<TJob> findTJobsByBranchId(@Param("branchId") Long branchId);

	@Query(value = "select job_name from (select count(*) as num ,job_name,server_id from t_job group by job_name,server_id) TB where TB.num>1  ",nativeQuery = true)
	List<String> findJobName();

	@Query(value = "SELECT job FROM TJob job WHERE job.jobName =:jobName order by job.id ")
	List<TJob> findByJobName(@Param("jobName") String jobName);

	@Transactional
	@Modifying
	@Query("DELETE FROM TJob b WHERE b.id = :jobId")
	void deleteTBuildsByJobId(@Param("jobId") long jobId);
}
