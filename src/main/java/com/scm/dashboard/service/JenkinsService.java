package com.scm.dashboard.service;

import com.scm.dashboard.persistence.domain.TJob;
import com.scm.dashboard.service.dto.BuildDTO;
import com.scm.dashboard.service.dto.JobDTO;

/**
 * Created by amqu on 2017/5/19.
 */
public interface JenkinsService {

    String fetchJenkinsConsoleLog(String logUrl) throws Exception ;

    JobDTO fetchBuildsForJob(TJob job);

    BuildDTO getBuildInfo(String jenkinsBuildUrl);
}
