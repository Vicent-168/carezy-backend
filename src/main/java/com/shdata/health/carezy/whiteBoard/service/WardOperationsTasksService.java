package com.shdata.health.carezy.whiteBoard.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.shdata.health.carezy.whiteBoard.entity.WardOperationsTasks;
import com.shdata.health.carezy.whiteBoard.mapper.WardOperationsTasksMapper;
import com.shdata.health.carezy.whiteBoard.vo.WardOperationsTasksSearchVo;
import com.shdata.health.carezy.whiteBoard.vo.WardOperationsTasksVo;
import com.shdata.health.common.base.BaseService;
import com.shdata.health.common.base.PageData;
import com.shdata.health.common.exception.ParameterException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * 病区运营_大屏代办  Service服务
 *
 * @author ljt
 * @date 2024-10-25
 */
@Service
@Transactional(readOnly = true)
public class WardOperationsTasksService extends BaseService<WardOperationsTasksMapper, WardOperationsTasks> {

    /**
     * 保存或更新
     *
     * @param vo 提交参数
     * @return
     */
    @Transactional
    public String saveOrUpdate(WardOperationsTasksVo vo) {
        validate(vo);
        WardOperationsTasks po = null;
        if (StrUtil.isNotBlank(vo.getId())) {
            po = getById(vo.getId());
            if (po == null) {
                throw new ParameterException("无效的ID");
            }
        }
        if (po == null) { //新增
            po = BeanUtil.toBean(vo, WardOperationsTasks.class);
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
    private void validate(WardOperationsTasksVo vo) {
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
    public PageData<WardOperationsTasksVo> findByPage(WardOperationsTasksSearchVo search) {
        return PageData.of(baseMapper.findByPage(search));
    }

}
