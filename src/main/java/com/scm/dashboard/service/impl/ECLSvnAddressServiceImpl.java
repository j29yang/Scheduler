package com.scm.dashboard.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.dashboard.persistence.domain.TEclSvnAddress;
import com.scm.dashboard.persistence.repo.ECLSvnAddressRepository;
import com.scm.dashboard.service.ECLSvnAddressService;

@Service
public class ECLSvnAddressServiceImpl implements ECLSvnAddressService{
	
	@Autowired
	private ECLSvnAddressRepository myECLSvnAddressRepository;

	@Override
	public TEclSvnAddress findbyBranchId(Long branchId) {
		// TODO Auto-generated method stub
		return myECLSvnAddressRepository.findByBranchId(branchId);
	}

}
