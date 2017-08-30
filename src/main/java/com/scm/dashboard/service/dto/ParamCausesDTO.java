package com.scm.dashboard.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParamCausesDTO {

	@JsonProperty("upstreamUrl")
	private String upstreamUrl;
	
	@JsonProperty("upstreamBuild")
	private Long upstreamBuild;

	public String getUpstreamUrl() {
		return upstreamUrl;
	}

	public void setUpstreamUrl(String upstreamUrl) {
		this.upstreamUrl = upstreamUrl;
	}

	public Long getUpstreamBuild() {
		return upstreamBuild;
	}

	public void setUpstreamBuild(Long upstreamBuild) {
		this.upstreamBuild = upstreamBuild;
	}

	@Override
	public String toString() {
		return "ParamCausesDto [upstreamUrl=" + upstreamUrl + ", upstreamBuild=" + upstreamBuild + "]";
	}
	

}
