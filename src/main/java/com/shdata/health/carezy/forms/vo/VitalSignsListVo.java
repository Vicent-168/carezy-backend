package com.shdata.health.carezy.forms.vo;

import com.shdata.health.common.serializer.jackson.DictFormat;
import com.shdata.health.common.serializer.jackson.DictType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class VitalSignsListVo {
    /**索引表主键ID*/
    private String id;
    /**wsid*/
    private String wsid;
    /**测量日期*/
    private Object rq;
    /**测量时间段*/
    @DictFormat(dictType = DictType.DICT, dictKey = "DIC_CLSJD")
    private Object sj;
    /**名称*/
    private String mc;
    /**数值*/
    private Object xmz;
    /**操作人*/
    private Object czr;
}
