package com.shdata.health.carezy.forms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shdata.health.carezy.forms.entity.LaboratoryFormIndex;
import com.shdata.health.carezy.forms.vo.LaboratoryFormIndexSearchVo;
import com.shdata.health.carezy.forms.vo.LaboratoryFormIndexVo;
import com.shdata.health.carezy.forms.vo.PatientTransportVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 住院护理化验单Mapper接口
 *
 * @author myy
 * @date 2024-11-07
 */
@Mapper
@Repository
public interface LaboratoryFormIndexMapper extends BaseMapper<LaboratoryFormIndex> {

    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return 返回分页数据
     */
    IPage<LaboratoryFormIndexVo> findByPage(LaboratoryFormIndexSearchVo search);

    LaboratoryFormIndexVo getIndexById(String id, String yljgid);

    List<LaboratoryFormIndexVo> getLaboratoryIndexByBridOrBch(String brid, String bch, String bqid, String yljgid);
}
