package com.shdata.health.carezy.common.dict;

import com.shdata.health.common.dict.Dict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DictMapper {
    List<Dict> findDictByType(@Param("type") String type);

    String getDiseaseName(String code);
    List<Dict> findByTypeAndCj(@Param("type") String type, @Param("cj") String cj);

    List<Dict> findByTypeAndSjdm(@Param("type") String type, @Param("sjdm") String sjdm);

    String getFormName(String code);

    List<Dict> getDiseaseDict();

    List<Dict> getFormDict();

    List<DictVo> findDict(@Param("type") String type);
}
