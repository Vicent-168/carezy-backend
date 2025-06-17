package com.shdata.health.carezy.patientmanage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shdata.health.common.mybatis.NameField;
import com.shdata.health.common.serializer.jackson.DictType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class RiskBaseInfo {

    private String id;

    private String bch;

    private String zyh;

    private String xm;

    private String nl;
    @NameField(type = DictType.DICT, target = "xb", key = "DIC_XB")
    private String xb;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date rysj;

    private String zyts;

    
    protected String r30;

    protected String r27;

    protected String r31;

    protected String r32;

    protected String r33;

    protected String r07;

    protected String r28;

    protected String r04;

    protected String r03;

    protected String r05;

    protected String r02;

    protected String r06;
}
