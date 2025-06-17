package com.shdata.health.carezy.forms.vo;

import com.shdata.health.carezy.common.dictconverter.FormNameFieldConverter;
import com.shdata.health.common.mybatis.NameField;
import com.shdata.health.common.serializer.jackson.DictFormat;
import com.shdata.health.common.serializer.jackson.DictType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class RiskListVo {

    /**
     * 风险等级
     */
    private String fxdj;

    /**
     * 时间
     */
    private String time;

    /**
     * 评估得分
     */
    private int score;
    /**
     * 评估结论
     */
    private String conclusion;
    /**
     * 评估人ID
     */
    @DictFormat(dictType = DictType.User,dictKey = "assessorMc")
    private String assessorId;
    /**
     * 文书编码
     */
    @NameField(type = DictType.CUSTOM, key = "ID", target = "wsName", bean = FormNameFieldConverter.class)
    private String wsid;
    /**
     * 文书名称
     */
    private String wsName;

    /**
     * 评估异常
     */
    private String abnormal;
    /**
     * 风险来源
     */
    private String riskSource;

    /**
     * 主键ID
     */
    private String id;

    /**
     * 监控状态
     */
    private String jkzt;

    /**
     * 文书分类
     */
    private String wsfl;
    /**
     * 文书编码
     */
    private String wsbm;
}
