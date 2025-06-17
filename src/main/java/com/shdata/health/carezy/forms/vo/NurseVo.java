package com.shdata.health.carezy.forms.vo;

import com.shdata.health.common.mybatis.NameField;
import com.shdata.health.common.serializer.jackson.DictFormat;
import com.shdata.health.common.serializer.jackson.DictType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class NurseVo {
    /** 病区护士长 */
    @NameField(type = DictType.User, target = "bqhszxm")
    private String bqhsz;
    /**
     * 病区护士长姓名
     */
    private String bqhszxm;

    /** 病区副护士长 */
    @NameField(type = DictType.User, target = "bqfhszxm")
    private String bqfhsz;
    /**
     * 病区副护士长姓名
     */
    private String bqfhszxm;
}
