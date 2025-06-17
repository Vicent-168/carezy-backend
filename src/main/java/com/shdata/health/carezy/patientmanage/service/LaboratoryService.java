package com.shdata.health.carezy.patientmanage.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.shdata.health.carezy.patientmanage.entity.Laboratory;
import com.shdata.health.carezy.patientmanage.mapper.LaboratoryMapper;
import com.shdata.health.carezy.patientmanage.vo.LaboratorySearchVo;
import com.shdata.health.carezy.patientmanage.vo.LaboratoryVo;
import com.shdata.health.common.base.BaseService;
import com.shdata.health.common.base.PageData;
import com.shdata.health.common.exception.ParameterException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 住院护理化验单  Service服务
 *
 * @author panwei
 * @date 2024-10-24
 */
@Service
@Transactional(readOnly = true)
public class LaboratoryService extends BaseService<LaboratoryMapper,Laboratory> {

    /**
     * 保存或更新
     *
     * @param vo 提交参数
     * @return
     */
    @Transactional
    public String saveOrUpdate(LaboratoryVo vo) {
        validate(vo);
        Laboratory po = null;
        if (StrUtil.isNotBlank(vo.getId())) {
            po = getById(vo.getId());
            if (po == null) {
                throw new ParameterException("无效的ID");
            }
        }
        if (po == null) { //新增
            po = BeanUtil.toBean(vo, Laboratory.class);
            po.setId(IdUtil.objectId());
            po.init();
            save(po);
            return "保存成功";
        } else {
            BeanUtil.copyProperties(vo, po);
            po.initByUpdate();
            updateById(po);
            return "更新成功";
        }
    }

    /**
     * 验证对象
     *
     * @param vo 提交参数
     */
    private void validate(LaboratoryVo vo) {
        if (vo == null) {
            throw new ParameterException("参数不能为空");
        }

    }

    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return
     */
    public PageData<LaboratoryVo> findByPage(LaboratorySearchVo search) {
        return PageData.of(baseMapper.findByPage(search));
    }


    public PageData<LaboratoryVo> getAbnormalLaboratoryList(LaboratorySearchVo search) {
        return PageData.of(baseMapper.getAbnormalLaboratoryList(search));
    }
}
