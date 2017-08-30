package com.scm.dashboard.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemDTO {
	
	@JsonProperty("commitId")
	private String commitId;

	public String getCommitId() {
		return commitId;
	}

	public void setCommitId(String commitId) {
		this.commitId = commitId;
	}
	
}
