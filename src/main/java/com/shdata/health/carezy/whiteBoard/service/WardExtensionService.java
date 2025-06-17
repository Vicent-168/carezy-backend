package com.shdata.health.carezy.whiteBoard.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.shdata.health.carezy.whiteBoard.entity.WardExtension;
import com.shdata.health.carezy.whiteBoard.mapper.WardExtensionMapper;
import com.shdata.health.carezy.whiteBoard.vo.WardExtensionSearchVo;
import com.shdata.health.carezy.whiteBoard.vo.WardExtensionVo;
import com.shdata.health.common.base.BaseService;
import com.shdata.health.common.base.PageData;
import com.shdata.health.common.exception.ParameterException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * 病区扩展表  Service服务
 *
 * @author ljt
 * @date 2024-11-05
 */
@Service
@Transactional(readOnly = true)
public class WardExtensionService extends BaseService<WardExtensionMapper,WardExtension> {

    /**
     * 保存或更新
     *
     * @param vo 提交参数
     * @return
     */
    @Transactional
    public String saveOrUpdate(WardExtensionVo vo) {
        validate(vo);
        WardExtension po = null;
        if (StrUtil.isNotBlank(vo.getId())) {
            po = getById(vo.getId());
            if (po == null) {
                throw new ParameterException("无效的ID");
            }
        }
        if (po == null) { //新增
            po = BeanUtil.toBean(vo, WardExtension.class);
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
    private void validate(WardExtensionVo vo) {
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
    public PageData<WardExtensionVo> findByPage(WardExtensionSearchVo search) {
        return PageData.of(baseMapper.findByPage(search));
    }

    /**
     * 根据病区id查询
     */
    public WardExtension selectByWardId(String wardId) {
        LambdaQueryWrapper<WardExtension> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WardExtension::getBqid, wardId);
        queryWrapper.eq(WardExtension::getDelFlag, "0");
        return baseMapper.selectOne(queryWrapper);
    }


    /**
     * 根据病区id更新总床位数
     *
     * @param ward
     * @param totalBed
     * @param id
     */
    public void updateTotalBedByWard(String ward, String totalBed, String id) {
        if (ObjectUtil.isNull(selectByWardId(ward))) {
            WardExtension wardExtension = new WardExtension();
            wardExtension.setId(IdUtil.objectId());
            wardExtension.setBqid(ward);
            wardExtension.setBqzcws(totalBed);
            wardExtension.setDelFlag("0");
            wardExtension.setCreateBy(id);
            wardExtension.setUpdateBy(id);
            baseMapper.insert(wardExtension);
        } else {
            LambdaUpdateWrapper<WardExtension> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(WardExtension::getBqzcws, totalBed);
            updateWrapper.set(WardExtension::getUpdateBy, id);
            updateWrapper.set(WardExtension::getUpdateTime, new Date());
            updateWrapper.eq(WardExtension::getBqid, ward);
            updateWrapper.eq(WardExtension::getDelFlag, "0");
            baseMapper.update(updateWrapper);
        }


    }
    /**
     * 根据病区id更新护士角色
     *
     * @param ward
     * @param id
     * @param headNurseName
     * @param deputyNurseName
     */
    public void updateRoleByWard(String ward, String id, String headNurseName, String deputyNurseName) {
        if (ObjectUtil.isNull(selectByWardId(ward))) {
            WardExtension wardExtension = new WardExtension();
            wardExtension.setId(IdUtil.objectId());
            wardExtension.setBqid(ward);
            wardExtension.setBqhsz(headNurseName);
            wardExtension.setBqfhsz(deputyNurseName);
            wardExtension.setDelFlag("0");
            wardExtension.setCreateBy(id);
            wardExtension.setUpdateBy(id);
            baseMapper.insert(wardExtension);
        } else {
            LambdaUpdateWrapper<WardExtension> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(WardExtension::getBqhsz, headNurseName);
            updateWrapper.set(WardExtension::getBqfhsz, deputyNurseName);
            updateWrapper.set(WardExtension::getUpdateBy, id);
            updateWrapper.set(WardExtension::getUpdateTime, new Date());
            updateWrapper.eq(WardExtension::getBqid, ward);
            updateWrapper.eq(WardExtension::getDelFlag, "0");
            baseMapper.update(updateWrapper);
        }


    }
}
