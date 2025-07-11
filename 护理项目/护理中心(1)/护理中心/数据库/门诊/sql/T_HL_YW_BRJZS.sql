CREATE TABLE T_HL_YW_BRJZS (
ID VARCHAR(32) NOT NULL
, BLWSID VARCHAR(32) NOT NULL
, JBMC VARCHAR(30) NOT NULL
, XM VARCHAR(20) NOT NULL
, GX VARCHAR(2) NOT NULL
, FXPG VARCHAR(2) NOT NULL
, BZ VARCHAR(200)
);
ALTER TABLE T_HL_YW_BRJZS ADD CONSTRAINT  T_HL_YW_BRJZS_PK PRIMARY KEY (id);
CREATE CLUSTER INDEX T_HL_YW_BRJZS_CLS ON T_HL_YW_BRJZS(blwsid);
COMMENT ON COLUMN T_HL_YW_BRJZS.ID IS '主键ID';
COMMENT ON COLUMN T_HL_YW_BRJZS.BLWSID IS '病人文书索引唯一键';
COMMENT ON COLUMN T_HL_YW_BRJZS.JBMC IS '疾病名称';
COMMENT ON COLUMN T_HL_YW_BRJZS.XM IS '患病亲属姓名';
COMMENT ON COLUMN T_HL_YW_BRJZS.GX IS '关系';
COMMENT ON COLUMN T_HL_YW_BRJZS.FXPG IS '患者本人风险评估';
COMMENT ON COLUMN T_HL_YW_BRJZS.BZ IS '备注';
COMMENT ON TABLE T_HL_YW_BRJZS IS '病人家族史';
