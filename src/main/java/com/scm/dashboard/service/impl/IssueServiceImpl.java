/**
 * @author xiaopan
 * @author j29yang
 * @created Aug 25, 2016 9:20:36 AM
 * 
 */
package com.scm.dashboard.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.scm.dashboard.constant.IssueStatusEnum;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scm.dashboard.constant.CommonConstant;
import com.scm.dashboard.persistence.domain.TBuild;
import com.scm.dashboard.persistence.domain.TBuildIssue;
import com.scm.dashboard.persistence.domain.TIssues;
import com.scm.dashboard.persistence.domain.TJenkinsLog;
import com.scm.dashboard.persistence.repo.TIssuesRepository;
import com.scm.dashboard.service.BranchService;
import com.scm.dashboard.service.BuildIssueService;
import com.scm.dashboard.service.BuildService;
import com.scm.dashboard.service.IssueService;
import com.scm.dashboard.vo.MatchingRuleVO;

@Service
public class IssueServiceImpl implements IssueService {
	private static final Logger logger = LoggerFactory.getLogger(IssueService.class);

	
	@Autowired
	private TIssuesRepository issuesRepository;
	
	@Autowired
	private BuildService buildService;

	@Autowired
	private BranchService branchService;
	
	@Autowired
	private BuildIssueService buildIssueService;


	private TIssues checkDuplicatedIssue(String description, String level) {
		List<TIssues> tIssuesList= issuesRepository.checkDuplicatedIssue(description, level);
		if(CollectionUtils.isNotEmpty(tIssuesList)){
			return tIssuesList.get(0);
		}
		return null;
	}

	@Override
	public TIssues addIssue(TJenkinsLog log, MatchingRuleVO rule){
		TBuild build=buildService.getBuildById(log.getBuildId());
		if(rule== null){
			return addDefaultIssue(build,log);
		}else{
			return addParsedIssue(build, log, rule);
		}
	}

	@Transactional
	private TIssues addParsedIssue(TBuild build, TJenkinsLog log, MatchingRuleVO rule) {
		Long jobId= log.getJobId();
		Long branchId=log.getBranchId();
		Long projectId=log.getProjectId();
		String desc = rule.getDescription();
		Integer issueType = rule.getIssueType();
		Integer scId = rule.getScId();
		
		try {
			TIssues duplicatedIssue = checkDuplicatedIssue(desc, CommonConstant.ISSUE_lEVEL_DEFAULT);
			Long coordinatorId = branchService.getBranchCoordinatorId(branchId);

			TIssues issue = new TIssues();
			if(null != duplicatedIssue) {
				issue.setStatus(IssueStatusEnum.DUPLICATE.getValue());
				issue.setDuplicate(duplicatedIssue.getId());
			}else{
				issue.setStatus(CommonConstant.ISSUE_STATUS_DEFAULT);
			}
			issue.setDescription(desc);
			issue.setLevel(CommonConstant.ISSUE_lEVEL_DEFAULT);
			issue.setIssueType(issueType);
			issue.setScId(scId);
			issue.setReference(CommonConstant.ISSUE_REFERENCE_DEFAULT);
			issue.setCreateTime(new Date());
			issue.setWhenHappen(new Timestamp(build.getStartTime().getTime() + build.getDuration()));
			issue.setDataSource(CommonConstant.DEFAULT_DATA_SOURCE);
			issue = this.save(issue);
			if(null != duplicatedIssue){
				updateIssueRefCnt(duplicatedIssue.getId());
			}

			TBuildIssue tbi = new TBuildIssue(issue, projectId, branchId, coordinatorId, jobId, build);
			buildService.updateIssue(build, issue.getId());
			buildIssueService.saveBuildIssue(tbi);
			return issue;
		}catch (Exception e){
			logger.error("Add parsed issue error: ",e);
			return  null;
		}

	}

	@Transactional
	private TIssues addDefaultIssue(TBuild build, TJenkinsLog log) {
		try {
			Long jobId = log.getJobId();
			Long branchId = log.getBranchId();
			Long projectId = log.getProjectId();
			//Create default issue
			TIssues issue = new TIssues();
			issue.setDescription(CommonConstant.ISSUE_DESCRIPTION_DEFAULT);
			issue.setLevel(CommonConstant.ISSUE_lEVEL_DEFAULT);
			issue.setStatus(CommonConstant.ISSUE_STATUS_DEFAULT);
			issue.setReference(CommonConstant.ISSUE_REFERENCE_DEFAULT);
			issue.setDataSource(CommonConstant.DEFAULT_DATA_SOURCE);
			issue.setCreateTime(new Date());
			issue.setWhenHappen(new Timestamp(build.getStartTime().getTime() + build.getDuration()));
			issue = this.save(issue);
			//Create build and issue related record
			Long coordinatorId = branchService.getBranchCoordinatorId(branchId);
			TBuildIssue tbi = new TBuildIssue(issue, projectId, branchId, coordinatorId, jobId, build);

			buildService.updateIssue(build, issue.getId());
			buildIssueService.saveBuildIssue(tbi);
			return issue;
		}catch (Exception e){
			logger.error("Add default issue error: ",e);
			return null;
		}
	}

	
//	@Cacheable(value="issues", key="#id")
	@Override
	public TIssues findOne(Long id) {
		return issuesRepository.findOne(id);
	}
	
//	@CachePut(value="issues", key="#issue.id")
	@Override
	public TIssues save(TIssues issue) {
		return issuesRepository.save(issue);
	}

	@Override
	public synchronized void updateIssueRefCnt(Long id) {
		issuesRepository.updateIssueRefCnt(id);
	}
	
}

