package com.scm.dashboard.persistence.repo;

import com.scm.dashboard.persistence.domain.TJobBranchRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by amqu on 2017/7/25.
 */
public interface JobBranchRelRepository extends JpaRepository<TJobBranchRel,Long> {

    List<TJobBranchRel> findByBranchId(Long branchId);

    @Query("select tc from TJobBranchRel tc join fetch tc.job tj where tc.branchId=:branchId and tj.id= :jobId")
    TJobBranchRel findByBranchIdAndJobId(@Param("branchId") long branchId, @Param("jobId") long jobId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE t_job_branch_rel SET deleted=1 WHERE branch_id=:branchId ", nativeQuery = true)
    void deleteTJobByBranchId(@Param("branchId") Long branchId);

    @Transactional
    @Modifying
    @Query(value="UPDATE t_job_branch_rel  SET deleted=1 WHERE project_id=:projectId",nativeQuery = true)
    void deleteJobByProjectId(@Param("projectId") Long projectId);

    @Query("select tjb from TJobBranchRel tjb join fetch tjb.job tj where tj.id = :jobId")
    List<TJobBranchRel> findByJobId(@Param("jobId") Long jobId);

    @Transactional
    @Modifying
    @Query(value="UPDATE t_job_branch_rel SET job_id =:newJobId where job_id=:oldJobId",nativeQuery = true)
    void updateJobId(@Param("newJobId") Long newJobId,@Param("oldJobId") Long oldJobId);
}
