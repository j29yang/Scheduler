package com.scm.dashboard.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.scm.dashboard.persistence.domain.TBuild;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface TBuildRepository extends JpaRepository<TBuild, Long> {

	@Query("SELECT b FROM TBuild b WHERE b.jobId = :jobId and b.number = :number")
	List<TBuild> findBuildByJobIdAndNumber(@Param("jobId") long jobId, @Param("number") long number);

	@Query("SELECT b FROM TBuild b WHERE b.jobId = :jobId and b.number = :number and b.startTime=:startTime")
	TBuild findBuildByJobIdAndNumberAndStartTime(@Param("jobId") long jobId, @Param("number") long number, @Param("startTime") Date startTime);

	@Query("SELECT b FROM TBuild b WHERE b.jobId = :jobId and b.building = 1")
	List<TBuild> findTBuildsByJobIdAndBuilding(@Param("jobId") long jobId);

	@Query(value =
			"SELECT * FROM t_build b "
					+ "WHERE "
					+ "		b.job_id = :jobId "
					+ "AND "
					+ "		b.start_time = (SELECT MAX(start_time) FROM t_build WHERE job_id = :jobId AND building = 0)", nativeQuery = true)
	List<TBuild> findLatestFinishBuildByJob(@Param("jobId") long jobId);

	@Query(value =
			"SELECT * FROM t_build b "
					+ "WHERE "
					+ "		b.job_id = :jobId "
					+ "AND "
					+ "		b.start_time = (SELECT MIN(start_time) FROM t_build WHERE job_id = :jobId AND building = 1)", nativeQuery = true)
	List<TBuild> findEarliestInBuildingBuildByJob(@Param("jobId") long jobId);

	@Query(value ="SELECT * FROM t_build b WHERE b.job_id = :jobId AND b.start_time >:startTime", nativeQuery = true)
	List<TBuild> findBuildByTime(@Param("jobId") long jobId,@Param("startTime") String startTime);


	@Query("SELECT b FROM TBuild b WHERE b.jobId = :jobId")
	List<TBuild> findTBuildsByJobId(@Param("jobId") long jobId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM t_build_issue where job_id=:jobId",nativeQuery = true)
	void deleteTBuildByJobId(@Param("jobId") long jobId);

	@Transactional
	@Modifying
	@Query("DELETE FROM TBuild b WHERE b.jobId = :jobId")
	void deleteTBuildsByJobId(@Param("jobId") long jobId);
	
	@Query("SELECT b.issueId FROM TBuild b WHERE b.jobId =:jobId AND b.issueId is NOT NULL")
	List<Long> findIssueIdsByJobId(@Param("jobId") long jobId);
}
