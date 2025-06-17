package com.shdata.health.carezy.forms.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 病人护理记录单明细项表  搜索VO
 *
 * @author myy
 * @date 2024-10-21
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormsDetailSearchVo {
    /**
     * 病人ID
     */
    private String brid;
    /**
     * 文书ID
     */
    @NotBlank
    private String wsid;
    /**
     * 文书编码
     */
    private String wsbm;
    /**
     * 文书分类
     */
    private String wsfl;

    /**
     * 页面大小
     */
    private int pageSize = 10;

    /**
     * 当前所在页
     */
    private int pageNum = 1;


}
