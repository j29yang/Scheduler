package com.scm.dashboard.persistence.repo;

import com.scm.dashboard.persistence.domain.TStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface StageRepository extends JpaRepository<TStage, Long> {
	
	@Query("SELECT ts FROM TStage ts WHERE ts.branchId = :branchId AND ts.deleted = 0 order by ts.order asc")
	List<TStage> getStagesByBranchId(@Param("branchId") Long branchId);
	
	
}
