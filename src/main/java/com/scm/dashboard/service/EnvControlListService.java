package com.scm.dashboard.service;

import java.util.List;

import com.scm.dashboard.persistence.domain.TEnvControlList;

/**
 *	2017-5-22
 *	service for environment control list 
 *  @author yizheng
 *
 */
public interface EnvControlListService {
	
	List<TEnvControlList> findAll();
	
	TEnvControlList findById(Long id);
	
	List<TEnvControlList> findByProjectId(Long id);

	TEnvControlList findByBranchIdAndName(Long branchId, String componentName);

	void update(TEnvControlList tECL);
	
}
