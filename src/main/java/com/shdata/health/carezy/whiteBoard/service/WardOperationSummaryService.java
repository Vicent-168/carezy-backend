package com.shdata.health.carezy.whiteBoard.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.shdata.health.carezy.whiteBoard.entity.WardOperationSummary;
import com.shdata.health.carezy.whiteBoard.mapper.WardOperationSummaryMapper;
import com.shdata.health.carezy.whiteBoard.vo.WardOperationSummarySearchVo;
import com.shdata.health.carezy.whiteBoard.vo.WardOperationSummaryVo;
import com.shdata.health.common.base.BaseService;
import com.shdata.health.common.base.PageData;
import com.shdata.health.common.exception.ParameterException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 病区运营汇总表  Service服务
 *
 * @author ljt
 * @date 2024-10-21
 */
@Service
@Transactional(readOnly = true)
public class WardOperationSummaryService extends BaseService<WardOperationSummaryMapper, WardOperationSummary> {

    /**
     * 保存或更新
     *
     * @param vo 提交参数
     * @return
     */
    @Transactional
    public String saveOrUpdate(WardOperationSummaryVo vo) {
        validate(vo);
        WardOperationSummary po = null;
        if (StrUtil.isNotBlank(vo.getId())) {
            po = getById(vo.getId());
            if (po == null) {
                throw new ParameterException("无效的ID");
            }
        }
        if (po == null) { //新增
            po = BeanUtil.toBean(vo, WardOperationSummary.class);
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
    private void validate(WardOperationSummaryVo vo) {
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
    public PageData<WardOperationSummaryVo> findByPage(WardOperationSummarySearchVo search) {
        return PageData.of(baseMapper.findByPage(search));
    }


}
