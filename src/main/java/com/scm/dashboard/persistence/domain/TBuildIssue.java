package com.scm.dashboard.persistence.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


/**
 * @author j29yang
 */
@Entity
@Table(name = "t_build_issue")
public class TBuildIssue implements Serializable {
	
	private static final long serialVersionUID = 7776794497990062104L;
	
	public TBuildIssue() {
		super();
	}

	public TBuildIssue(TIssues issue, Long projectId, Long branchId, Long coordinatorId, Long jobId, TBuild build) {
		super();
		this.issue = issue;
		this.branchId = branchId;
		this.projectId = projectId;
		this.jobId = jobId;
		this.build = build;
		this.coordinatorId = coordinatorId;
		this.createTime =build.getStartTime();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "project_id")
	private Long projectId;
	
	@Column(name = "coordinator_id")
	private Long coordinatorId;
	
	@Column(name = "branch_id")
	private Long branchId;
	
	@ManyToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(referencedColumnName = "id", name = "issue_id", nullable = false)
	@NotFound(action= NotFoundAction.IGNORE)
	private TIssues issue;

	@ManyToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(referencedColumnName = "id", name = "coordinator_id", insertable = false, updatable = false)
	private TCoordinator coordinator;
	
	@Column(name = "create_time")
	private Date createTime;
	
	@Column(name = "job_id")
	private Long jobId;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(referencedColumnName = "id", name = "build_id", nullable = false)
	private TBuild build;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TIssues getIssue() {
		return issue;
	}

	public void setIssue(TIssues issue) {
		this.issue = issue;
	}

	public Long getCoordinatorId() {
		return coordinatorId;
	}

	public void setCoordinatorId(Long coordinatorId) {
		this.coordinatorId = coordinatorId;
	}
	
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public TBuild getBuild() {
		return build;
	}

	public void setBuild(TBuild build) {
		this.build = build;
	}
	@Override
	public String toString() {
		return "TBuildIssue [id=" + id + ", branchId=" + branchId + ", issue=" + issue + ", coordinatorId=" + coordinatorId
				+ ", jobId=" + jobId + ", build=" + build + "]";
	}
	
}
