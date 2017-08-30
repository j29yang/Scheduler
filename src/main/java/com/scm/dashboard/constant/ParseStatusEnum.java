package com.scm.dashboard.constant;

/**
 * Created by amqu on 2017/5/23.
 */
public enum ParseStatusEnum {
    NEW(0,"New record"),PARSING(1,"Under parsing"),SUCCESS(2,"Parse successfully"),FAIL(-1,"Failed in startParseDescription");

    private int value;
    private String desc;


    ParseStatusEnum(int value,String desc){
        this.value=value;
        this.desc=desc;
    }

    public int getValue() {
        return value;
    }

    public ParseStatusEnum getParseStatusEnum(int value){
       for(ParseStatusEnum status: ParseStatusEnum.values()){
           if(status.getValue()==value){
               return status;
           }
       }
       return null;
    }
}
