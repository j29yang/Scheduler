package com.scm.dashboard.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.scm.dashboard.persistence.domain.TBranch;

/**
 * Created by amqu on 2017/4/19.
 */
public interface BranchService {
	String getBranchCoordinator(Long branchId);

	Long getBranchCoordinatorId(Long branchId);

	TBranch findById(Long id);

	List<TBranch> findByProjectId(Long id);

	/**
	 * find the build of this job belongs to which branch
	 * @param buildVersion
	 * @param jobId
	 * @return
	 */
	List<Long> getBranchIdsForBuild(String buildVersion,Long jobId);
}
