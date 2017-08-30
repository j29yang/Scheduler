package com.scm.dashboard.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.scm.dashboard.persistence.domain.TScName;

public interface TScNameRepository extends CrudRepository<TScName, String> {

    @Query("SELECT tsc FROM TScName tsc WHERE tsc.name = :scName")
    TScName findByName(@Param("scName")String errorSc);
    
    @Query("SELECT tsc FROM TScName tsc WHERE tsc.projectId = :projectId ORDER BY tsc.id")
    List<TScName> findListByProjectId(@Param("projectId")Long projectId);

}
