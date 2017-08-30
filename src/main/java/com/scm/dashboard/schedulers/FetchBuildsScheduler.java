package com.scm.dashboard.schedulers;


import com.scm.dashboard.persistence.domain.TJob;
import com.scm.dashboard.service.JobService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.scm.dashboard.service.MonitorService;

import java.sql.Timestamp;
import java.util.*;

/**
 * 定时从Jenkins上fetch相关的build信息。
 * 
 * @author l58wang
 */
@Service
public class FetchBuildsScheduler {

	private static final Logger logger =  LoggerFactory.getLogger(FetchBuildsScheduler.class);

	@Autowired
	private MonitorService monitorService;

	@Autowired
	private JobService jobService;

	/**
	 * 定时从Jenkins上查找新的builds，针对需要监控的job和branch。 目前为TDDLTE项目的需求。
	 */
	@Scheduled(fixedDelay = 1*60*1000)
	public void fetchBuilds() {
		logger.info("====================start to fetch build info=====================");
		List<TJob> jobs;
		if(JobWatchDistributedSystem.SIZE == 0){
			logger.info("====================Get all the jobs=====================");
			jobs = jobService.getJobsNeedToBeWatched();
		}else{
			jobs = jobService.getJobPage(JobWatchDistributedSystem.ORDER, JobWatchDistributedSystem.SIZE);
			logger.info("====================Get job count：" +jobs.size()+"=====================");
			if(CollectionUtils.isNotEmpty(jobs)){
				logger.info("====================Start jobId: "+ jobs.get(0).getId()+"=====================");
			}

		}
		monitorService.fetchBuilds(jobs);

	}

}
