package com.shdata.health.carezy.whiteBoard.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.shdata.health.carezy.whiteBoard.entity.BqyyXmb;
import com.shdata.health.carezy.whiteBoard.mapper.BqyyXmbMapper;
import com.shdata.health.carezy.whiteBoard.vo.BqyyXmbSearchVo;
import com.shdata.health.carezy.whiteBoard.vo.BqyyXmbVo;
import com.shdata.health.common.base.BaseService;
import com.shdata.health.common.base.PageData;
import com.shdata.health.common.exception.ParameterException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * 病区运营项目表  Service服务
 *
 * @author xgb
 * @date 2024-11-19
 */
@Service
@Transactional(readOnly = true)
public class BqyyXmbService extends BaseService<BqyyXmbMapper, BqyyXmb> {

    /**
     * 保存或更新
     *
     * @param vo 提交参数
     * @return
     */
    @Transactional
    public String saveOrUpdate(BqyyXmbVo vo) {
        validate(vo);
        BqyyXmb po = null;
        if (StrUtil.isNotBlank(vo.getId())) {
            po = getById(vo.getId());
            if (po == null) {
                throw new ParameterException("无效的ID");
            }
        }
        if (po == null) { //新增
            po = BeanUtil.toBean(vo, BqyyXmb.class);
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
    private void validate(BqyyXmbVo vo) {
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
    public PageData<BqyyXmbVo> findByPage(BqyyXmbSearchVo search) {
        return PageData.of(baseMapper.findByPage(search));
    }


    /**
     * 查询所有配置的医疗机构
     */
    public Set<String> findAllYljgids() {
        return baseMapper.findAllYljgids();
    }

    /**
     * 根据机构查询项目
     *
     * @param organId
     * @return
     */
    public List<BqyyXmb> selectByOrganId(String organId) {
        LambdaQueryWrapper<BqyyXmb> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BqyyXmb::getYljgid, organId)
                .eq(BqyyXmb::getDelFlag, 0);
        return baseMapper.selectList(queryWrapper);
    }
    /**
     * 查询默认项目
     */
    public List<BqyyXmb> selectDefaultXm(){
        LambdaQueryWrapper<BqyyXmb> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.isNull(BqyyXmb::getYljgid)
                .or().eq(BqyyXmb::getYljgid, "")
                .eq(BqyyXmb::getDelFlag, 0);
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 批量删除项目
     */
    public void deleteByIdList(List<String> idList) {
        if (CollectionUtil.isEmpty(idList)) {
            return;
        }
        LambdaUpdateWrapper<BqyyXmb> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(BqyyXmb::getDelFlag, 1);
        updateWrapper.set(BqyyXmb::getUpdateTime, new Date());
        updateWrapper.in(BqyyXmb::getId, idList);
        update(updateWrapper);
    }

    public void updateBatch(List<BqyyXmb> update) {
        if (CollectionUtil.isEmpty(update)) {
            return;
        }
        baseMapper.updateBatch(update);
    }
}
