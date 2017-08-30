package com.scm.dashboard.persistence.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * table SCMatchingRule.
 * @author j29yang
 */
@Entity
@Table(name = "t_sc_matching_rule")
public class TScMatchingRule implements Serializable {

	private static final long serialVersionUID = -3527371626291418492L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "error_keyword")
	private String errorKeyword;
	
	@Column(name = "sc_name")
	private String scName;
	
	@Column(name = "project_name")
	private String project;

	@Column(name = "project_Id")
	private long projectId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getErrorKeyword() {
		return errorKeyword;
	}

	public void setErrorKeyword(String errorKeyword) {
		this.errorKeyword = errorKeyword;
	}

	public String getScName() {
		return scName;
	}

	public void setScName(String scName) {
		this.scName = scName;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	@Override
	public String toString() {
		return "TScMatchingRule [id=" + id + ", errorKeyword=" + errorKeyword + ", scName=" + scName + ", project="
				+ project + "]";
	}

}
