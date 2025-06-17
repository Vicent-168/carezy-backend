package com.shdata.health.carezy.common.constants;

/**
 * 缓存相关常量
 */
public class Constants {

    /**
     * 已落实
     */
    public static final String YLS = "1";

    /**
     * 未落实
     */
    public static final String WLS = "2";

    /**
     * 完成任务
     */
    public static final String WC_TASK = "1";

    /**
     * 待办任务
     */
    public static final String DB_TASK = "2";


    /**
     * 导管分类代码
     */
    public static final String DG_FLDM = "9";

    /**
     * 一级护理
     */
    public static final String YJHL = "1";
    /**
     * 二级护理
     */
    public static final String EJHL = "2";
    /**
     * 三级护理
     */
    public static final String SJHL = "3";



    /**
     * 导管置管的文书分类
     */
    public static final String DGZG_FLDM = "9";

    /**
     * 胸管
     */
    public static final String XG = "02";
    /**
     * T管
     */
    public static final String TG = "03";
    /**
     * 口鼻插管
     */
    public static final String KBCG = "04";
    /**
     * 气管插管
     */
    public static final String QBCG = "05";
    /**
     * 动静脉插管
     */
    public static final String DJMCG = "06";
    /**
     * 浅静脉留置管
     */
    public static final String QJMLZG = "07";
    /**
     * 引流管
     */
    public static final String YLG = "08";
    /**
     * 负压球
     */
    public static final String FYQ = "10";
    /**
     * 深V导管
     */
    public static final String SVG = "11";
    /**
     * 三腔管
     */
    public static final String SQG = "12";
    /**
     * 造瘘管
     */
    public static final String ZLG = "13";
    /**
     * PICC导管
     */
    public static final String PICCG = "14";
    /**
     * 导尿管
     */
    public static final String DNG = "16";
    /**
     * 外周V置管
     */
    public static final String WZVZG = "17";
    /**
     * 胃管
     */
    public static final String WG = "18";
    /**
     * 氧气管
     */
    public static final String YQG = "19";




    /**
     * 字典类型
     */
    public static final String DICT_TYPE="DICT";

    /**
     * 表格类型
     */
    public static final String TABLE_TYPE="TABLE";

    /**
     * 必传
     */
    public static final String bc = "0";

    /**
     * 选传
     */
    public static final String xt = "1";

    /**
     * 风险等级：无风险
     */
    public static final String wfxdj = "1";

    /**
     * 风险等级：低风险
     */
    public static final String dfxdj = "2";

    /**
     * 风险等级：中风险
     */
    public static final String zfxdj = "3";

    /**
     * 风险等级：高风险
     */
    public static final String gfxdj = "4";

    /**
     * 风险等级：极高风险
     */
    public static final String jgfxdj = "5";

    /**
     * 状态为是
     */
    public static final String yes = "0";
    /**
     * 状态为否
     */
    public static final String no = "1";

    /**
     * 已完成
     */
    public static final String complete = "1";  //已完成
    /**
     * 未完成
     */
    public static final String uncomplete = "0";  //未完成

    /**
     * 未监控
     */
    public static final String wjk = "1";
    /**
     * 已监控
     */
    public static final String yjk = "2";

    /**
     * 无需监控
     */
    public static final String wxjk = "3";

    /**
     *责任护士已监控
     */
    public static final String zrhsyjk = "4";
    /**
     * 护士长已监控
     */
    public static final String hscyjk = "5";
    /**
     * 总护士长已监控
     */
    public static final String zhscyjk = "6";



    /**
     *  责任护士落实情况
     */
    public static final String DutyNurse = "XM_ZRHSLSQK";
    /**
     * 护士长落实情况
     */
    public static final String HeadNurse = "XM_HSZLSQK";
    /**
     * 总护士长落实情况
     */
    public static final String TotalHeadNurse = "XM_ZHSZLSQK";



    /**
     * 健康宣教文书分类
     */
    public static final String JKXJ_WSFL = "8";

    /**
     * 风险评估文书分类
     */
    public static final String FXPG_WSFL = "2";
    /**
     * 生命体征文书分类
     */
    public static final String SMTZ_WSFL = "5";

    /**
     * 护理状态-待护理
     */
    public static final String HLZT_WAIT_NURSING = "2";

    /** 文书分类代码 */

    //入院评估
    public static final String WSFL_RYPG="1";

    //风险评估
    public static final String WSFL_FXPG="2";

    //入院告知
    public static final String WSFL_RYGZ="3";

    //吸氧记录
    public static final String WSFL_XYJL="4";

    //生命体征
    public static final String WSFL_SMTZ="5";

    //血糖监测
    public static final String WSFL_XTJC="6";

    //一般护理
    public static final String WSFL_YBHL="7";

    //健康宣教评估
    public static final String WSFL_JKXJPG="8";

    //导管评估
    public static final String WSFL_DGPG="9";

    //患者转运
    public static final String WSFL_HZZY="10";

    //会诊请求
    public static final String WSFL_HZQQ="11";

    //风险事件
    public static final String WSFL_FXSJ="12";
    /**
     * 护士角色编码
     */
    public static final String ROLE_NURSE = "nurse";
    /**
     * 护士长角色编码
     */
    public static final String ROLE_HEAD_NURSE = "head-nurse";
    /**
     * 体征记录单ID
     */
    public static final String TZJLD_ID="0C5434545907B31103A756D062C85E59";

    /**
     *  入院告知 文书分类代码
     */
    public static final String RYGZ_WSFL="3";
    /**
     * 入院评估文书编码
     */
    public static final String RYPG_WSBM="01";
    /**
     * 体征记录单文书编码
     */
    public static final String TZJLD_WSBM="11";

    /**
     * 血栓风险文书编码
     */
    public static final String XSFX_WSBM="33";

    /**
     * 体征记录单文书编码
     */
    public static final String TIZHENG_WSBM="11";
    /**
     * 出入量记录单文书编码
     */
    public static final String CRL_WSBM="12";
    /**
     * 图章记录单文书编码
     */
    public static final String TUZHENG_WSBM="13";
    /**
     * 过敏记录单文书编码
     */
    public static final String GM_WSBM="14";
    /**
     * 护理措施风险告知书文书编码
     */
    public static final String HLCSFXGZS_WSBM="08";
    /**
     * 住院患者告知书文书编码
     */
    public static final String ZYHZGZS_WSBM="09";
    /**
     * PICC评估文书编码
     */
    public static final String PICC_WSBM="22";

    /**
     * 血糖检测表单ID
     */
    public static final String XTJCBD_ID="1F5434545907B3110F9B15C2F9354C73";

    /**
     * 血糖检测表单文书编码
     */
    public static final String XTJCBD_WSBM="15";
    /**
     * 压疮风险因素评估表单文书编码
     */
    public static final String YCFXBD_WSBM="03";
    /**
     * 跌倒坠床风险因素评估表单文书编码
     */
    public static final String DDZCBD_WSBM="02";
    /**
     * 导管风险因素评估表单文书编码
     */
    public static final String DGFXBD_WSBM="05";
    /**
     * 保护性约束文书编码
     */
    public static final String BHXYS_WSBM="05";
    /**
     * 风险发生文书编码
     */
    public static final String FXFSBD_WSBM="26";

    /**
     * 健康宣教文书分类代码
     */
    public static final String JKXJ_WSFLDM="8";

    /**
     * 入院宣教文书编码
     */
    public static final String RYXJ_WSBM="18";

    /**
     * 住院宣教文书编码
     */
    public static final String ZYXJ_WSBM="19";

    /**
     * 卫生宣教文书编码
     */
    public static final String WSXJ_WSBM="20";

    /**
     * 出院宣教文书编码
     */
    public static final String CYXJ_WSBM="21";
    /**
     * 体温测量部位 1表示耳朵
     */
    public static final String XM_CLTWBW="1";


    public static final String TZTZJLD_WSBM = "13";

    /**
     * 有创治疗分类代码
     */
    public static final String YCZL_FLDM="9";
    /**
     * 老年人误吸文书编码
     */
    public static final String LNRWX_WSBM = "31";
    /**
     * 失禁性皮炎会阴部皮肤评估书编码
     */
    public static final String SJXPYHYBPF_WSBM = "32";
}
