package com.scm.dashboard.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import com.scm.dashboard.persistence.domain.TProject;
import com.scm.dashboard.persistence.repo.TProjectRepository;
import com.scm.dashboard.service.ProjectService;

/**
 * Service for add/remove/fetch project info.
 *
 * @author l58wang
 */
@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private TProjectRepository pjRepository;



    //store each category contains branch number
    private static Map<Long, Map<Long, String>> categoryBranchRelMap = new HashMap<>();


    @Override
    @CachePut(value = "myCache", key = "'all_projects'")
    public List<TProject> findAll() {
        return pjRepository.findAllProject();
    }

}
