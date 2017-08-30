package com.scm.dashboard.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.scm.dashboard.persistence.domain.TEnvControlList;

/**
 * 2017-5-22
 * environment control list repository
 * @author yizheng
 *
 */
public interface EnvControlListRepository extends JpaRepository<TEnvControlList, Long>{

	@Query(value = "SELECT tecl FROM TEnvControlList tecl WHERE tecl.projectId=:id ")
	List<TEnvControlList> findByProjectId(@Param("id")Long id);

	@Query(value = "SELECT tecl FROM TEnvControlList tecl WHERE tecl.branchId=:branchId and tecl.componentName=:componentName")
	TEnvControlList findByBranchIdAndName(@Param("branchId")Long branchId, @Param("componentName")String componentName);

}
