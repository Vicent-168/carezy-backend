package com.shdata.health.carezy.whiteBoard.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.shdata.health.carezy.whiteBoard.entity.PatientListView;
import com.shdata.health.carezy.whiteBoard.mapper.PatientListViewMapper;
import com.shdata.health.carezy.whiteBoard.vo.PatientListViewSearchVo;
import com.shdata.health.carezy.whiteBoard.vo.PatientListViewVo;
import com.shdata.health.common.base.BaseService;
import com.shdata.health.common.base.PageData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 患者列表视图  Service服务
 *
 * @author ljt
 * @date 2024-10-25
 */
@Service
@Transactional(readOnly = true)
public class PatientListViewService extends BaseService<PatientListViewMapper,PatientListView> {


    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return
     */
    public PageData<PatientListViewVo> findByPage(PatientListViewSearchVo search) {
        return PageData.of(baseMapper.findByPage(search));
    }

    /**
     * 根据患者类型查询
     */
    public List<PatientListView> selectByWard(String organId, String patientFilter, String ward) {
        LambdaUpdateWrapper<PatientListView> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(PatientListView::getYljgid, organId);
        queryWrapper.eq(PatientListView::getBqid, ward);
        queryWrapper.apply("INSTR(hzsxlx, {0}) > 0", patientFilter);
        return this.list(queryWrapper);
    }

    /**
     * 查询全部患者
     */
    public List<PatientListView> selectAll(String organId, String ward) {
        LambdaUpdateWrapper<PatientListView> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(PatientListView::getYljgid, organId);
        queryWrapper.eq(PatientListView::getBqid, ward);
        return this.list(queryWrapper);
    }

}
