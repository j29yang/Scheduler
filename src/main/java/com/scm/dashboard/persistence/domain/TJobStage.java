package com.scm.dashboard.persistence.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author j29yang
 *
 */
@Entity
@Table(name = "t_job_stage")
public class TJobStage implements Serializable {

	private static final long serialVersionUID = -6654289624849571044L;

	public TJobStage() {
		super();
	}
	
	public TJobStage(Long jobId, Long stageId, Long branchId, Long projectId, int order, String mode, boolean metricsFlag) {
		super();
		this.jobId = jobId;
		this.stageId = stageId;
		this.branchId = branchId;
		this.projectId = projectId;
		this.order = order;
		this.mode = mode;
		this.upStream = "";
		this.downStream = "";
		this.metricsStatisticsFlag = metricsFlag;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "job_id")
	private Long jobId;
	
	@Column(name = "stage_id")
	private Long stageId;
	
	@Column(name = "branch_id")
	private Long branchId;
	
	@Column(name = "project_id")
	private Long projectId;
	
	@Column(name = "flow_order")
	private int order;
	
	@Column(name = "mode")
	private String mode;
	
	@Column(name = "up_stream")
	private String upStream;
	
	@Column(name = "down_stream")
	private String downStream;
	
	@Column(name = "metrics_statistics_flag")
	private boolean metricsStatisticsFlag;
	
	@Column(name = "build_time_key_job")
	private boolean bTimeKeyJob;
	
	@Column(name = "publish_job")
	private boolean publishJob;
	
	@Column(name = "subscribe_job")
	private boolean subscribeJob;

	@Column(name = "version_regex")
	private String versionRegex;
	
	@Column(name = "deleted", nullable = false)
	private int deleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Long getStageId() {
		return stageId;
	}

	public void setStageId(Long stageId) {
		this.stageId = stageId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public String getUpStream() {
		return upStream;
	}

	public void setUpStream(Set<Long> upStream) {
		this.upStream = upStream.toString();
	}

	public String getDownStream() {
		return downStream;
	}

	public void setDownStream(Set<Long> downStream) {
		this.downStream = downStream.toString();
	}

	public boolean isMetricsStatisticsFlag() {
		return metricsStatisticsFlag;
	}

	public void setMetricsStatisticsFlag(boolean metricsStatisticsFlag) {
		this.metricsStatisticsFlag = metricsStatisticsFlag;
	}
	
	public boolean isbTimeKeyJob() {
		return bTimeKeyJob;
	}

	public void setbTimeKeyJob(boolean bTimeKeyJob) {
		this.bTimeKeyJob = bTimeKeyJob;
	}
	
	public boolean isPublishJob() {
		return publishJob;
	}

	public void setPublishJob(boolean publishJob) {
		this.publishJob = publishJob;
	}

	public boolean isSubscribeJob() {
		return subscribeJob;
	}

	public void setSubscribeJob(boolean subscribeJob) {
		this.subscribeJob = subscribeJob;
	}

	public String getVersionRegex() {
		return versionRegex;
	}

	public void setVersionRegex(String versionRegex) {
		this.versionRegex = versionRegex;
	}
	
	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "TJobStage [id=" + id + ", jobId=" + jobId + ", stageId=" + stageId + ", branchId=" + branchId
				+ ", projectId=" + projectId + ", order=" + order + ", mode=" + mode + ", upStream=" + upStream
				+ ", downStream=" + downStream + "]";
	}

}
