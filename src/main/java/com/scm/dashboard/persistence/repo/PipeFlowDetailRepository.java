package com.scm.dashboard.persistence.repo;

import com.scm.dashboard.persistence.domain.TPipeFlowDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author j29yang
 *
 */
public interface PipeFlowDetailRepository extends JpaRepository<TPipeFlowDetail, Long> {
	
	@Query("SELECT pfd.branchId FROM TPipeFlowDetail pfd WHERE pfd.buildId = :buildId")
    List<Long> findPipeFlowBranchIds(@Param("buildId") Long buildId);

    @Query("SELECT pfd FROM TPipeFlowDetail pfd WHERE pfd.buildId = :buildId")
    List<TPipeFlowDetail> findPipeFlow(@Param("buildId") Long buildId);

    @Query("SELECT pfd FROM TPipeFlowDetail pfd WHERE pfd.buildId = :buildId AND pfd.branchId =:branchId")
    TPipeFlowDetail findPipeFlow(@Param("buildId") Long buildId,@Param("branchId") Long branchId);

	@Query("SELECT pfd FROM TPipeFlowDetail pfd WHERE pfd.buildNumber = :buildNumber AND pfd.jobId = :jobId")
    TPipeFlowDetail findPipeFlowByBuildNum(@Param("buildNumber") Long buildNumber, @Param("jobId") Long jobId);

	@Query("SELECT pfd FROM TPipeFlowDetail pfd WHERE pfd.pipeFlowId = :pipeFlowId")
    List<TPipeFlowDetail> findPipeFlowDetails(@Param("pipeFlowId") Long pipeFlowId);

    @Query("SELECT pfd FROM TPipeFlowDetail pfd WHERE pfd.jobId = :jobId")
    List<TPipeFlowDetail> findPipeFlowByJobId(@Param("jobId") Long jobId);
}
