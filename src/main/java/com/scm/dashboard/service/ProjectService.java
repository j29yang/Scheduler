package com.scm.dashboard.service;

import java.util.List;

import com.scm.dashboard.persistence.domain.TProject;

/**
 * Created by amqu on 2017/4/19.
 */
public interface ProjectService {
    List<TProject> findAll();

}
