package com.scm.dashboard.constant;

/**
 * Created by amqu on 2017/7/11.
 */
public enum IssueStatusEnum {
    NEW(1,"NEW"),
    CLOSED(0,"CLOSED"),
    INVESTIGATION(2,"INVESTIGATION"),
    DUPLICATE(3,"DUPLICATE"),
    ASSIGNED(4,"Assigned to Component");

    private int value;

    private String desc;

    IssueStatusEnum(int value,String desc){
        this.value=value;
        this.desc=desc;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public  IssueStatusEnum getIssueStatusEnum(String desc){
        for(IssueStatusEnum status: IssueStatusEnum.values()){
            if(status.desc.equals(desc)){
                return status;
            }
        }
        return  null;
    }

    public String getIssueStatusEnumDesc(int value){
        for(IssueStatusEnum status: IssueStatusEnum.values()){
            if(status.value == value){
                return status.desc;
            }
        }
        return  null;
    }
}
