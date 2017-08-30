package com.scm.dashboard.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParamDTO {

	@JsonProperty("name")
	private String name;

	@JsonProperty("value")
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ParamDTO [name=" + name + ", value=" + value + "]";
	}
}
