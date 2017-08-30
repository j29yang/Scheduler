package com.scm.dashboard.persistence.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_coordinator")
public class TCoordinator implements Serializable{


	private static final long serialVersionUID = 1661350337965521236L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "coordinator",nullable = false)
	private String coordinator;

	@Column(name = "project_id", nullable = false)
	private Long projectId ;

	@Column(name = "create_time", nullable = false, length = 35)
	private Date crateTime;

	@Column(name = "user_name", nullable = false)
	private String userName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCoordinator() {
		return coordinator;
	}

	public void setCoordinator(String coordinator) {
		this.coordinator = coordinator;
	}


	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Date getCrateTime() {
		return crateTime;
	}

	public void setCrateTime(Date crateTime) {
		this.crateTime = crateTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
