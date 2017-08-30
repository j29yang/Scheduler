package com.scm.dashboard.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.scm.dashboard.persistence.domain.TProject;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TProjectRepository extends CrudRepository<TProject, Long> {

	@Query("SELECT tp FROM TProject tp WHERE tp.deleted=0 AND tp.id<>0")
	List<TProject> findAllProject();

	@Query("SELECT tp FROM TProject tp WHERE tp.deleted=0 AND tp.name=:name")
	TProject findByName(@Param("name")String name);

	@Query("SELECT tp FROM TProject tp WHERE tp.id=:projectId AND tp.deleted=0")
	TProject findProjectById(@Param("projectId")Long projectId);
}
