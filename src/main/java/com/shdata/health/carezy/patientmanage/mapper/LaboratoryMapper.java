package com.shdata.health.carezy.patientmanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shdata.health.carezy.patientmanage.entity.Laboratory;
import com.shdata.health.carezy.patientmanage.vo.LaboratorySearchVo;
import com.shdata.health.carezy.patientmanage.vo.LaboratoryVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 住院护理化验单Mapper接口
 *
 * @author panwei
 * @date 2024-10-24
 */
@Mapper
@Repository
public interface LaboratoryMapper extends BaseMapper<Laboratory> {

    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return 返回分页数据
     */
    IPage<LaboratoryVo> findByPage(LaboratorySearchVo search);

    IPage<LaboratoryVo> getAbnormalLaboratoryList(LaboratorySearchVo search);
}
