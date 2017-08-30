package com.scm.dashboard.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * job/${jobname}/api/json接口中展示的build信息。
 * @author l58wang
 */
public class InnerBuildDTO {

	@JsonProperty("number")
	private long number;

	@JsonProperty("url")
	private String url;

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
