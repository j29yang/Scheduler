package com.scm.dashboard.service.dto;

import com.scm.dashboard.persistence.domain.TBranch;
import com.scm.dashboard.persistence.domain.TBuild;
import com.scm.dashboard.persistence.domain.TJob;

import java.util.List;

/**
 * Created by amqu on 2017/7/28.
 */
public class JobBuild  {

    private TJob job;

    private TBuild build;

    private List<Long> branchIdList;

    private boolean isTriggerBuild;

    public JobBuild(){

    }

    public JobBuild(TJob job,TBuild build,List<Long> branchIdList){
        this.job = job;
        this.build = build;
        this.branchIdList = branchIdList;
    }

    public TJob getJob() {
        return job;
    }

    public void setJob(TJob job) {
        this.job = job;
    }

    public TBuild getBuild() {
        return build;
    }

    public void setBuild(TBuild build) {
        this.build = build;
    }

    public List<Long> getBranchIdList() {
        return branchIdList;
    }

    public void setBranchIdList(List<Long> branchIdList) {
        this.branchIdList = branchIdList;
    }

    public boolean isTriggerBuild() {
        return isTriggerBuild;
    }

    public void setTriggerBuild(boolean triggerBuild) {
        isTriggerBuild = triggerBuild;
    }
}
