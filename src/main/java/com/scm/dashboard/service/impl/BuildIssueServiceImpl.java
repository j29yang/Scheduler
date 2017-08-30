package com.scm.dashboard.service.impl;

import java.util.List;

import com.scm.dashboard.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.scm.dashboard.persistence.domain.TBuildIssue;
import com.scm.dashboard.persistence.domain.TIssues;
import com.scm.dashboard.persistence.domain.TJob;
import com.scm.dashboard.persistence.repo.TBuildIssueRepository;
import com.scm.dashboard.service.BuildIssueService;

/**
 * @author j29yang
 */
@Service
public class BuildIssueServiceImpl implements BuildIssueService {
	
	
	@Autowired
	private TBuildIssueRepository TBuildIssueRepository;


	@Override
	@CacheEvict(value = "issueTotal", key = "#buildIssue.branchId")
	public TBuildIssue saveBuildIssue(TBuildIssue buildIssue) {
		return TBuildIssueRepository.save(buildIssue);
	}
	

	@Override
	public TIssues findStuffedIssueByJob(Long jobId) {
		TIssues issue = null;
		List<TIssues> issues = TBuildIssueRepository.findStuffedIssueByJobId(jobId,
				CommonConstant.ISSUE_DESCRIPTION_DEFAULT, CommonConstant.ISSUE_STATUS_DEFAULT);
		if(!CollectionUtils.isEmpty(issues)) {
			issue = issues.get(0);
		}
		return issue;
	}
	
	@Override
	public TBuildIssue findLastBuildIssue(TIssues issue, Long jobId) {
		return TBuildIssueRepository.findLastRecordByIssueAndJob(issue.getId(), jobId);
	}

}
