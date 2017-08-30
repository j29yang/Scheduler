package com.scm.dashboard.service.impl;

import com.scm.dashboard.service.JenkinsService;
import com.scm.dashboard.service.dto.BuildDTO;
import com.scm.dashboard.utils.JenkinsUrlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.scm.dashboard.persistence.domain.TJob;
import com.scm.dashboard.service.dto.JobDTO;

/**
 * Jenkins 接口服务。
 * @author l58wang
 */
@Service
public class JenkinsServiceImpl implements JenkinsService {
	private static final Logger logger = LoggerFactory.getLogger(JenkinsServiceImpl.class);

	private Netty4ClientHttpRequestFactory httpRequestFactory = new Netty4ClientHttpRequestFactory();

	{
		httpRequestFactory.setReadTimeout(80 * 1000);
		httpRequestFactory.setConnectTimeout(45 * 1000);
		httpRequestFactory.setMaxResponseSize(1024 * 1024 * 100);
	}

	@Autowired
	private ClientHttpRequestFactory httpComponentsClientHttpRequestFactory;

	@Override
	public JobDTO fetchBuildsForJob(TJob job){
		String url = JenkinsUrlUtil.getJenkinsJobURL(job);
		JobDTO jobDTO;
		try {
			jobDTO = newRestTemplate().getForObject(url, JobDTO.class);
		} catch (Exception e) {
			logger.error("jobId=" + job.getId() + ", request jenkins job error url: " + url, e);
			return null;
		}
		return jobDTO;
	}


	@Override
	public String fetchJenkinsConsoleLog(String logUrl) throws Exception {
		return newRestTemplate().getForObject(logUrl, String.class);
	}

	private RestTemplate newRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(httpComponentsClientHttpRequestFactory);
		return restTemplate;
	}

	@Override
	public BuildDTO getBuildInfo(String jenkinsBuildUrl){
		BuildDTO buildDto;
		try {
			buildDto = newRestTemplate().getForObject(jenkinsBuildUrl, BuildDTO.class);
			return buildDto;

		} catch (Exception e) {
			logger.error("request jenkins build error url: " + jenkinsBuildUrl, e);
			return null;
		}
	}

}