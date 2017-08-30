package com.scm.dashboard.persistence.repo;

import com.scm.dashboard.persistence.domain.TPipeFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * @author j29yang
 *
 */
public interface PipeFlowRepository extends JpaRepository<TPipeFlow, Long> {
	
//	@Query("SELECT pf FROM TPipeFlow pf WHERE pf.id = (SELECT MAX(pf.id) from TPipeFlow pf WHERE pf.branchId = :branchId)")
//	TPipeFlow findLastestPipeFlowByBranchId(@Param("branchId")Long branchId);
	
	@Query("SELECT pf FROM TPipeFlow pf WHERE pf.startTime = (SELECT MAX(pf.startTime) from TPipeFlow pf WHERE pf.branchId = :branchId) AND pf.branchId = :branchId AND pf.deleted = 0")
	List<TPipeFlow> findLastestPipeFlowByBranchId(@Param("branchId") Long branchId);

	@Query("SELECT pf FROM TPipeFlow pf WHERE pf.startTime BETWEEN :startTime AND :endTime AND pf.branchId IN(:branchList) AND pf.result = 'SUCCESS' AND pf.projectId = :pjId AND pf.deleted = 0")
	List<TPipeFlow> getPipeFlowListByDate(@Param("pjId") Long pjId, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("branchList") List<Long> branchList);

	@Query("SELECT pf FROM TPipeFlow pf WHERE pf.startTime BETWEEN :startTime AND :endTime AND pf.branchId IN(:branchList) AND pf.result IN ('SUCCESS', 'FAILURE') AND pf.projectId = :pjId AND pf.deleted = 0")
	List<TPipeFlow> getTotalPipeFlowListByDateAndBranch(@Param("pjId") Long pjId, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("branchList") List<Long> branchList);

	@Query("SELECT pf FROM TPipeFlow pf WHERE pf.triggerBuildId=:triggerBuildId AND pf.branchId = :branchId AND pf.deleted = 0")
	TPipeFlow getTPipeFlow(@Param("triggerBuildId") Long triggerBuildId,@Param("branchId") Long branchId);
}
