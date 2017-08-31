package com.scm.dashboard.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.dashboard.constant.CommonConstant;
import com.scm.dashboard.constant.ParseStatusEnum;
import com.scm.dashboard.persistence.domain.TIssueMatchingRule;
import com.scm.dashboard.persistence.domain.TIssues;
import com.scm.dashboard.persistence.domain.TJenkinsLog;
import com.scm.dashboard.service.IssueMatchingRuleService;
import com.scm.dashboard.service.IssueService;
import com.scm.dashboard.service.JenkinsLogService;
import com.scm.dashboard.service.JenkinsService;
import com.scm.dashboard.service.ParseService;
import com.scm.dashboard.service.dto.BuildDTO;
import com.scm.dashboard.vo.MatchingRuleVO;

/**
 * Created by amqu on 2017/5/23.
 */
@Service
public class ParseServiceImpl implements ParseService {

    private static final Logger logger =  LoggerFactory.getLogger(ParseService.class);

    @Autowired
    private IssueService issueService;

    @Autowired
    private JenkinsService jenkinsService;

    @Autowired
    private JenkinsLogService jenkinsLogService;

    @Autowired
    private IssueMatchingRuleService issueMatchingRuleService;

    @Override
    public void startParseDescription(TJenkinsLog jenkinsLog) {
        if (null == jenkinsLog) {
            return;
        }
        logger.info("===============Start to parse description=============");
        Long jenkinsLogId = jenkinsLog.getId();
        Integer logSize=0;
        try {
            //3.请求获取Jenkins console log
            logger.info("Fetch jenkins log for job: " + jenkinsLog.getJobId() + " build Number: " + jenkinsLog.getBuildNum());
            String consoleLog = jenkinsService.fetchJenkinsConsoleLog(jenkinsLog.getJenkinsUrl());
            if(null == consoleLog){
            	logger.info("id="+jenkinsLog.getId()+", build_id="+jenkinsLog.getBuildId()+", parsing failed, empty consoleLog");
                jenkinsLogService.updateStatus(jenkinsLogId,ParseStatusEnum.FAIL, ParseStatusEnum.PARSING);
                return;
            }
            //4.解析console log
			logSize = consoleLog.length();
			logger.info("Parse console log id:" + jenkinsLogId+", size: " + logSize);
            MatchingRuleVO rule = parseDescription(consoleLog, jenkinsLog.getProjectId());

            //5.添加issue信息
            TIssues issues = issueService.addIssue(jenkinsLog, rule);
            if(null == issues){
            	logger.info("id="+jenkinsLog.getId()+", build_id="+jenkinsLog.getBuildId()+", parsing failed, adding issues exception");
                jenkinsLogService.updateStatusAndSize(jenkinsLogId,ParseStatusEnum.FAIL, ParseStatusEnum.PARSING,logSize);
                return;
            }
            //6.释放资源，更新标志位
            int ret2 = jenkinsLogService.updateSuccess(jenkinsLogId,issues.getId(),logSize);
            if (ret2 <= 0) {
            	logger.info("id="+jenkinsLog.getId()+", build_id="+jenkinsLog.getBuildId()+", parsing failed, ret <=0");
                jenkinsLogService.updateStatusAndSize(jenkinsLogId,ParseStatusEnum.FAIL, ParseStatusEnum.PARSING,logSize);
                return;
            }
            logger.info("===============Successfully parsed: " + jenkinsLog.getJobId() + " build Number: " + jenkinsLog.getBuildNum() + "=============");
        } catch (Exception ex) {
        	logger.info("id="+jenkinsLog.getId()+", build_id="+jenkinsLog.getBuildId()+", parsing failed, other exception");
            logger.error("===========Failed to parse desc for jenkins log id: " +jenkinsLogId+ "============ ",ex);
            jenkinsLogService.updateStatusAndSize(jenkinsLogId,ParseStatusEnum.FAIL, ParseStatusEnum.PARSING,logSize);
        } finally {
            logger.info("===============Successfully exited: " + jenkinsLog.getJobId() + " build Number: " + jenkinsLog.getBuildNum() + "=============");
        }
    }

    @Override
    public TJenkinsLog getNewJenkinsLog(){
        //1.查询是否有新的待解析log
        TJenkinsLog jenkinsLog = jenkinsLogService.getOneNewLog();

       // TJenkinsLog jenkinsLog = jenkinsLogService.getOneNewLog(100l);
        if (null == jenkinsLog) {
            return null;
        }
        //2.判断该记录是否已被抢占
        int ret = jenkinsLogService.updateStatus(jenkinsLog.getId(),ParseStatusEnum.PARSING, ParseStatusEnum.NEW);
        if (ret <= 0) {
            return null;
        }
        return jenkinsLog;
    }

    /**
     * GET VERSION INFO FROM JENKINS
     */
    @Override
    public String parseVersion(BuildDTO build) {
        try {
            Pattern pattern = Pattern.compile(".*([\\w\\d]+_[\\w\\d_]?[\\d]+_[\\d]+_[\\d]+).*");

            String baseline = build.getParamVal("baseline");
            String lteRlable = build.getParamVal("LTE_RLABEL");

            if (StringUtils.isNotEmpty(baseline)) {
                // 从param的baseline里面取
                return baseline;
            } else if (StringUtils.isNotEmpty(lteRlable)) {
                // 从param的LTE_RLABEL里面取
                return lteRlable;
            } else {
                // 如果param里面不能取到结果，那么就从desc中取。
                String desc = build.getDesc();
                if (desc != null) {
                    Matcher matcher = pattern.matcher(desc);
                    if (matcher.find()) {
                        return matcher.group(1);
                    }
                }
            }
            // 如果都不能取到，而且job不处于运行状态，那么就从console中取。
            if (!build.isBuilding()) {
                String jenkinsLogUrl =build.getUrl()+"/consoleText";
                return parseVersionFromLog(jenkinsLogUrl);
            }
        }catch (Throwable e){
            logger.error("Parse build version error",e);
        }
        // 在都不能取到的情况下，就返回null。
        return CommonConstant.DEFAULT_VERSION;
    }


    @Override
    public String parseVersion(String consoleLog){
        Pattern pattern = Pattern.compile("ScirtemVersion=(.*)");
        Matcher matcher = pattern.matcher(consoleLog);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return CommonConstant.DEFAULT_VERSION;

    }

    @Override
    public MatchingRuleVO parseDescription(String consoleLog, Long projectId) {
        //Check if there's the rules for this project
    	List<TIssueMatchingRule> regexList = issueMatchingRuleService.getMatchingRule(projectId);
    	MatchingRuleVO matchingRule = new MatchingRuleVO();
    	logger.info("the log need to parse: "+consoleLog);
    	long start = System.currentTimeMillis();
    	for(TIssueMatchingRule rule : regexList){
    		String regex = rule.getRegex();
    		String desc = rule.getDescription();
    		logger.info("start to parse by the regex: "+ regex);
            try {
            	String parsingResult = "";

            	parsingResult = parsing(consoleLog, regex);
            	//parse succeed
                if (parsingResult != null && !parsingResult.isEmpty()) {
                	logger.info("parse successfully by the regex: "+ regex);
                	if(desc == null || desc.isEmpty()){
                		matchingRule.setDescription(parsingResult);
                	}else{
                		matchingRule.setDescription(desc);
                	}
                	matchingRule.setScId(rule.getComponent()==0? null:rule.getComponent());
                	matchingRule.setIssueType(rule.getIssueType());
                	return matchingRule;
                }else{
                	logger.info("parse fails by the regex: "+ regex);
                	continue;
                }
            } catch (Exception e) {
                logger.error("parse jenkins log exception: ",e);
                return null;
            }
    	}
        long end = System.currentTimeMillis();
        logger.info("The time of description parse from log: "+ (end - start));
        return null;
    }

    public String simpleParsing(String consoleLog, String regex){
    	Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(consoleLog);
        if (m.find()) {
        	return m.group(1);
        }else{       	
        	return "";
        }
        
    }
    
    private String parsing(String consoleLog, String regex){
        Pattern p = Pattern.compile(regex);
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(consoleLog.getBytes())));

        String[] arrays = br.lines().parallel().filter(l ->  p.matcher(l).find()).distinct().toArray(String[]::new);
        StringBuffer sb = new StringBuffer();
        Arrays.stream(arrays).forEach(item -> sb.append(item).append("\n"));
        return sb.toString();
    }



//    public String parseSC(TJob job,String desc){
//        String scName = scMatchingRuleService.parseScName(scMatchingRuleService.getScRuleMap(job.getProjectName()), desc);
//		if (null == scName) {
//            scName = CommonConstant.ISSUE_SC_NAME_DEFAULT;
//        }
//		TScName sc = scNameService.findByName(scName);
//		if (null == sc) {
//			sc = scNameService.addScName(scName, job.getProjectId());
//		}
//		return scName;
//    }


    /**
     * 当Job运行结束，但是从parameter和description都取不到version，那么需要从console中获取。
     */
    private String parseVersionFromLog(String jenkinsLogUrl) throws Exception{
        String consoleLog = jenkinsService.fetchJenkinsConsoleLog(jenkinsLogUrl);
        if (consoleLog == null) {
            return null;
        }
        return parseVersion(consoleLog);
    }

	@Override
	public String parseCommitId(BuildDTO build) {
		String unifiedCommitId = build.getUnifiedCommitId(); 
		if(null == unifiedCommitId) {
			try {
				Pattern pattern = Pattern.compile(".*ScirtemCommitId=([A-Za-z0-9]+).*");
				String desc = build.getDesc();
				if (desc != null) {
				    Matcher matcher = pattern.matcher(desc);
				    if (matcher.find()) {
				    	unifiedCommitId = matcher.group(1);
				    }
				} else if (!build.isBuilding()) {
				    String jenkinsLogUrl =build.getUrl()+"/consoleText";
				    unifiedCommitId = parseCommitIdFromLog(jenkinsLogUrl);
				}
				
			} catch (Throwable e) {
				logger.error("Parse build version error",e);
			}
		}
		return unifiedCommitId;
	}
	
	@Override
    public String parseCommitId(String consoleLog){
        Pattern pattern = Pattern.compile("ScirtemCommitId=(.*)");
        Matcher matcher = pattern.matcher(consoleLog);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
	
	private String parseCommitIdFromLog(String jenkinsLogUrl) throws Exception{
        String consoleLog = jenkinsService.fetchJenkinsConsoleLog(jenkinsLogUrl);
        if (consoleLog == null) {
            return null;
        }
        return parseCommitId(consoleLog);
    }
}
