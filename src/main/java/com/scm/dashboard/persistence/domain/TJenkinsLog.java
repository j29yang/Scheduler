package com.scm.dashboard.persistence.domain;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * Created by amqu on 2017/5/23.
 */
@Entity
@Table(name = "t_jenkins_log")
public class TJenkinsLog implements Serializable{


    private static final long serialVersionUID = 34235323029322526L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "build_number", nullable = false)
    private long buildNum;

    @Column(name = "build_id", nullable = false)
    private long buildId;

    @Column(name = "issue_id", nullable = true)
    private Long issueId;

    @Column(name = "job_id", nullable = false)
    private long jobId;

    @Column(name = "branch_id", nullable = false)
    private long branchId;

    @Column(name = "project_id", nullable = false)
    private long projectId;

    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "jenkins_url", nullable = false)
    private String jenkinsUrl;

    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Column(name = "log_size", nullable = false)
    private Integer logSize;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBuildNum() {
        return buildNum;
    }

    public void setBuildNum(long buildNum) {
        this.buildNum = buildNum;
    }


    public long getBuildId() {
        return buildId;
    }

    public void setBuildId(long buildId) {
        this.buildId = buildId;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public long getBranchId() {
        return branchId;
    }

    public void setBranchId(long branchId) {
        this.branchId = branchId;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getJenkinsUrl() {
        return jenkinsUrl;
    }

    public void setJenkinsUrl(String jenkinsUrl) {
        this.jenkinsUrl = jenkinsUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getLogSize() {
        return logSize;
    }

    public void setLogSize(Integer logSize) {
        this.logSize = logSize;
    }
}
