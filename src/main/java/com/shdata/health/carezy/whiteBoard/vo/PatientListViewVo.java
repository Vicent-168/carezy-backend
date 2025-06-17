package com.shdata.health.carezy.whiteBoard.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
/**
 * 患者列表视图  VO
 *
 * @author ljt
 * @date 2024-10-25
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PatientListViewVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** $column.cname */
    private String id;
    /** $column.cname */
    private String yljgid;
    /** $column.cname */
    private String yljg;
    /** $column.cname */
    @NotBlank
    private String kh;
    /** $column.cname */
    private String klxmc;
    /** $column.cname */
    @NotBlank
    private String klx;
    /** $column.cname */
    @NotBlank
    private String xm;
    /** $column.cname */
    private String xbmc;
    /** $column.cname */
    private String xb;
    /** $column.cname */
    @NotBlank
    private String sfzh;
    /** $column.cname 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date csrq;
    /** $column.cname */
    private long nl;
    /** $column.cname */
    private String lxdh;
    /** $column.cname */
    private String zyh;
    /** $column.cname */
    private String mzh;
    /** $column.cname */
    private String bqid;
    /** $column.cname */
    private String bqmc;
    /** $column.cname */
    private String bch;
    /** $column.cname 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date rysj;
    /** $column.cname 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date cysj;
    /** $column.cname */
    private String zdid;
    /** $column.cname */
    private String hsid;
    /** $column.cname */
    private String hsidxm;
    /** $column.cname */
    private String ysid;
    /** $column.cname */
    private String cwysxm;
    /** $column.cname */
    private String ss;
    /** $column.cname */
    private String hlxmid;
    /** $column.cname */
    private String hlxmmc;
    /** $column.cname */
    private String hldj;
    /** $column.cname */
    private String hzzt;
    /** $column.cname */
    private String hzztmc;
    /** $column.cname */
    private String rycyzt;
    /** $column.cname */
    private String djksid;
    /** $column.cname */
    private String djysid;
    /** $column.cname 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date djrq;
    /** $column.cname */
    private String hzsxlx;
    /** $column.cname */
    private String hzsxlxmc;
    /** $column.cname */
    private long zyts;
    /** $column.cname */
    private Integer dbrws;
    /** $column.cname */
    private String gm;
}
