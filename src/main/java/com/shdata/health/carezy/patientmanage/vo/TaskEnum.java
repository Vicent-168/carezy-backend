package com.shdata.health.carezy.patientmanage.vo;


import lombok.Getter;

@Getter
public enum TaskEnum {
    TASK_RYPG("1", "入院评估"),
    TASK_ZYHZGZS("10", "住院患者告知书"),
    TASK_HLCSFXGZS("9", "护理措施风险告知书");

    final String code;
    final String name;
    TaskEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

}
