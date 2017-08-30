package com.scm.dashboard.service;

import com.scm.dashboard.persistence.domain.TJenkinsLog;
import com.scm.dashboard.service.dto.BuildDTO;
import com.scm.dashboard.vo.MatchingRuleVO;

/**
 * Created by amqu on 2017/5/23.
 */
public interface ParseService {

    void startParseDescription(TJenkinsLog jenkinsLog);

    TJenkinsLog getNewJenkinsLog();

    String parseVersion(String consoleLog);

    String parseVersion(BuildDTO build);

    MatchingRuleVO parseDescription(String consoleLog, Long projectId);
}
