package com.shdata.health.carezy.whiteBoard.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.util.Date;

/**
 * 患者列表视图实体 对应表名V_HL_HZLB
 *
 * @author ljt
 * @date 2024-10-25
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("V_HL_HZLB")
public class PatientListView{

    @Serial
    private static final long serialVersionUID = 1L;

    /** $column.cname */
    @TableField("ID")
    private String id;
    /** $column.cname */
    @TableField("YLJGID")
    private String yljgid;
    /** $column.cname */
    @TableField("YLJG")
    private String yljg;
    /** $column.cname */
    @TableField("KH")
    private String kh;
    /** $column.cname */
    @TableField("KLXMC")
    private String klxmc;
    /** $column.cname */
    @TableField("KLX")
    private String klx;
    /** $column.cname */
    @TableField("XM")
    private String xm;
    /** $column.cname */
    @TableField("XBMC")
    private String xbmc;
    /** $column.cname */
    @TableField("XB")
    private String xb;
    /** $column.cname */
    @TableField("SFZH")
    private String sfzh;
    /** $column.cname */
    @TableField("CSRQ")
    private Date csrq;
    /** $column.cname */
    @TableField("NL")
    private long nl;
    /** $column.cname */
    @TableField("LXDH")
    private String lxdh;
    /** $column.cname */
    @TableField("ZYH")
    private String zyh;
    /** $column.cname */
    @TableField("MZH")
    private String mzh;
    /** $column.cname */
    @TableField("BQID")
    private String bqid;
    /** $column.cname */
    @TableField("BQMC")
    private String bqmc;
    /** $column.cname */
    @TableField("BCH")
    private String bch;
    /** $column.cname */
    @TableField("RYSJ")
    private Date rysj;
    /** $column.cname */
    @TableField("CYSJ")
    private Date cysj;
    /** $column.cname */
    @TableField("ZDID")
    private String zdid;
    /** $column.cname */
    @TableField("HSID")
    private String hsid;
    /** $column.cname */
    @TableField("HSIDXM")
    private String hsidxm;
    /** $column.cname */
    @TableField("YSID")
    private String ysid;
    /** $column.cname */
    @TableField("CWYSXM")
    private String cwysxm;
    /** $column.cname */
    @TableField("SS")
    private String ss;
    /** $column.cname */
    @TableField("HLXMID")
    private String hlxmid;
    /** $column.cname */
    @TableField("HLXMMC")
    private String hlxmmc;
    /** $column.cname */
    @TableField("HLDJ")
    private String hldj;
    /** $column.cname */
    @TableField("HZZT")
    private String hzzt;
    /** $column.cname */
    @TableField("HZZTMC")
    private String hzztmc;
    /** $column.cname */
    @TableField("RYCYZT")
    private String rycyzt;
    /** $column.cname */
    @TableField("DJKSID")
    private String djksid;
    /** $column.cname */
    @TableField("DJYSID")
    private String djysid;
    /** $column.cname */
    @TableField("DJRQ")
    private Date djrq;
    /** $column.cname */
    @TableField("HZSXLX")
    private String hzsxlx;
    /** $column.cname */
    @TableField("HZSXLXMC")
    private String hzsxlxmc;
    /** $column.cname */
    @TableField("ZYTS")
    private long zyts;
    /** $column.cname */
    @TableField("DBRWS")
    private Integer dbrws;
    /** $column.cname */
    @TableField("GM")
    private String gm;
}
