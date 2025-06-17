package com.shdata.health.carezy.forms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shdata.health.carezy.forms.entity.LaboratoryFormDetasils;
import com.shdata.health.carezy.forms.vo.LaboratoryFormDetasilsSearchVo;
import com.shdata.health.carezy.forms.vo.LaboratoryFormDetasilsVo;
import com.shdata.health.carezy.forms.vo.PatientTransportVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 住院护理化验单详情Mapper接口
 *
 * @author myy
 * @date 2024-11-06
 */
@Mapper
@Repository
public interface LaboratoryFormDetasilsMapper extends BaseMapper<LaboratoryFormDetasils> {

    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return 返回分页数据
     */
    IPage<LaboratoryFormDetasilsVo> findByPage(LaboratoryFormDetasilsSearchVo search);

    List<LaboratoryFormDetasilsVo> getLaboratoryFormDetails(String bgdh);

}
