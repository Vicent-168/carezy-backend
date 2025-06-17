package com.shdata.health.carezy.forms.mapper;

import cn.hutool.core.lang.Opt;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shdata.health.carezy.forms.entity.AdmissionForm;
import com.shdata.health.carezy.forms.vo.AdmissionFormSearchVo;
import com.shdata.health.carezy.forms.vo.AdmissionFormVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 病人护理入院告知内容表Mapper接口
 *
 * @author myy
 * @date 2024-12-16
 */
@Mapper
@Repository
public interface AdmissionFormMapper extends BaseMapper<AdmissionForm> {


    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return 返回分页数据
     */
    IPage<AdmissionFormVo> findByPage(AdmissionFormSearchVo search);

    void insertAdmissionFormList(List<AdmissionForm> list);

    List<AdmissionFormVo> getAdmissionForm(String id);

    boolean deleteAdmissionForm(List<String> list);

    AdmissionFormVo getAdmissInfoById(String id);
}
