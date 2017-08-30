package com.scm.dashboard.persistence.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author j29yang
 *
 */
@Entity
@Table(name = "t_pipeline_flow")
public class TPipeFlow implements Serializable {

	private static final long serialVersionUID = -8227663138796466654L;

	public TPipeFlow() {
		super();
	}
	
	public TPipeFlow(Long triggerBuildId, Long triggerJob, Long branchId, Long projectId, Date startTime) {
		super();
		this.triggerBuildId = triggerBuildId;
		this.triggerJob = triggerJob;
		this.branchId = branchId;
		this.projectId = projectId;
		this.startTime = startTime;
		this.createTime = new Date();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "trigger_build")
	private Long triggerBuildId;

	@Column(name = "trigger_job")
	private Long triggerJob;
	
	@Column(name = "branch_id")
	private Long branchId;
	
	@Column(name = "project_id")
	private Long projectId;
	
	@Column(name = "duration")
	private long duration;
	
	@Column(name = "result")
	private String result;
	
	@Column(name = "start_time")
	private Date startTime;
	
	@Column(name = "create_time")
	private Date createTime;
	
	@Column(name = "deleted", nullable = false)
	private int deleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTriggerBuildId() {
		return triggerBuildId;
	}

	public void setTriggerBuildId(Long triggerBuildId) {
		this.triggerBuildId = triggerBuildId;
	}

	public Long getTriggerJob() {
		return triggerJob;
	}

	public void setTriggerJob(Long triggerJob) {
		this.triggerJob = triggerJob;
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
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "TPipeFlow [id=" + id + ", triggerBuild=" + triggerBuildId + ", triggerJob=" + triggerJob + ", branchId="
				+ branchId + ", projectId=" + projectId + ", createTime=" + createTime + "]";
	}

}
