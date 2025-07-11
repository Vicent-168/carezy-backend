CREATE TABLE T_HL_YW_BRDJXX (
ID VARCHAR(32) NOT NULL
, YLJGID VARCHAR(32)
, KH VARCHAR(30) NOT NULL
, KLX VARCHAR(2) NOT NULL
, XM VARCHAR(32) NOT NULL
, XB VARCHAR(2)
, SFZH VARCHAR(32) NOT NULL
, CSRQ DATETIME(0)
, LXDH VARCHAR(32)
, JZMZH VARCHAR(64)
, JZKSID VARCHAR(32)
, JZYSID VARCHAR(32)
, JZSJ DATETIME(0)
, ZDID VARCHAR(32)
, QYJG VARCHAR(32)
, QYYS VARCHAR(32)
, QYRQ DATETIME(0)
, MBGLDX VARCHAR(200)
, HLLX VARCHAR(32)
, HLZT VARCHAR(2)
, DJRQ DATETIME(0)
, DEL_FLAG VARCHAR(2) DEFAULT '0' NOT NULL
, CREATE_BY VARCHAR(32)
, CREATE_TIME DATETIME(0) DEFAULT SYSDATE NOT NULL
, UPDATE_BY VARCHAR(32)
, UPDATE_TIME DATETIME(0) DEFAULT SYSDATE NOT NULL
, REMARKS VARCHAR(255)
);
ALTER TABLE T_HL_YW_BRDJXX ADD CONSTRAINT  T_HL_YW_BRDJXX_PK PRIMARY KEY (id);
CREATE INDEX T_HL_YW_BRDJXX_IDX1 ON T_HL_YW_BRDJXX(yljgid, sfzh);
CREATE CLUSTER INDEX T_HL_YW_BRDJXX_CLS ON T_HL_YW_BRDJXX(yljgid, kh, klx);
COMMENT ON COLUMN T_HL_YW_BRDJXX.ID IS '主键ID';
COMMENT ON COLUMN T_HL_YW_BRDJXX.YLJGID IS '医疗机构ID';
COMMENT ON COLUMN T_HL_YW_BRDJXX.KH IS '卡号';
COMMENT ON COLUMN T_HL_YW_BRDJXX.KLX IS '卡类型';
COMMENT ON COLUMN T_HL_YW_BRDJXX.XM IS '姓名';
COMMENT ON COLUMN T_HL_YW_BRDJXX.XB IS '性别';
COMMENT ON COLUMN T_HL_YW_BRDJXX.SFZH IS '身份证号';
COMMENT ON COLUMN T_HL_YW_BRDJXX.CSRQ IS '出身日期';
COMMENT ON COLUMN T_HL_YW_BRDJXX.LXDH IS '联系电话';
COMMENT ON COLUMN T_HL_YW_BRDJXX.JZMZH IS '就诊门诊号';
COMMENT ON COLUMN T_HL_YW_BRDJXX.JZKSID IS '就诊科室ID';
COMMENT ON COLUMN T_HL_YW_BRDJXX.JZYSID IS '就诊医生ID';
COMMENT ON COLUMN T_HL_YW_BRDJXX.JZSJ IS '就诊时间';
COMMENT ON COLUMN T_HL_YW_BRDJXX.ZDID IS '诊断ID';
COMMENT ON COLUMN T_HL_YW_BRDJXX.QYJG IS '签约机构名称';
COMMENT ON COLUMN T_HL_YW_BRDJXX.QYYS IS '签约医生';
COMMENT ON COLUMN T_HL_YW_BRDJXX.QYRQ IS '签约日期';
COMMENT ON COLUMN T_HL_YW_BRDJXX.MBGLDX IS '慢病管理对象';
COMMENT ON COLUMN T_HL_YW_BRDJXX.HLLX IS '护理类型';
COMMENT ON COLUMN T_HL_YW_BRDJXX.HLZT IS '护理状态';
COMMENT ON COLUMN T_HL_YW_BRDJXX.DJRQ IS '登记日期';
COMMENT ON COLUMN T_HL_YW_BRDJXX.DEL_FLAG IS '删除标志';
COMMENT ON COLUMN T_HL_YW_BRDJXX.CREATE_BY IS '创建人';
COMMENT ON COLUMN T_HL_YW_BRDJXX.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN T_HL_YW_BRDJXX.UPDATE_BY IS '更新人';
COMMENT ON COLUMN T_HL_YW_BRDJXX.UPDATE_TIME IS '更新时间';
COMMENT ON COLUMN T_HL_YW_BRDJXX.REMARKS IS '备注';
COMMENT ON TABLE T_HL_YW_BRDJXX IS '护理病人登记信息';
