package com.shdata.health.carezy.whiteBoard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shdata.health.carezy.whiteBoard.entity.PatientBedDictionary;
import com.shdata.health.carezy.whiteBoard.vo.PatientBedDictionarySearchVo;
import com.shdata.health.carezy.whiteBoard.vo.PatientBedDictionaryVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 病床字典表Mapper接口
 *
 * @author ljt
 * @date 2024-10-22
 */
@Mapper
@Repository
public interface PatientBedDictionaryMapper extends BaseMapper<PatientBedDictionary> {

    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return 返回分页数据
     */
    IPage<PatientBedDictionaryVo> findByPage(PatientBedDictionarySearchVo search);
}
