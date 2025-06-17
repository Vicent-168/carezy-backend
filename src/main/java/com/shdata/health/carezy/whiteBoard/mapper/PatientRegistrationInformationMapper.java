package com.shdata.health.carezy.whiteBoard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shdata.health.carezy.whiteBoard.entity.PatientRegistrationInformation;
import com.shdata.health.carezy.whiteBoard.vo.PatientRegistrationInformationSearchVo;
import com.shdata.health.carezy.whiteBoard.vo.PatientRegistrationInformationVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 护理病人登记信息Mapper接口
 *
 * @author ljt
 * @date 2024-10-18
 */
@Mapper
@Repository
public interface PatientRegistrationInformationMapper extends BaseMapper<PatientRegistrationInformation> {

    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return 返回分页数据
     */
    IPage<PatientRegistrationInformationVo> findByPage(PatientRegistrationInformationSearchVo search);
    /**
     *
     */
    PatientRegistrationInformation findByWardId(String wardId);
}
