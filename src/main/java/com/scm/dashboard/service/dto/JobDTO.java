package com.scm.dashboard.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * job/${jobname}/api/json接口内的信息
 * @author l58wang
 */
public class JobDTO {

	@JsonProperty("builds")
	private InnerBuildDTO[] builds;

	public InnerBuildDTO[] getBuilds() {
		return builds;
	}

	public void setBuilds(InnerBuildDTO[] builds) {
		this.builds = builds;
	}
}
