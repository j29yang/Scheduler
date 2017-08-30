package com.scm.dashboard.persistence.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * table job.
 * 
 * @author l58wang
 */
@Entity
@Table(name = "t_job")
public class TJob implements Serializable {

	private static final long serialVersionUID = 1972354302305632445L;
	
	public TJob() {
		super();
	}
	
	public TJob(String jobName, String path, Long projectId, String projectName, TServer server) {
		super();
		this.jobName = jobName;
		this.path = path;
		this.projectId = projectId;
		this.projectName = projectName;
		this.server = server;
		this.threshold = 24;
		this.watch = true;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "job_name")
	private String jobName;
	
	@Column(name = "path")
	private String path;
	
	@Column(name = "project_id")
	private Long projectId;

	@Column(name = "project_name")
	private String projectName;

	@Column(name = "watch")
	private boolean watch;

	@Column(name = "parse_issue")
	private boolean parseIssue;
	
	@Column(name = "auto_close")
	private boolean autoClose;
	
	@Column(name = "version_count_once")
	private boolean versionCountOnce;

	@Column(name = "threshold")
	private int threshold;

	@Column(name = "is_attach", nullable = false)
	private Integer isAttach;

	@ManyToOne()//fetch=FetchType.LAZY
	@JoinColumn(referencedColumnName = "id",name = "server_id")
	private TServer server;

	@Column(name = "deleted", nullable = false)
	private int deleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public boolean isWatch() {
		return watch;
	}

	public void setWatch(boolean watch) {
		this.watch = watch;
	}

	public boolean isParseIssue() {
		return parseIssue;
	}

	public void setParseIssue(boolean parseIssue) {
		this.parseIssue = parseIssue;
	}
	
	public boolean isVersionCountOnce() {
		return versionCountOnce;
	}

	public void setVersionCountOnce(boolean versionCountOnce) {
		this.versionCountOnce = versionCountOnce;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public Integer getIsAttach() {
		return isAttach;
	}

	public void setIsAttach(Integer isAttach) {
		this.isAttach = isAttach;
	}

	public TServer getServer() {
		return server;
	}

	public void setServer(TServer server) {
		this.server = server;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public boolean isAutoClose() {
		return autoClose;
	}

	public void setAutoClose(boolean autoClose) {
		this.autoClose = autoClose;
	}

	@Override
	public String toString() {
		return "TJob [id=" + id + ", jobName=" + jobName + ", branch=" 
				+ ", server=" +  ", watch=" + watch + ", threshold="
				+ threshold + "]";
	}
}
