package com.scm.dashboard.service;

import com.scm.dashboard.persistence.domain.TJob;
import com.scm.dashboard.service.dto.BuildDTO;

import java.util.List;

/**
 * Created by amqu on 2017/5/23.
 */
public interface FetchBuildsService {
    List<BuildDTO> getBuilds(TJob job, long lfCTime);
}
