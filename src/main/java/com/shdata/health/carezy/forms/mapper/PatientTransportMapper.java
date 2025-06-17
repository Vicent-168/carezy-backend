package com.shdata.health.carezy.forms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shdata.health.carezy.forms.entity.PatientTransport;
import com.shdata.health.carezy.forms.vo.PatientTransportSearchVo;
import com.shdata.health.carezy.forms.vo.PatientTransportVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 患者转运表Mapper接口
 *
 * @author myy
 * @date 2024-11-06
 */
@Mapper
@Repository
public interface PatientTransportMapper extends BaseMapper<PatientTransport> {

    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return 返回分页数据
     */
    IPage<PatientTransportVo> findByPage(PatientTransportSearchVo search);

    PatientTransportVo getPatientTransport(String id);
}
