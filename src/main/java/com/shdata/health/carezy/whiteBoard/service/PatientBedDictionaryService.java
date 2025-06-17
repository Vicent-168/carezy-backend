package com.shdata.health.carezy.whiteBoard.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.shdata.health.carezy.whiteBoard.entity.PatientBedDictionary;
import com.shdata.health.carezy.whiteBoard.mapper.PatientBedDictionaryMapper;
import com.shdata.health.carezy.whiteBoard.vo.PatientBedDictionarySearchVo;
import com.shdata.health.carezy.whiteBoard.vo.PatientBedDictionaryVo;
import com.shdata.health.common.base.BaseService;
import com.shdata.health.common.base.PageData;
import com.shdata.health.common.exception.ParameterException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 病床字典表  Service服务
 *
 * @author ljt
 * @date 2024-10-22
 */
@Service
@Transactional(readOnly = true)
public class PatientBedDictionaryService extends BaseService<PatientBedDictionaryMapper,PatientBedDictionary> {

    /**
     * 保存或更新
     *
     * @param vo 提交参数
     * @return
     */
    @Transactional
    public String saveOrUpdate(PatientBedDictionaryVo vo) {
        validate(vo);
        PatientBedDictionary po = null;
        if (StrUtil.isNotBlank(vo.getId())) {
            po = getById(vo.getId());
            if (po == null) {
                throw new ParameterException("无效的ID");
            }
        }
        if (po == null) { //新增
            po = BeanUtil.toBean(vo, PatientBedDictionary.class);
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
    private void validate(PatientBedDictionaryVo vo) {
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
    public PageData<PatientBedDictionaryVo> findByPage(PatientBedDictionarySearchVo search) {
        return PageData.of(baseMapper.findByPage(search));
    }

    /**
     * 查询最新房间数据
     *
     * @param organ
     * @param ward
     * @return
     */
    public List<PatientBedDictionary> selectAll(String organ, String ward) {
        LambdaQueryWrapper<PatientBedDictionary> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PatientBedDictionary::getYljgid, organ);
        queryWrapper.eq(PatientBedDictionary::getBqid, ward);
        queryWrapper.eq(PatientBedDictionary::getDelFlag, "0");
        return this.list(queryWrapper);
    }

    /**
     * 删除病床信息
     *
     * @param bedId
     */
    public void deleteByBedId(String bedId) {
        LambdaUpdateWrapper<PatientBedDictionary> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(PatientBedDictionary::getId, bedId);
        updateWrapper.set(PatientBedDictionary::getDelFlag, "1");
        update(updateWrapper);
    }

}
