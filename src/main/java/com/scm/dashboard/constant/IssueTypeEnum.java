package com.scm.dashboard.constant;

/**
 * Created by amqu on 2017/7/12.
 */
public enum IssueTypeEnum {
    ACCOUNT(1,"Account issues"),
    CI(2,"CI system"),
    EXTCI(3,"External CI system"),
    EXTTOOL(4,"External tools"),
    HUMAN(5,"Human issues"),
    IT(6,"IT"),
    ERROR(7,"Errors/Faults in deliveries"),
    PROCESS(8,"Process issues"),
    SCRIPTS(9,"Script issues"),
    EXTPARTY(10,"External party");

    private int value;

    private String desc;

    IssueTypeEnum(int value, String desc){
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

    public IssueTypeEnum getIssueTypeEnum(int value){
        for(IssueTypeEnum typeEnum: IssueTypeEnum.values()){
            if(typeEnum.value == value){
                return typeEnum;
            }
        }
        return  null;
    }

    public IssueTypeEnum getIssueTypeEnum(String desc){
        for(IssueTypeEnum typeEnum: IssueTypeEnum.values()){
            if(typeEnum.desc.equals(desc)){
                return typeEnum;
            }
        }
        return  null;
    }

    public int getIssueTypeEnumValue(String desc){
        for(IssueTypeEnum typeEnum: IssueTypeEnum.values()){
            if(typeEnum.desc.equals(desc)){
                return typeEnum.getValue();
            }
        }
        return -1;
    }


    public String getIssueTypeEnumDesc(int value){
        for(IssueTypeEnum typeEnum: IssueTypeEnum.values()){
            if(typeEnum.value == value){
                return typeEnum.desc;
            }
        }
        return  null;
    }
}
