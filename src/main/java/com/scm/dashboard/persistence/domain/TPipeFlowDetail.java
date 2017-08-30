package com.scm.dashboard.persistence.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author j29yang
 *
 */
@Entity
@Table(name = "t_pipeline_flow_detail")
public class TPipeFlowDetail implements Serializable {

	private static final long serialVersionUID = -4092128033892875919L;

	public TPipeFlowDetail() {
		super();
	}

	public TPipeFlowDetail(Long buildId, Long buildNum, Long jobId, Long branchId, Long projectId, Long pipeFlowId, Long jobStageId, boolean metricsFlag) {
		super();
		this.buildId = buildId;
		this.buildNumber = buildNum;
		this.jobId = jobId;
		this.branchId = branchId;
		this.projectId = projectId;
		this.pipeFlowId = pipeFlowId;
		this.jobStageId = jobStageId;
		this.metricsStatisticsFlag = metricsFlag;
		this.createTime = new Date();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "build_id")
	private Long buildId;
	
	@Column(name = "build_number")
	private Long buildNumber;

	@Column(name = "job_id")
	private Long jobId;
	
	@Column(name = "branch_id")
	private Long branchId;
	
	@Column(name = "project_id")
	private Long projectId;
	
	@Column(name = "pipe_flow")
	private Long pipeFlowId;

	@Column(name = "job_stage")
	private Long jobStageId;
	
	@Column(name = "metrics_statistics_flag")
	private boolean metricsStatisticsFlag;

	@Column(name = "create_time", nullable = false)
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBuildId() {
		return buildId;
	}

	public void setBuildId(Long buildId) {
		this.buildId = buildId;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getPipeFlowId() {
		return pipeFlowId;
	}

	public void setPipeFlowId(Long pipeFlowId) {
		this.pipeFlowId = pipeFlowId;
	}

	public Long getJobStageId() {
		return jobStageId;
	}

	public void setJobStageId(Long jobStageId) {
		this.jobStageId = jobStageId;
	}

	public Long getBuildNumber() {
		return buildNumber;
	}

	public void setBuildNumber(Long buildNumber) {
		this.buildNumber = buildNumber;
	}
	
	public boolean isMetricsStatisticsFlag() {
		return metricsStatisticsFlag;
	}

	public void setMetricsStatisticsFlag(boolean metricsStatisticsFlag) {
		this.metricsStatisticsFlag = metricsStatisticsFlag;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "TPipeFlowDetail [id=" + id + ", buildId=" + buildId + ", buildNumber=" + buildNumber + ", jobId="
				+ jobId + ", branchId=" + branchId + ", projectId=" + projectId + ", pipeFlowId=" + pipeFlowId
				+ ", jobStageId=" + jobStageId + "]";
	}

}
