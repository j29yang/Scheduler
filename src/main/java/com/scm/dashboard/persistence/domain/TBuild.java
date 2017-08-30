package com.scm.dashboard.persistence.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "t_build")
public class TBuild implements Serializable {

	private static final long serialVersionUID = -7918330837408190578L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "number")
	private long number;
	
	@Column(name = "job_id")
	private long jobId;
	
	@Column(name = "project_id")
	private Long projectId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time", nullable = false, length = 35)
	private Date startTime;

    @Column(name = "start_timestamp")
    private Long startTimestamp;

	@Column(name = "duration")
	private long duration;

	@Column(name = "result")
	private String result;

	@Column(name = "version")
	private String version;
	
	@Column(name = "version_count_once")
	private boolean versionCountOnce;

	@Column(name = "building")
	private boolean building;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time",  length = 35)
	private Date createTime;

	@Column(name = "data_source")
	private String dataSource;

	@Column( name = "issue_id")
	private Long issueId;

	@Transient
	private String upstreamUrl;

	@Transient
	private Long upstreamBuildNum;
	
	@Transient
	private String unifiedCommitId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

    public Long getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public boolean isVersionCountOnce() {
		return versionCountOnce;
	}

	public void setVersionCountOnce(boolean versionCountOnce) {
		this.versionCountOnce = versionCountOnce;
	}

	public boolean isBuilding() {
		return building;
	}

	public void setBuilding(boolean building) {
		this.building = building;
	}
	
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public long getJobId() {
		return jobId;
	}

	public void setJobId(long jobId) {
		this.jobId = jobId;
	}
	
	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getUpstreamUrl() {
		return upstreamUrl;
	}

	public void setUpstreamUrl(String upstreamUrl) {
		this.upstreamUrl = upstreamUrl;
	}

	public Long getUpstreamBuildNum() {
		return upstreamBuildNum;
	}

	public void setUpstreamBuildNum(Long upstreamBuildNum) {
		this.upstreamBuildNum = upstreamBuildNum;
	}

	public String getUnifiedCommitId() {
		return unifiedCommitId;
	}

	public void setUnifiedCommitId(String unifiedCommitId) {
		this.unifiedCommitId = unifiedCommitId;
	}

	@Override
	public String toString() {
		return "TBuild [id=" + id + ", startTime=" + startTime + ", duration=" + duration + ", result=" + result
				+ ", version=" + version + ", building=" + building + ", number=" + number + ", jobId=" + jobId + ", issue="
				+ issueId + "]";
	}
}
