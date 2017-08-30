package com.scm.dashboard.service.impl;


import com.scm.dashboard.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.dashboard.persistence.domain.TServer;
import com.scm.dashboard.persistence.repo.TServerRepository;

/**
 * @author j29yang
 * 
 */
@Service
public class ServerServiceImpl implements ServerService {

	@Autowired
	private TServerRepository serverRepository;
	
	@Override
	public TServer findByAddressAndProjectId(String address, Long projectId) {
		return serverRepository.findByAddressAndProjectId(address,projectId);
	}
	
	@Override
	public TServer save(TServer server) {
		return serverRepository.save(server);
	}
}
