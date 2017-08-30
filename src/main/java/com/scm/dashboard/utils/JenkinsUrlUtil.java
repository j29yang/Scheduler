package com.scm.dashboard.utils;

import com.scm.dashboard.persistence.domain.TJob;

/**
 * Created by amqu on 2017/5/23.
 */
public class JenkinsUrlUtil {
    private static final String URL_FORMAT_OF_JOB = "%s/job/%s/api/json";
    private static final String URL_FORMAT_OF_BUILD = "%s/job/%s/%s/api/json";


    public static String getJenkinsLogURL(TJob job,Long buildNum){
        if (null == job.getPath() || job.getPath().isEmpty()) {
            return job.getServer().getAddress() + "job/" + job.getJobName() +
                    "/" + buildNum + "//consoleText";
        }else {
            return job.getServer().getAddress() + job.getPath() + "job/" + job.getJobName() +
                    "/" + buildNum + "//consoleText";
        }

    }

    public static String getJenkinsJobURL(TJob job){
        if (null == job.getPath() || job.getPath().isEmpty()) {
            return String.format(URL_FORMAT_OF_JOB, job.getServer().getAddress(), job.getJobName());
        }else {
            return String.format(URL_FORMAT_OF_JOB, job.getServer().getAddress() + job.getPath(), job.getJobName());
        }

    }

    public static String getJenkinsBuildURL(TJob job,Long buildNum){
        if (null == job.getPath() || job.getPath().isEmpty()) {
            return String.format(URL_FORMAT_OF_BUILD, job.getServer().getAddress(), job.getJobName(), buildNum);
        } else {
            return String.format(URL_FORMAT_OF_BUILD, job.getServer().getAddress() + job.getPath(), job.getJobName(), buildNum);
        }

    }

    public static String getJenkinsBuildURL(TJob job,Long buildNum,String jobName){
        if (null == job.getPath() || job.getPath().isEmpty()) {
            return String.format(URL_FORMAT_OF_BUILD, job.getServer().getAddress(), jobName, buildNum);
        } else {
            return String.format(URL_FORMAT_OF_BUILD, job.getServer().getAddress() + job.getPath(), jobName, buildNum);
        }

    }

}
