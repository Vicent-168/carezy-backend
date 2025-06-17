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
 * 病区大屏汇总表  VO
 *
 * @author ljt
 * @date 2024-10-17
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class WardLargeScreenSummaryVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;
    /** 医疗机构ID */
    @NotBlank
    private String yljgid;
    /** 病区ID */
    @NotBlank
    private String bqid;
    /** 统计日期 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date tjrq;
    /** 注意事项 */
    private String zysx;
    /** 备忘录 */
    private String bwl;
    /** 在院总数 */
    private long zyzs;
    /** 今日入院 */
    private String jrry;
    /** 明日出院 */
    private String mrcy;
    /** 病危 */
    private String bw;
    /** 转床 */
    private String zc;
    /** 青霉素 */
    private String qms;
    /** 头孢类 */
    private String tbl;
    /** 24H尿量 */
    private String eshnl;
    /** 24H出入量 */
    private String eshcrl;
    /** 膀胱冲洗 */
    private String pgcx;
    /** 气垫床 */
    private String qdc;
    /** 导尿管 */
    private String dng;
    /** PICC */
    private String picc;
    /** PORT */
    private String port;
    /** 腹腔引流 */
    private String pqyl;
    /** 一级护理 */
    private String yjhl;
    /** 二级护理 */
    private String ejhl;
    /** 三级护理 */
    private String sjhl;
    /** 特级护理 */
    private String tjhl;
    /** 压力性损伤 */
    private String ylxss;
    /** 跌倒坠床 */
    private String ddzc;
    /** 静脉血栓 */
    private String jmxs;
    /** 管道滑脱 */
    private String gdht;
    /** 获得性肺炎 */
    private String hdxfy;
    /** 导尿管感染 */
    private String dnggr;
    /** 失禁性皮炎 */
    private String sjxpy;
    /** 误吞误吸 */
    private String wtwx;
    /** 24H排尿 */
    private String eshpn;
    /** MEWS预警 */
    private String mewsyj;
    /** 血压QD */
    private String xyqd;
    /** 血压BID */
    private String xybid;
    /** 血压Q8H */
    private String xyq8h;
    /** 血压Q6H */
    private String xyq6h;
    /** 血糖QD */
    private String xtqd;
    /** 血糖BID */
    private String xtbid;
    /** 血糖Q8H */
    private String xtq8h;
    /** 血糖Q6H */
    private String xtq6h;
}
