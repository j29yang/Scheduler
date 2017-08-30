package com.scm.dashboard.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.scm.dashboard.persistence.domain.TEclSvnAddress;

/**
 * 
 * @author yizheng 2017-2-24
 *
 */
public interface ECLSvnAddressRepository extends JpaRepository<TEclSvnAddress, Long>{

	@Query( value = "SELECT te FROM TEclSvnAddress te WHERE te.branchId = :branchId ")
	TEclSvnAddress findByBranchId(@Param("branchId")Long branchId);
	

}
