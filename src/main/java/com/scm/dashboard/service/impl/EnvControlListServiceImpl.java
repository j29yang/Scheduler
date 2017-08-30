package com.scm.dashboard.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.dashboard.persistence.domain.TEnvControlList;
import com.scm.dashboard.persistence.repo.EnvControlListRepository;
import com.scm.dashboard.service.EnvControlListService;

/**
 * Service implement for EnvControlListService
 * 2017-5-22
 * @author yizheng
 *
 */
@Service
public class EnvControlListServiceImpl implements EnvControlListService{
	
	@Autowired
	private EnvControlListRepository myEnvControlListRepository;

	@Override
	public List<TEnvControlList> findAll() {
		return myEnvControlListRepository.findAll();
	}

	@Override
	public TEnvControlList findById(Long id) {
		return myEnvControlListRepository.findOne(id);
	}

	@Override
	public List<TEnvControlList> findByProjectId(Long id) {
		return myEnvControlListRepository.findByProjectId(id);
	}

	@Override
	public TEnvControlList findByBranchIdAndName(Long branchId, String componentName) {
		return myEnvControlListRepository.findByBranchIdAndName(branchId, componentName);
	}

	@Override
	public void update(TEnvControlList tECL) {
		myEnvControlListRepository.save(tECL);
	}

}
