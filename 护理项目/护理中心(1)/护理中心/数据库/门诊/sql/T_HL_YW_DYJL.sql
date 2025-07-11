CREATE TABLE T_HL_YW_DYJL (
ID VARCHAR(32) NOT NULL
, BLWSID VARCHAR(32) NOT NULL
, BRID VARCHAR(32) NOT NULL
, WSID VARCHAR(32) NOT NULL
, YLJGID VARCHAR(32) NOT NULL
, KSID VARCHAR(32) NOT NULL
, RQ DATETIME(0)
, YSID VARCHAR(32)
, DEL_FLAG VARCHAR(2) DEFAULT '0' NOT NULL
, CREATE_BY VARCHAR(32)
, CREATE_TIME DATETIME(0) DEFAULT SYSDATE NOT NULL
, UPDATE_BY VARCHAR(32)
, UPDATE_TIME DATETIME(0) DEFAULT SYSDATE NOT NULL
, REMARKS VARCHAR(255)
);
ALTER TABLE T_HL_YW_DYJL ADD CONSTRAINT  T_HL_YW_DYJL_PK PRIMARY KEY (id);
CREATE CLUSTER INDEX T_HL_YW_DYJL_CLS ON T_HL_YW_DYJL(blwsid);
COMMENT ON COLUMN T_HL_YW_DYJL.ID IS '主键ID';
COMMENT ON COLUMN T_HL_YW_DYJL.BLWSID IS '病人文书索引唯一键';
COMMENT ON COLUMN T_HL_YW_DYJL.BRID IS '病人唯一号';
COMMENT ON COLUMN T_HL_YW_DYJL.WSID IS '病历文书ID ';
COMMENT ON COLUMN T_HL_YW_DYJL.YLJGID IS '医疗机构ID';
COMMENT ON COLUMN T_HL_YW_DYJL.KSID IS '科室ID';
COMMENT ON COLUMN T_HL_YW_DYJL.RQ IS '日期';
COMMENT ON COLUMN T_HL_YW_DYJL.YSID IS '医生ID';
COMMENT ON COLUMN T_HL_YW_DYJL.DEL_FLAG IS '删除标志';
COMMENT ON COLUMN T_HL_YW_DYJL.CREATE_BY IS '创建人';
COMMENT ON COLUMN T_HL_YW_DYJL.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN T_HL_YW_DYJL.UPDATE_BY IS '更新人';
COMMENT ON COLUMN T_HL_YW_DYJL.UPDATE_TIME IS '更新时间';
COMMENT ON COLUMN T_HL_YW_DYJL.REMARKS IS '备注';
COMMENT ON TABLE T_HL_YW_DYJL IS '打印记录表';
