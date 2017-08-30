package com.scm.dashboard.service.dto;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParamWrapperDTO {

	@JsonProperty("parameters")
	private ParamDTO[] paramDTOS;

	@JsonProperty("causes")
	private ParamCausesDTO[] causes;

	public ParamDTO[] getParamDTOS() {
		return paramDTOS;
	}

	public void setParamDTOS(ParamDTO[] paramDTOS) {
		this.paramDTOS = paramDTOS;
	}

	public ParamCausesDTO[] getCauses() {
		return causes;
	}

	public void setCauses(ParamCausesDTO[] causes) {
		this.causes = causes;
	}

	@Override
	public String toString() {
		return "ParamWrapperDto [paramDtos=" + Arrays.toString(paramDTOS) + ", causes=" + Arrays.toString(causes) + "]";
	}
}
