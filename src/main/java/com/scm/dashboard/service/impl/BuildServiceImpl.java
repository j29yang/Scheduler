package com.scm.dashboard.service.impl;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.scm.dashboard.constant.CommonConstant;
import com.scm.dashboard.persistence.domain.TJob;
import com.scm.dashboard.service.BuildService;
import com.scm.dashboard.service.ParseService;
import com.scm.dashboard.service.dto.BuildDTO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.dashboard.persistence.domain.TBuild;
import com.scm.dashboard.persistence.repo.TBuildRepository;

/**
 * 關於build的相關操作
 * 
 * @author l58wang
 */
@Service
public class BuildServiceImpl implements BuildService {
	
	private static final Logger logger = LoggerFactory.getLogger(BuildService.class);
	

	@Autowired
	private TBuildRepository buildRepository;

	@Autowired
	private ParseService parseService;



	@Override
	public TBuild getBuildById(Long buildId){
		return buildRepository.findOne(buildId);
	}

	@Override
	public TBuild getBuildWithIssueById(Long buildId){
		return buildRepository.findOne(buildId);
	}

	@Override
	public TBuild saveBuild(TBuild build) {
		return buildRepository.save(build);
	}

	@Override
	public TBuild updateIssue(TBuild build, Long issueId) {
		build.setIssueId(issueId);
		return buildRepository.save(build);
	}

	@Override
	public List<TBuild> saveBuilds(List<TBuild> builds){
		try {
			return buildRepository.save(builds);
		} catch (Exception e) {
			logger.error("Failed to save builds ",e);
			return null;
		}
	}

	@Override
	public TBuild getLatestFinishBuildByJob(long jobId) {
		List<TBuild> builds = buildRepository.findLatestFinishBuildByJob(jobId);
		if(CollectionUtils.isEmpty(builds)){
			return null;
		}

		Collections.sort(builds, Comparator.comparing(TBuild::getStartTime));
		return builds.get(builds.size()-1);
	}

	@Override
	public TBuild findEarliestInBuildingBuildByJob(long jobId) {
		List<TBuild> builds = buildRepository.findEarliestInBuildingBuildByJob(jobId);
		if(CollectionUtils.isEmpty(builds)){
			return null;
		}
		Collections.sort(builds, Comparator.comparing(TBuild::getStartTime));
		return builds.get(builds.size()-1);
	}

	@Override
	public List<TBuild> getBuildByTime(long jobId, String date) {
		return buildRepository.findBuildByTime(jobId,date);
	}


	@Override
	public List<TBuild> findBuildIsBuilding(long jobId){
		return buildRepository.findTBuildsByJobIdAndBuilding(jobId);
	}

	@Override
	public TBuild findBuildByJobIdAndNumber(long jobId,Long number){
		List<TBuild> builds = buildRepository.findBuildByJobIdAndNumber(jobId,number);
		if(CollectionUtils.isEmpty(builds)){
			return null;
		}

		Collections.sort(builds, Comparator.comparing(TBuild::getStartTime));
		return builds.get(builds.size()-1);
	}

	@Override
	public TBuild findUniqueBuild(long jobId,Long number,Date startTime){
		return buildRepository.findBuildByJobIdAndNumberAndStartTime(jobId,number,startTime);
	}

	@Override
	public TBuild toTBuild(BuildDTO buildDto, TJob job, TBuild build) {
		if(null == build) {
			build = new TBuild();
			build.setJobId(job.getId());
			build.setCreateTime(new Date());
			build.setProjectId(job.getProjectId());
			build.setNumber(buildDto.getNumber());
		}
		build.setStartTime(new Timestamp(buildDto.getTimestamp()));
		build.setStartTimestamp(buildDto.getTimestamp());
		build.setBuilding(buildDto.isBuilding());
		build.setDuration(buildDto.getDuration());
		build.setResult(buildDto.getResult());
		build.setVersion(parseService.parseVersion(buildDto));
		build.setVersionCountOnce(job.isVersionCountOnce());
		build.setDataSource(CommonConstant.DEFAULT_DATA_SOURCE);
		build.setUpstreamUrl(buildDto.getUpstreamUrl());
		build.setUpstreamBuildNum(buildDto.getUpstreamBuild());
		build.setUnifiedCommitId(buildDto.getUnifiedCommitId());
		return build;
	}

	@Override
	public List<Long> findIssueIdsByJobId(long jobId) {
		List<Long> issueIds = buildRepository.findIssueIdsByJobId(jobId);
		return issueIds;
	}
}