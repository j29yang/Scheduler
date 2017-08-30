package com.scm.dashboard.service.impl;

import com.scm.dashboard.persistence.domain.TScirtemServer;

import java.util.List;

/**
 * Created by amqu on 2017/6/20.
 */
public interface ScirtemServerService {

    TScirtemServer addScirtemServer();

    void updateLiveTime(long id);

    List<TScirtemServer> getLiveServer();
}

