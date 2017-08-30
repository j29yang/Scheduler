package com.scm.dashboard.service;

import com.scm.dashboard.persistence.domain.TServer;

/**
 * Created by amqu on 2017/4/19.
 */
public interface ServerService {
    TServer findByAddressAndProjectId(String address, Long projectId);

    TServer save(TServer server);
}
