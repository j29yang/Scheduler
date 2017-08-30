package com.scm.dashboard.persistence.repo;

import com.scm.dashboard.persistence.domain.TCoordinator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by amqu on 2017/4/19.
 */
public interface TCoordinatorRepository extends JpaRepository<TCoordinator, Long> {

    List<TCoordinator> findAllByProjectId(Long projectId);

}
