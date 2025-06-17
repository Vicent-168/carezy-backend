package com.shdata.health.carezy.forms.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.shdata.health.carezy.forms.entity.ConduitMaintenance;
import com.shdata.health.carezy.forms.mapper.ConduitMaintenanceMapper;
import com.shdata.health.carezy.forms.vo.ConduitMaintenanceVo;
import com.shdata.health.common.base.BaseService;
import com.shdata.health.common.exception.ParameterException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 导管置管_导管维护表  Service服务
 *
 * @author myy
 * @date 2024-11-20
 */
@Service
@Transactional(readOnly = true)
public class ConduitMaintenanceService extends BaseService<ConduitMaintenanceMapper,ConduitMaintenance> {

    /**
     * 保存或更新
     *
     * @param vo 提交参数
     * @return
     */
    @Transactional
    public String saveOrUpdate(ConduitMaintenanceVo vo) {
        validate(vo);
        ConduitMaintenance po = null;
        if (StrUtil.isNotBlank(vo.getId())) {
            po = getById(vo.getId());
            if (po == null) {
                throw new ParameterException("无效的ID");
            }
        }
        if (po == null) { //新增
            po = BeanUtil.toBean(vo, ConduitMaintenance.class);
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
     * @param vo 提交参数
     */
    private void validate(ConduitMaintenanceVo vo) {
        if (vo == null) {
            throw new ParameterException("参数不能为空");
        }
    }


    public List<ConduitMaintenanceVo> getList(String brid) {
        return baseMapper.getList(brid);
    }
}
