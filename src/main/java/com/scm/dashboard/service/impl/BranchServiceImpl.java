package com.scm.dashboard.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.scm.dashboard.persistence.domain.TJobBranchRel;
import com.scm.dashboard.persistence.domain.TJobStage;
import com.scm.dashboard.service.JobService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.dashboard.persistence.domain.TBranch;
import com.scm.dashboard.persistence.domain.TCoordinator;
import com.scm.dashboard.persistence.repo.TBranchRepository;
import com.scm.dashboard.persistence.repo.TCoordinatorRepository;
import com.scm.dashboard.service.BranchService;

/**
 * @author j29yang
 * 
 */
@Service
public class BranchServiceImpl implements BranchService {

	@Autowired
	private TBranchRepository branchRepository;
	
	@Autowired
	private TCoordinatorRepository coordinatorRepository;

	@Autowired
	private JobService jobService;

	@Autowired
	private JobSatgeService jobSatgeService;
	
	@Override
	public String getBranchCoordinator(Long branchId){
		TBranch tBranch = branchRepository.findById(branchId);
		if(null == tBranch){
			return "";
		}
		Long coordinatorId=tBranch.getCoordinatorId();
		if(0l==coordinatorId){
		    return "";
        }
		TCoordinator tCoordinator= coordinatorRepository.findOne(coordinatorId);
		if(null!=tCoordinator){
			return tCoordinator.getCoordinator();
		}
		return "";
	}

	@Override
	public Long getBranchCoordinatorId(Long branchId){
		TBranch tBranch = branchRepository.findById(branchId);
		if(null == tBranch){
			return 0L;
		}
		return tBranch.getCoordinatorId();
	}
	@Override
	public TBranch findById(Long id) {
		return branchRepository.findOne(id);
	}
	@Override
	public List<TBranch> findByProjectId(Long id) {
		return branchRepository.findByProjectId(id);
	}

	/**
	 * find the build of this job belongs to which branch
	 * @param buildVersion
	 * @param jobId
	 * @return
	 */
	@Override
	public List<Long> getBranchIdsForBuild(String buildVersion,Long jobId){
		List<Long> branchList =new ArrayList<>();
		List<TJobBranchRel> jobBranchRelList = jobService.getJobBranchRels(jobId);
		if(CollectionUtils.isEmpty(jobBranchRelList)){
			return null;
		}
		// There are more than one branches use this job, so it's a common job
		if(jobBranchRelList.size()>1){
			List<TJobStage> jobStageList = jobSatgeService.findByJobId(jobId);
			for (TJobStage jobStage : jobStageList) {
				String versionRegex = jobStage.getVersionRegex();
				if (null == buildVersion || null == versionRegex || buildVersion.contains(versionRegex)) {
					branchList.add(jobStage.getBranchId());
				}
			}
		}else{
			branchList.add(jobBranchRelList.get(0).getBranchId());
		}
		return branchList;
	}
}
