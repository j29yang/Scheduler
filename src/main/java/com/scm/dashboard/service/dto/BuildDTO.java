package com.scm.dashboard.service.dto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BuildDTO {

	@JsonProperty("number")
	private long number;

	@JsonProperty("result")
	private String result;

	@JsonProperty("timestamp")
	private Long timestamp;

	@JsonProperty("duration")
	private long duration;

	@JsonProperty("building")
	private boolean building;

	@JsonProperty("actions")
	private ParamWrapperDTO[] paramWrapperDTO;

	@JsonProperty("description")
	private String desc;

	@JsonProperty("url")
	private String url;
	
	@JsonProperty("changeSet")
	private ChangeSetDTO changeSet;

	private Map<String, String> parameters = new HashMap<>();

	private String upstreamUrl;

	private Long upstreamBuild;
	
	private String unifiedCommitId;

	public ParamWrapperDTO[] getParamWrapperDTO() {
		return paramWrapperDTO;
	}

	public void setParamWrapperDTO(ParamWrapperDTO[] actions) {
		boolean flag = false;
		for (ParamWrapperDTO action : actions) {
			if (!flag) {
				ParamCausesDTO[] causes = action.getCauses();
				if (null != causes && null != causes[0]) {
					this.upstreamUrl = causes[0].getUpstreamUrl();
					this.upstreamBuild = causes[0].getUpstreamBuild();
					flag = true;
				}
			}
			ParamDTO[] paramDTOS = action.getParamDTOS();
			if (paramDTOS != null) {
				for (ParamDTO paramDTO : paramDTOS) {
					parameters.put(paramDTO.getName(), paramDTO.getValue());
				}
			}
		}
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public boolean  isBuilding() {
		return building;
	}

	public void setBuilding(boolean building) {
		this.building = building;
	}

	public String getParamVal(String paramKey) {
		return parameters.get(paramKey);
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

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
	
	public ChangeSetDTO getChangeSet() {
		return changeSet;
	}

	public void setChangeSet(ChangeSetDTO changeSet) {
		if(null == changeSet || null == changeSet.getItemDTOS()) {
			return;
		}
		if (changeSet.getItemDTOS().length >= 1) {
			this.unifiedCommitId = changeSet.getItemDTOS()[0].getCommitId();
		}
	}

	public String getUnifiedCommitId() {
		return unifiedCommitId;
	}

	@Override
	public String toString() {
		return "BuildDTO [number=" + number + ", result=" + result + ", timestamp=" + timestamp + ", duration="
				+ duration + ", building=" + building + ", paramWrapperDTO=" + Arrays.toString(paramWrapperDTO)
				+ ", desc=" + desc + ", url=" + url + ", changeSet=" + changeSet + ", parameters=" + parameters
				+ ", upstreamUrl=" + upstreamUrl + ", upstreamBuild=" + upstreamBuild + ", unifiedCommitId="
				+ unifiedCommitId + "]";
	}

}
