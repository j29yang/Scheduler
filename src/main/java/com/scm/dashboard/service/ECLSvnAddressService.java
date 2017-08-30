package com.scm.dashboard.service;

import com.scm.dashboard.persistence.domain.TEclSvnAddress;

/**
 * 
 * @author yizheng 2017-5-24
 *
 */
public interface ECLSvnAddressService {
	
	TEclSvnAddress findbyBranchId(Long branchId);
}
