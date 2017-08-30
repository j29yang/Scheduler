package com.scm.dashboard.service.dto;

/**
 * show to web page
 * @author yizheng
 */
public class IssueTable {
	
	private Long id;
	
	private String description;
	
	private String status;

    private String issueType;

	private String errorSc;
	
	private String branchName;
	
	private String startTime;
	
	private String closeTime;
	
	private String consoleLog;
	
	private String coordinator ;
	
	private String followUp;
	
	private String version;
	
	private long duration;
	
	private Long duplicate;
	
    private Integer reference;
    
    private long projectId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String Dscrpt) {
		this.description = Dscrpt;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

	public String getErrorSc() {
		return errorSc;
	}
	
	public void setErrorSc(String errorSc) {
		this.errorSc = errorSc;
	}
	
	public String getBranchName() {
		return branchName;
	}
	
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public String getStartTime() {
		return startTime;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getCloseTime() {
		return closeTime;
	}
	
	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}
	
	public String getConsoleLog() {
		return consoleLog;
	}
	
	public void setConsoleLog(String consoleLog) {
		this.consoleLog = consoleLog;
	}
	
	public String getCoordinator() {
		return coordinator;
	}
	
	public void setCoordinator(String coordinator) {
		this.coordinator = coordinator;
	}
	
	public String getFollowUp() {
		return followUp;
	}
	
	public void setFollowUp(String followUp) {
		this.followUp = followUp;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public long getDuration() {
		return duration;
	}
	
	public void setDuration(long duration) {
		this.duration = duration;
	}

	public Long getDuplicate() {
		return duplicate;
	}

	public void setDuplicate(Long duplicate) {
		this.duplicate = duplicate;
	}

	public Integer getReference() {
        return reference;
    }
    
    public void setReference(Integer refNum) {
        this.reference = refNum;
    }

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
}
