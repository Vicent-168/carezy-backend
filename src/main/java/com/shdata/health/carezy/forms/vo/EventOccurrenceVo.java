package com.shdata.health.carezy.forms.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
/**
 * 事件发生表  VO
 *
 * @author myy
 * @date 2024-10-21
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class EventOccurrenceVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;
    /** 病人文书索引唯一键 */
    private String blwsid;
    /** 文书ID */
    @NotBlank
    private String wsid;
    /**文书编码**/
    @NotBlank
    private String wsbm;
    /** 病人唯一号 */
    @NotBlank
    private String brid;
    /** 发生时间 日期格式:yyyy-MM-dd */
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date fssj;
    /** 发生事件 */
    private String fsshij;
    /** 事件发生地点 */
    private String sjfsdd;
    /** 严重程度 */
    private String yzcd;
    /** 评估护士 */
    private String pghs;
}
