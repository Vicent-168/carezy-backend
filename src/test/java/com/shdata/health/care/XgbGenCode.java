package com.shdata.health.care;

import com.shdata.health.common.gen.CodeInfo;
import com.shdata.health.common.gen.GenUtil;


public class XgbGenCode {

    public static void main(String[] args) {
        genDicHlxm();
    }
    private static void genDicHlxm() {
        CodeInfo codeInfo = new CodeInfo();
        codeInfo.setPackagePath("com.shdata.health.carezy"); //包路径
        codeInfo.setModule("patientmanage");//模块
        codeInfo.setAuthor("Dingwentao");//作者
        codeInfo.setTableName("T_HL_DIC_WSJGDZB"); //表名
        codeInfo.setClassName("DocumentAgencyCorrespondence"); //java类名
        codeInfo.setEntityName("文书机构对照表"); //实体中文名
        codeInfo.setSearchs("yljgid,wsbm");//搜索字段，多个字段逗号隔开
        codeInfo.setExcel(true); //是否需要Excel导入导出
        codeInfo.setController(true); //是否需要Controller
        GenUtil.genCode(codeInfo);
    }



}
