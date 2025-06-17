package com.shdata.health.carezy.common.dictconverter;

import com.shdata.health.carezy.common.dict.DictService;
import com.shdata.health.carezy.common.utils.DataUtil;
import com.shdata.health.common.mybatis.ConvertNameI;
import com.shdata.health.common.mybatis.NameField;
import com.shdata.health.common.utils.DictUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 疾病名称转换器
 */
@Component
public class DiseaseNameFieldConverter implements ConvertNameI {
    @Override
    public String getName(NameField field, String value) {
        if (field.key().equals("ID")){
            return getDiseaseName(value);
        }
        return value;
    }

    private String getDiseaseName(String code) {

        Map<String, String> dicts = DataUtil.getDiseaseName();
        if (dicts != null) {
            return dicts.get(code);
        }
        return null;
    }

}
