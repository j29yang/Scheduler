package com.scm.dashboard.service.impl;

import com.scm.dashboard.persistence.domain.TJob;
import com.scm.dashboard.service.FetchBuildsService;
import com.scm.dashboard.service.JenkinsService;
import com.scm.dashboard.service.dto.BuildDTO;
import com.scm.dashboard.service.dto.InnerBuildDTO;
import com.scm.dashboard.service.dto.JobDTO;
import com.scm.dashboard.utils.JenkinsUrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by amqu on 2017/5/23.
 */
@Service
public class FetchBuildsServiceImpl implements FetchBuildsService {

    private static Logger logger = LoggerFactory.getLogger(FetchBuildsService.class);

    @Autowired
    private JenkinsService jenkinsService;

    /**
     * 取得被监控job，新的或者还未结束的build number（相对于数据库内的信息来说）
     * 场景一：正常情况下比较build number的大小
     * 场景二：切换job后，build number小于之前获取到的number
     * 场景三：切换job后 build number大于之前获取到的number，过滤build时间小的number
     */
    @Override
    public List<BuildDTO> getBuilds(TJob job, long lfCTime) {
        logger.info("Start to fetch builds from jenkins for job id: " + job.getId());
        JobDTO jobDTO = jenkinsService.fetchBuildsForJob(job);
        InnerBuildDTO[] builds = jobDTO.getBuilds();
        List<Long> numList = new ArrayList<>();
        for (InnerBuildDTO build : builds) {
            numList.add(build.getNumber());
        }
        Collections.sort(numList);
        Collections.reverse(numList);
        return getBuildDtos(job, lfCTime, numList);
    }

    /**
     * 根据时间获取有效的build
     */
    private List<BuildDTO> getBuildDtos(TJob job, long lfCTime, List<Long> numList) {
        logger.info("Filter the new builds for job id: " + job.getId());
        List<BuildDTO> buildDtoList = new ArrayList<>();
        for (Long number : numList) {
            String jenkinsBuildUrl = JenkinsUrlUtil.getJenkinsBuildURL(job, number);
            BuildDTO buildDto=jenkinsService.getBuildInfo(jenkinsBuildUrl);
            if (null == buildDto) {
                continue;
            }
            long cTimeOfDto = buildDto.getTimestamp();
            //add the valid builds
            if (cTimeOfDto >=lfCTime) {
                buildDtoList.add(buildDto);
            } else {
                break;
            }
        }
        return buildDtoList;
    }
}
