package com.scm.dashboard.persistence.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * table branch.
 * @author l58wang
 */
@Entity
@Table(name = "t_branch")
public class TBranch implements Serializable {

	private static final long serialVersionUID = 139901474412046532L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "project_id", nullable = false)
	private long projectId;

	@Column(nullable = false)
	private String name;

	@Column(name = "branch_order", nullable = false)
	private int order;

	@Column(name = "pipeline", nullable = false)
	private boolean pipeline;

	@Column(name = "create_time", nullable = false, length = 35)
	private Date createTime;
	
	@Column(name = "modify_time", nullable = true)
	private Date modifyTime;

	@Column(name = "user_name", nullable = false)
	private String userName;

	@Column(name = "coordinator_id", nullable = false)
	private Long coordinatorId = 0l;
	
	public TBranch() {
		super();
	}

	public TBranch(long projectId, String name, boolean pipeline) {
		this.name = name;
		this.pipeline = pipeline;
		this.projectId = projectId;
		this.createTime = new Date();
		this.order = 0;
	}

	@Column(name = "deleted", nullable = false)
	private int deleted;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public boolean isPipeline() {
		return pipeline;
	}

	public void setPipeline(boolean pipeline) {
		this.pipeline = pipeline;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public Long getCoordinatorId() {
		return coordinatorId;
	}

	public void setCoordinatorId(Long coordinatorId) {
		this.coordinatorId = coordinatorId;
	}
}
