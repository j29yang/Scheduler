package com.scm.dashboard.service.dto;

import com.scm.dashboard.persistence.domain.TBuild;
import com.scm.dashboard.persistence.domain.TJob;

/**
 * @author j29yang
 *
 */
public class JobBuildDTO {
	
	private TJob job;
	
	private TBuild build;
	
	public JobBuildDTO() {
		super();
	}

	public JobBuildDTO(TJob job, TBuild build) {
		super();
		this.job = job;
		this.build = build;
	}

	public TJob getJob() {
		return job;
	}

	public void setJob(TJob job) {
		this.job = job;
	}

	public TBuild getBuild() {
		return build;
	}

	public void setBuild(TBuild build) {
		this.build = build;
	}

	@Override
	public String toString() {
		return "JobBuild [job=" + job + ", build=" + build + "]";
	}

}
