package com.shdata.health.carezy.whiteBoard.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.shdata.health.carezy.whiteBoard.entity.InpatientNursingTask;
import com.shdata.health.carezy.whiteBoard.mapper.InpatientNursingTaskMapper;
import com.shdata.health.carezy.whiteBoard.vo.InpatientNursingTaskSearchVo;
import com.shdata.health.carezy.whiteBoard.vo.InpatientNursingTaskVo;
import com.shdata.health.common.base.BaseService;
import com.shdata.health.common.base.PageData;
import com.shdata.health.common.exception.ParameterException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 住院护理任务表  Service服务
 *
 * @author ljt
 * @date 2024-10-21
 */
@Service
@Transactional(readOnly = true)
public class InpatientNursingTaskService extends BaseService<InpatientNursingTaskMapper,InpatientNursingTask> {

    /**
     * 保存或更新
     *
     * @param vo 提交参数
     * @return
     */
    @Transactional
    public String saveOrUpdate(InpatientNursingTaskVo vo) {
        validate(vo);
        InpatientNursingTask po = null;
        if (StrUtil.isNotBlank(vo.getId())) {
            po = getById(vo.getId());
            if (po == null) {
                throw new ParameterException("无效的ID");
            }
        }
        if (po == null) { //新增
            po = BeanUtil.toBean(vo, InpatientNursingTask.class);
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
    private void validate(InpatientNursingTaskVo vo) {
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
    public PageData<InpatientNursingTaskVo> findByPage(InpatientNursingTaskSearchVo search) {
        return PageData.of(baseMapper.findByPage(search));
    }



}
