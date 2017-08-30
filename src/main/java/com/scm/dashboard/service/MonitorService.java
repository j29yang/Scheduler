package com.scm.dashboard.service;

import com.scm.dashboard.persistence.domain.TJob;

import java.util.List;

/**
 * Created by amqu on 2017/4/19.
 */
public interface MonitorService {

    void fetchBuilds(List<TJob> jobs);

    void fetchJobBuilds(TJob job);
}
