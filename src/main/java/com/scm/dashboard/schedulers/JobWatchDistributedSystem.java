package com.scm.dashboard.schedulers;

import com.scm.dashboard.persistence.domain.TJob;
import com.scm.dashboard.persistence.domain.TScirtemServer;
import com.scm.dashboard.service.JobService;
import com.scm.dashboard.service.impl.ScirtemServerService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by amqu on 2017/6/20.
 */
@Service
public class JobWatchDistributedSystem {

    private static final Logger logger = LoggerFactory.getLogger(JobWatchDistributedSystem.class);

    public static Long SERVER_ID = 0L;

    public static Integer SIZE = 0;

    public static Integer ORDER = 0;

    @Autowired
    private ScirtemServerService scirtemServerService;

    @Autowired
    private JobService jobService;


    @Scheduled(fixedDelay = 3 * 60 * 1000)
    public void keepLive() {
        scirtemServerService.updateLiveTime(SERVER_ID);
        logger.info("====================update server live time=====================");
    }

    @Scheduled(fixedDelay = 3 * 60 * 1000)
    public void autoArrangeTask() {
        logger.info("====================Auto arrange job watch task =====================");
        List<TScirtemServer> scirtemServerList =scirtemServerService.getLiveServer();
        if(null == scirtemServerList) {
            return;
        }
        Integer serverNum = scirtemServerList.size();
        logger.info("====================Server total: " + serverNum + " =====================");
        if(serverNum <= 1){
            return;
        } else {
            //sort server by id
            Collections.sort(scirtemServerList, Comparator.comparing(TScirtemServer :: getId));
            //put in map
            Map<Long,Integer> serverOrderMap = new HashMap<>();
            for(int i=0; i< serverNum; i++) {
                serverOrderMap.put(scirtemServerList.get(i).getId(),i);
            }
            //count job number
            List<TJob> jobs = jobService.getJobsNeedToBeWatched();
            if(CollectionUtils.isEmpty(jobs)) {
                return;
            }
            Integer jobNum = jobs.size();
            logger.info("====================Job total: " + jobNum + " =====================");
            calculate(jobNum,serverNum,serverOrderMap);
            logger.info("====================SIZE: " + SIZE + " Page: " + ORDER + " =====================");
        }
    }

    public void addServer(){
        TScirtemServer tScirtemServer = scirtemServerService.addScirtemServer();
        if(null != tScirtemServer) {
            SERVER_ID = tScirtemServer.getId();
        }
        logger.info("====================Add Server id: " + SERVER_ID+ " =====================");
    }

    private void calculate(int jobNum, int serverNum, Map<Long,Integer> serverMap){
        if(jobNum != 0 && serverNum != 0 ){
            ORDER = serverMap.get(SERVER_ID);
            if(null == ORDER){
                ORDER =0;
                return;
            }
            Integer mod = jobNum % serverNum;
            SIZE = jobNum / serverNum;
            if(mod > 0 && ORDER == (serverNum - 1)) {
                SIZE = SIZE + mod;
            }
        }
    }
}
