package com.scm.dashboard.persistence.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 
 * @author j29yang
 */
@Entity
@Table(name = "t_stage")
public class TStage implements Serializable {

	private static final long serialVersionUID = 7525186899933277317L;

	public TStage() {
		super();
	}
	
	public TStage(String name, Long projectId, Long branchId, int order) {
		super();
		this.name = name;
		this.projectId = projectId;
		this.branchId = branchId;
		this.order = order;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "name")
	private String name;

	@Column(name = "project_id")
	private Long projectId;

	@Column(name = "branch_id")
	private Long branchId;
	
	@Column(name = "stage_order")
	private int order;
	
	@Column(name = "deleted", nullable = false)
	private int deleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "TStage [id=" + id + ", branchId=" + branchId + ", order=" + order + ", deleted=" + deleted + "]";
	}
	
}
