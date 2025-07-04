CREATE TABLE T_HL_DIC_HLDAFLZDJY (
ID VARCHAR(32) NOT NULL
, HLDAFL VARCHAR(32) NOT NULL
, WSID VARCHAR(32) NOT NULL
, ZDJY VARCHAR(8000) NOT NULL
, DEL_FLAG VARCHAR(2) DEFAULT '0' NOT NULL
, CREATE_BY VARCHAR(32)
, CREATE_TIME DATETIME(0) DEFAULT SYSDATE NOT NULL
, UPDATE_BY VARCHAR(32)
, UPDATE_TIME DATETIME(0) DEFAULT SYSDATE NOT NULL
, REMARKS VARCHAR(255)
);
ALTER TABLE T_HL_DIC_HLDAFLZDJY ADD CONSTRAINT  T_HL_DIC_HLDAFLZDJY_PK PRIMARY KEY (id);
CREATE CLUSTER INDEX T_HL_DIC_HLDAFLZDJY_CLS ON T_HL_DIC_HLDAFLZDJY(hldafl);
COMMENT ON COLUMN T_HL_DIC_HLDAFLZDJY.ID IS '主键ID';
COMMENT ON COLUMN T_HL_DIC_HLDAFLZDJY.HLDAFL IS '护理档案分类';
COMMENT ON COLUMN T_HL_DIC_HLDAFLZDJY.WSID IS '病历文书ID ';
COMMENT ON COLUMN T_HL_DIC_HLDAFLZDJY.ZDJY IS '指导建议';
COMMENT ON COLUMN T_HL_DIC_HLDAFLZDJY.DEL_FLAG IS '删除标志';
COMMENT ON COLUMN T_HL_DIC_HLDAFLZDJY.CREATE_BY IS '创建人';
COMMENT ON COLUMN T_HL_DIC_HLDAFLZDJY.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN T_HL_DIC_HLDAFLZDJY.UPDATE_BY IS '更新人';
COMMENT ON COLUMN T_HL_DIC_HLDAFLZDJY.UPDATE_TIME IS '更新时间';
COMMENT ON COLUMN T_HL_DIC_HLDAFLZDJY.REMARKS IS '备注';
COMMENT ON TABLE T_HL_DIC_HLDAFLZDJY IS '护理档案分类指导建议表';
