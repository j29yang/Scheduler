package com.scm.dashboard.persistence.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_server")
public class TServer implements Serializable {

	private static final long serialVersionUID = 3940388143677749544L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String address;

	@Column(name = "project_id")
	private long projectId;

	@Column
	private String user;

	@Column
	private String password;

//	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private String server;

	public TServer() {
		super();
	}

	public TServer(String address, long projectId) {
		super();
		this.address = address;
		this.projectId = projectId;
		this.server = "jenkins";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getProjectId() {
		return projectId;
}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}
}
