package com.scm.dashboard.persistence.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * table issueMatchingRule.
 * @author j29yang
 */
@Entity
@Table(name = "t_issue_matching_rule")
public class TIssueMatchingRule implements Serializable {

	private static final long serialVersionUID = 8043872402235412648L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "regex")
	private String regex;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "issue_type")
	private Integer issueType;
	
	@Column(name = "issue_type_name")
	private String issueTypeName;

	@Column(name = "component")
	private Integer component;
	
	@Column(name = "component_name")
	private String componentName;
	
	@Column(name = "project_id")
	private Long projectId;
	
	@Column(name = "regex_order")
	private Integer regexOrder;
	
	@Column(name = "create_time")
	private Date createTime;
	
	@Column(name = "update_time")
	private Date updateTime;
	
	@Column(name = "deleted")
	private Integer deleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIssueType() {
		return issueType;
	}

	public void setIssueType(Integer issueType) {
		this.issueType = issueType;
	}

	public Integer getComponent() {
		return component;
	}

	public void setComponent(Integer component) {
		this.component = component;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Integer getRegexOrder() {
		return regexOrder;
	}

	public void setRegexOrder(Integer regexOrder) {
		this.regexOrder = regexOrder;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getIssueTypeName() {
		return issueTypeName;
	}

	public void setIssueTypeName(String issueTypeName) {
		this.issueTypeName = issueTypeName;
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}	
}
