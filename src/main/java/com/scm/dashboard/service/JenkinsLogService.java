package com.scm.dashboard.service;

import com.scm.dashboard.constant.ParseStatusEnum;
import com.scm.dashboard.persistence.domain.TBuild;
import com.scm.dashboard.persistence.domain.TJenkinsLog;
import com.scm.dashboard.persistence.domain.TJob;

/**
 * Created by amqu on 2017/5/24.
 */
public interface JenkinsLogService {
    TJenkinsLog getOneNewLog();

    TJenkinsLog getOneNewLog(Long id);

    int updateSuccess(Long id, Long issueId,Integer logSize);

    int updateStatus(Long id,ParseStatusEnum status, ParseStatusEnum lastStatus);

    int updateStatusAndSize(Long id, ParseStatusEnum status, ParseStatusEnum lastStatus,Integer logSize);

    TJenkinsLog save(TJenkinsLog tJenkinsLog);

    TJenkinsLog addJenkinsLog(TBuild build, TJob job,Long branchId);

}
