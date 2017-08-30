package com.scm.dashboard.persistence.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.scm.dashboard.persistence.domain.TBranch;

public interface TBranchRepository extends JpaRepository<TBranch, Long> {

	@Query("SELECT tbr FROM TBranch tbr WHERE tbr.name=:name AND tbr.deleted=0")
	TBranch findByName(@Param("name")String name);

	@Query("SELECT tbr FROM TBranch tbr WHERE tbr.id=:branchId AND tbr.deleted=0")
	TBranch findById(@Param("branchId")Long branchId);

	@Query("SELECT tbr FROM TBranch tbr WHERE tbr.projectId=:projectId AND tbr.deleted=0")
	List<TBranch> findByProjectId(@Param("projectId")Long projectId);
	
	@Query("SELECT distinct(branch.id) FROM TBranch branch WHERE branch.projectId =:projectId and branch.deleted=0 and branch.pipeline=0")
    List<Long> getBranchIdsByProjectId(@Param("projectId") Long projectId);
	
	@Query("SELECT distinct(branch.id) FROM TBranch branch WHERE branch.projectId =:projectId and branch.deleted=0 and branch.pipeline=1")
    List<Long> getPipeBranchIdsByProjectId(@Param("projectId") Long projectId);
	
	@Query(value="SELECT tb FROM TBranch tb WHERE tb.id in(:branchIds)")
	List<TBranch> getBranchListByBranchIds(@Param("branchIds")List<Long> branchIds);

	@Transactional
	@Modifying
	@Query(value="UPDATE t_branch tbr SET tbr.deleted=1 WHERE tbr.project_id=:projectId",nativeQuery = true)
	void deleteBranchByProjectId(@Param("projectId")Long projectId);

	@Query("SELECT branch FROM TBranch branch where branch.name = :branchName and branch.projectId = :projectId and branch.deleted=0")
	TBranch findByNameAndProjectId(@Param("branchName") String branchName, @Param("projectId") long projectId);

	@Transactional
	@Modifying
	@Query(value="UPDATE t_branch tbr SET tbr.coordinator_id=:coordinatorId WHERE tbr.id in(:branchIds)",nativeQuery = true)
	void updateBranchCoordinator(@Param("coordinatorId")Long coordinatorId, @Param("branchIds")List<Long> branchIds);

	@Transactional
	@Modifying
	@Query(value="UPDATE t_branch tbr SET tbr.coordinator_id=0 WHERE tbr.id in(:branchIds)",nativeQuery = true)
	void updateBranchCoordinator(@Param("branchIds")List<Long> branchIds);

	@Transactional
	@Modifying
	@Query(value="UPDATE t_branch tbr SET tbr.coordinator_id=0 WHERE tbr.coordinator_id=:coordinatorId",nativeQuery = true)
	void removeBranchCoordinator(@Param("coordinatorId")Long coordinatorId);

	List<TBranch> getTBranchByCoordinatorId(Long coordinatorId);
	
	@Query("SELECT tbr FROM TBranch tbr WHERE tbr.id = (SELECT MAX(id) FROM TBranch tbr WHERE tbr.projectId=:projectId AND tbr.deleted=0)")
	TBranch findLatestBranchByPjId(@Param("projectId")Long projectId);

}
