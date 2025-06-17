package com.shdata.health.carezy.patientmanage.vo;

import lombok.Getter;

@Getter
public enum FormCodeNameEnum {
    BHXYS("30","保护性约束"),
    SKGZ("27","伤口跟踪"),
    LNRWX("31","老年人误吸"),
    SJXPYHYBPF("32","失禁性皮炎会阴部皮肤"),

    JDYY("07","简单营养"),
    KQJG("28","口腔健康"),
    RCSHHD("04","日常生活活动"),
    YCFX("03","压力性损伤风险"),
    DGFX("05","导管风险"),
    XSFX("33","血栓风险"),
    DDZC("02","跌倒坠床风险"),
    LNRHDNL("06","老年人活动能力"),


    BHXYSFS("r30Happen","保护性约束发生"),
    SKGZFS("r27Happen","伤口跟踪发生"),
    LNRWXFS("r31Happen","老年人误吸发生"),
    SJXPYHYBPFFS("r32Happen","失禁性皮炎会阴部皮肤发生"),

    JDYYFS("r07Happen","简单营养发生"),
    KQJGFS("r28Happen","口腔健康发生"),
    RCSHHDFS("r04Happen","日常生活活动发生"),
    YCFXFS("r03Happen","压力性损伤发生"),
    DGFXFS("r05Happen","导管风险发生"),
    XSFXFS("r33Happen","血栓风险发生"),
    DDZCFS("r02Happen","跌倒坠床发生"),
    LNRHDNLFS("r06Happen","老年人活动能力发生");



    private String code;
    private String name;

    FormCodeNameEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 根据code获取name
     * @param code
     * @return
     */
    public static String getNameByCode(String code){
        for (FormCodeNameEnum item : FormCodeNameEnum.values()) {
            if(item.getCode().equals(code)){
                return item.getName();
            }
        }
        return null;
    }
}
