package com.shdata.health.carezy.whiteBoard.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.shdata.health.carezy.whiteBoard.entity.WardOperationCustomItem;
import com.shdata.health.carezy.whiteBoard.mapper.WardOperationCustomItemMapper;
import com.shdata.health.carezy.whiteBoard.vo.WardOperationCustomItemSearchVo;
import com.shdata.health.carezy.whiteBoard.vo.WardOperationCustomItemVo;
import com.shdata.health.common.base.BaseService;
import com.shdata.health.common.base.PageData;
import com.shdata.health.common.exception.ParameterException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 病区运营_自定义项目表  Service服务
 *
 * @author ljt
 * @date 2024-10-24
 */
@Service
@Transactional(readOnly = true)
public class WardOperationCustomItemService extends BaseService<WardOperationCustomItemMapper,WardOperationCustomItem> {

    /**
     * 保存或更新
     *
     * @param vo 提交参数
     * @return
     */
    @Transactional
    public String saveOrUpdate(WardOperationCustomItemVo vo) {
        validate(vo);
        WardOperationCustomItem po = null;
        if (StrUtil.isNotBlank(vo.getId())) {
            po = getById(vo.getId());
            if (po == null) {
                throw new ParameterException("无效的ID");
            }
        }
        if (po == null) { //新增
            po = BeanUtil.toBean(vo, WardOperationCustomItem.class);
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
    private void validate(WardOperationCustomItemVo vo) {
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
    public PageData<WardOperationCustomItemVo> findByPage(WardOperationCustomItemSearchVo search) {
        return PageData.of(baseMapper.findByPage(search));
    }


    /**
     * 根据类型查询最新数据
     *
     * @param organId
     * @param ward
     * @param type
     */
    public List<WardOperationCustomItem> selectByType(String organId, String ward, String type) {
        LambdaQueryWrapper<WardOperationCustomItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WardOperationCustomItem::getYljgid, organId);
        queryWrapper.eq(WardOperationCustomItem::getBqid, ward);
        queryWrapper.eq(WardOperationCustomItem::getXmlx, type);
        queryWrapper.orderByDesc(WardOperationCustomItem::getCreateTime);
        if ("1".equals(type)) {
            queryWrapper.last("limit 4");
        } else if ("2".equals(type)) {
            queryWrapper.last("limit 6");
        }

        return this.list(queryWrapper);
    }
    /**
     * 根据机构病区位置删除旧数据
     */
    public void deleteByPosition(String organId, String ward, String type) {
        LambdaUpdateWrapper<WardOperationCustomItem> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(WardOperationCustomItem::getYljgid, organId);
        updateWrapper.eq(WardOperationCustomItem::getBqid, ward);
        updateWrapper.eq(WardOperationCustomItem::getXmlx, type);
        updateWrapper.set(WardOperationCustomItem::getDelFlag, "1");
        this.update(updateWrapper);
    }
}
