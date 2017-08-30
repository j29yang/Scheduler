package com.scm.dashboard.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.scm.dashboard.persistence.domain.TJobBranchRel;
import com.scm.dashboard.persistence.repo.JobBranchRelRepository;
import com.scm.dashboard.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.scm.dashboard.persistence.domain.TJob;
import com.scm.dashboard.persistence.repo.TJobRepository;

@Service
public class JobServiceImpl implements JobService {

	@Autowired
	private TJobRepository jobRepository;

	@Autowired
	private JobBranchRelRepository jobBranchRelRepository;

	/**
	 * 取得需要被监控的job列表。
	 * @return
	 */
	public List<TJob> getJobsNeedToBeWatched(){
		return jobRepository.findTJobsByWatchIsTrue();
	}

	@Override
	public TJob getJobById(Long jobId){
		return jobRepository.findOne(jobId);
	}

	@Override
	public List<TJob> getJobByBranchId(Long branchId){
		List<TJobBranchRel> jobBranchRelList = jobBranchRelRepository.findByBranchId(branchId);
		List<TJob> jobList = new ArrayList<>();
		for (TJobBranchRel tJobBranchRel : jobBranchRelList){
			jobList.add(tJobBranchRel.getJob());
		}
		return jobList;
	}


	@Override
	public List<TJob> getJobPage(int page, int size){
		Pageable pageable = new PageRequest(page,size);
		Page<TJob> jobPage = jobRepository.findWatchTJobsByPaging(pageable);
		return jobPage.getContent();
	}


	@Override
	public TJob findByJobNameAndProjectId(String jobName, Long prjectId,Long serverId) {
		return jobRepository.findByJobNameAndprojectId(jobName, prjectId,serverId);
	}

	@Override
	public TJob save(TJob job) {
		return jobRepository.save(job);
	}

	@Override
	public List<TJobBranchRel> getJobBranchRels(Long jobId){
		return jobBranchRelRepository.findByJobId(jobId);
	}
}
