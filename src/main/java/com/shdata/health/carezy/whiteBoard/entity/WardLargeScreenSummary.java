package com.shdata.health.carezy.whiteBoard.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shdata.health.common.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 病区大屏汇总表实体 对应表名T_HL_DP_BQDP
 *
 * @author ljt
 * @date 2024-10-17
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("T_HL_DP_BQDP")
public class WardLargeScreenSummary extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId
    private String id;
    /** 医疗机构ID */
    @TableField("YLJGID")
    private String yljgid;
    /** 病区ID */
    @TableField("BQID")
    private String bqid;
    /** 注意事项 */
    @TableField("ZYSX")
    private String zysx;
    /** 备忘录 */
    @TableField("BWL")
    private String bwl;
    /** 在院总数 */
    @TableField("ZYZS")
    private long zyzs;
    /** 今日入院 */
    @TableField("JRRY")
    private String jrry;
    /** 明日出院 */
    @TableField("MRCY")
    private String mrcy;
    /** 病危 */
    @TableField("BW")
    private String bw;
    /** 转床 */
    @TableField("ZC")
    private String zc;
    /** 青霉素 */
    @TableField("QMS")
    private String qms;
    /** 头孢类 */
    @TableField("TBL")
    private String tbl;
    /** 24H尿量 */
    @TableField("ESHNL")
    private String eshnl;
    /** 24H出入量 */
    @TableField("ESHCRL")
    private String eshcrl;
    /** 膀胱冲洗 */
    @TableField("PGCX")
    private String pgcx;
    /** 气垫床 */
    @TableField("QDC")
    private String qdc;
    /** 导尿管 */
    @TableField("DNG")
    private String dng;
    /** PICC */
    @TableField("PICC")
    private String picc;
    /** PORT */
    @TableField("PORT")
    private String port;
    /** 腹腔引流 */
    @TableField("PQYL")
    private String pqyl;
    /** 一级护理 */
    @TableField("YJHL")
    private String yjhl;
    /** 二级护理 */
    @TableField("EJHL")
    private String ejhl;
    /** 三级护理 */
    @TableField("SJHL")
    private String sjhl;
    /** 特级护理 */
    @TableField("TJHL")
    private String tjhl;
    /** 压力性损伤 */
    @TableField("YLXSS")
    private String ylxss;
    /** 跌倒坠床 */
    @TableField("DDZC")
    private String ddzc;
    /** 静脉血栓 */
    @TableField("JMXS")
    private String jmxs;
    /** 管道滑脱 */
    @TableField("GDHT")
    private String gdht;
    /** 获得性肺炎 */
    @TableField("HDXFY")
    private String hdxfy;
    /** 导尿管感染 */
    @TableField("DNGGR")
    private String dnggr;
    /** 失禁性皮炎 */
    @TableField("SJXPY")
    private String sjxpy;
    /** 误吞误吸 */
    @TableField("WTWX")
    private String wtwx;
    /** 24H排尿 */
    @TableField("ESHPN")
    private String eshpn;
    /** MEWS预警 */
    @TableField("MEWSYJ")
    private String mewsyj;
    /** 血压QD */
    @TableField("XYQD")
    private String xyqd;
    /** 血压BID */
    @TableField("XYBID")
    private String xybid;
    /** 血压Q8H */
    @TableField("XYQ8H")
    private String xyq8h;
    /** 血压Q6H */
    @TableField("XYQ6H")
    private String xyq6h;
    /** 血糖QD */
    @TableField("XTQD")
    private String xtqd;
    /** 血糖BID */
    @TableField("XTBID")
    private String xtbid;
    /** 血糖Q8H */
    @TableField("XTQ8H")
    private String xtq8h;
    /** 血糖Q6H */
    @TableField("XTQ6H")
    private String xtq6h;
}
