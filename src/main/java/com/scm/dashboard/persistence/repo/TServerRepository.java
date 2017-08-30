package com.scm.dashboard.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.scm.dashboard.persistence.domain.TServer;

/**
 * @author j29yang
 *
 */
public interface TServerRepository extends JpaRepository<TServer, Long> {

	TServer findByAddressAndProjectId(String address,Long projectId);

}
