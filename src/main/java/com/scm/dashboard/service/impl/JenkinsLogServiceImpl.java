package com.scm.dashboard.service.impl;

import com.scm.dashboard.constant.ParseStatusEnum;
import com.scm.dashboard.persistence.domain.TBuild;
import com.scm.dashboard.persistence.domain.TJenkinsLog;
import com.scm.dashboard.persistence.domain.TJob;
import com.scm.dashboard.persistence.repo.TJenkinsLogRepository;
import com.scm.dashboard.schedulers.FetchBuildsScheduler;
import com.scm.dashboard.service.JenkinsLogService;
import com.scm.dashboard.utils.JenkinsUrlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by amqu on 2017/5/24.
 */
@Service
public class JenkinsLogServiceImpl implements JenkinsLogService {

    private static final Logger logger =  LoggerFactory.getLogger(JenkinsLogService.class);

    @Autowired
    private TJenkinsLogRepository tJenkinsLogRepository;


    @Override
    public TJenkinsLog getOneNewLog() {
        return tJenkinsLogRepository.getOneNewLog();

    }

    @Override
    public TJenkinsLog getOneNewLog(Long id) {
        return tJenkinsLogRepository.getLog(id);

    }

    @Override
    public int updateStatus(Long id, ParseStatusEnum status, ParseStatusEnum lastStatus) {
        return tJenkinsLogRepository.updateStatus(id,status.getValue(), lastStatus.getValue());
    }


    @Override
    public int updateStatusAndSize(Long id, ParseStatusEnum status, ParseStatusEnum lastStatus,Integer logSize) {
        return tJenkinsLogRepository.updateStatus(id,status.getValue(), lastStatus.getValue(),logSize);
    }

    @Override
    public int updateSuccess(Long id, Long issueId,Integer logSize) {
        return tJenkinsLogRepository.updateSuccess(id,issueId,logSize);
    }

    @Override
    public TJenkinsLog save(TJenkinsLog tJenkinsLog){
        return tJenkinsLogRepository.save(tJenkinsLog);
    }


    @Override
    public TJenkinsLog addJenkinsLog(TBuild build, TJob job,Long branchId) {
        TJenkinsLog tJenkinsLog = new TJenkinsLog();
        tJenkinsLog.setBuildId(build.getId());
        tJenkinsLog.setBuildNum(build.getNumber());
        tJenkinsLog.setProjectId(job.getProjectId());
        tJenkinsLog.setBranchId(branchId);
        tJenkinsLog.setJobId(job.getId());
        tJenkinsLog.setJenkinsUrl(JenkinsUrlUtil.getJenkinsLogURL(job, build.getNumber()));
        tJenkinsLog.setStatus(ParseStatusEnum.NEW.getValue());
        tJenkinsLog.setCreateTime(new Date());
        try {
            tJenkinsLog = save(tJenkinsLog);
        } catch (Exception e) {

        }
        return tJenkinsLog;
    }
}
