package com.shdata.health.carezy.whiteBoard.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shdata.health.carezy.whiteBoard.entity.WardLargeScreenSummary;
import com.shdata.health.carezy.whiteBoard.mapper.WardLargeScreenSummaryMapper;
import com.shdata.health.carezy.whiteBoard.vo.WardLargeScreenSummaryVo;
import com.shdata.health.common.base.BaseService;
import com.shdata.health.common.exception.ParameterException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cn.hutool.extra.validation.ValidationUtil.validate;


/**
 * 病区大屏汇总表  Service服务
 *
 * @author ljt
 * @date 2024-10-17
 */
@Service
@Transactional(readOnly = true)
public class WardLargeScreenSummaryService extends BaseService<WardLargeScreenSummaryMapper, WardLargeScreenSummary> {

    /**
     * 查询最新一条数据
     *
     * @param userInfo
     * @return
     */
    public WardLargeScreenSummary selectLast(String organId,String bqid) {
        LambdaQueryWrapper<WardLargeScreenSummary> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WardLargeScreenSummary::getYljgid, organId);
        queryWrapper.eq(WardLargeScreenSummary::getBqid, bqid);
        queryWrapper.orderByDesc(WardLargeScreenSummary::getUpdateTime).last("limit 1");
        return this.getOne(queryWrapper);
    }

    /**
     * 保存或更新
     *
     * @param vo 提交参数
     * @return
     */
    @Transactional
    public String saveOrUpdate(WardLargeScreenSummaryVo vo) {
        validate(vo);
        WardLargeScreenSummary po = null;
        if (StrUtil.isNotBlank(vo.getId())) {
            po = getById(vo.getId());
            if (po == null) {
                throw new ParameterException("无效的ID");
            }
        }
        if (po == null) { //新增
            po = BeanUtil.toBean(vo, WardLargeScreenSummary.class);
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
}
