package com.shdata.health.carezy.common.dictconverter;

import com.shdata.health.carezy.common.dict.DictService;
import com.shdata.health.carezy.common.utils.DataUtil;
import com.shdata.health.common.mybatis.ConvertNameI;
import com.shdata.health.common.mybatis.NameField;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 疾病名称转换器
 */
@Component
public class FormNameFieldConverter implements ConvertNameI {
    @Resource
    private DictService dictService;
    @Override
    public String getName(NameField field, String value) {
        if (field.key().equals("ID")){
            return getFormName(value);
        }
        return value;
    }

    private String getFormName(String code) {

        Map<String, String> dicts = DataUtil.getFormName();
        if (dicts != null) {
            return dicts.get(code);
        }
        return null;
    }

}
