package com.scm.dashboard.constant;

/**
 * Created by amqu on 2017/6/15.
 */
public enum JobTypeEum {

    PARSE(0,"Parse jenkins log"),FETCH(1,"Fetch build info");

    private int value;
    private String desc;


    JobTypeEum(int value,String desc){
        this.value=value;
        this.desc=desc;
    }

    public int getValue() {
        return value;
    }

    public JobTypeEum getJobTypeEum(int value){
        for(JobTypeEum type: JobTypeEum.values()){
            if(type.getValue()==value){
                return type;
            }
        }
        return null;
    }
}
