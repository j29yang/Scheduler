package com.scm.dashboard.schedulers;

import com.scm.dashboard.persistence.domain.TJenkinsLog;
import com.scm.dashboard.service.ParseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * 实时解析build失败的jenkins log
 * Created by amqu on 2017/5/23.
 */
@Service
public class ParseJenkinsLogJob {
    private static final Logger logger =  LoggerFactory.getLogger(ParseJenkinsLogJob.class);

    @Autowired
    private ParseService parseService;

    @Autowired
    private ThreadPoolManager threadPoolManager;

    private static boolean isStop = false;

    /**
     *开始消费jenkins log，直到出现stop.txt文件
     */
    public void comsume(){
        checkStopFile();
        while(!isStop) {
            TJenkinsLog jenkinsLog = parseService.getNewJenkinsLog();
            if(null == jenkinsLog){
                continue;
            }
            threadPoolManager.putThread(new ParseThread(jenkinsLog, parseService));
        }
    }

    private void checkStopFile() {
        File stopFile=new File("/stop.txt");
        if(stopFile.exists()){
            this.isStop=true;
        }
    }

    class ParseThread implements Runnable {

        private TJenkinsLog jenkinsLog;
        private ParseService parseService;

        ParseThread(TJenkinsLog jenkinsLog,ParseService parseService){
            this.jenkinsLog=jenkinsLog;
            this.parseService = parseService;
        }

        @Override
        public void run() {
            parseService.startParseDescription(jenkinsLog);
        }
    }

}
