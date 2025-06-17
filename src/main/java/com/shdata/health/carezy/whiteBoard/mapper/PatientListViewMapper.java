package com.shdata.health.carezy.whiteBoard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shdata.health.carezy.whiteBoard.entity.PatientListView;
import com.shdata.health.carezy.whiteBoard.vo.PatientListViewSearchVo;
import com.shdata.health.carezy.whiteBoard.vo.PatientListViewVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 患者列表视图Mapper接口
 *
 * @author ljt
 * @date 2024-10-25
 */
@Mapper
@Repository
public interface PatientListViewMapper extends BaseMapper<PatientListView> {

    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return 返回分页数据
     */
    IPage<PatientListViewVo> findByPage(PatientListViewSearchVo search);
}
