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
 * Table t_project.
 * 
 * @author l58wang
 */
@Entity
@Table(name = "t_project")
public class TProject implements Serializable {


	private static final long serialVersionUID = -3073392201822465387L;

	public TProject() {
		super();
	}

	public TProject(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = true)
	private String description;

	@Column(name = "create_time", nullable = false, length = 35)
	private Date crateTime;

	@Column(name = "deleted", nullable = false)
	private int deleted;

	@Column(name = "user_name", nullable = false)
	private String userName;
	
	/**
	 * add by Irin
	 * for judge whether this project have component locked
	 */
	@Column(name = "is_component_locked", nullable = false)
	private String isComponentLocked;
 
	
	public String getIsComponentLocked() {
		return isComponentLocked;
	}

	public void setIsComponentLocked(String isComponentLocked) {
		this.isComponentLocked = isComponentLocked;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
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
