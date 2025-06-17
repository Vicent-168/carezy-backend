package com.shdata.health.carezy.forms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shdata.health.carezy.forms.entity.ConduitMaintenance;
import com.shdata.health.carezy.forms.vo.ConduitMaintenanceSearchVo;
import com.shdata.health.carezy.forms.vo.ConduitMaintenanceVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 导管置管_导管维护表Mapper接口
 *
 * @author myy
 * @date 2024-11-20
 */
@Mapper
@Repository
public interface ConduitMaintenanceMapper extends BaseMapper<ConduitMaintenance> {

    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return 返回分页数据
     */
    IPage<ConduitMaintenanceVo> findByPage(ConduitMaintenanceSearchVo search);

    List<ConduitMaintenanceVo> getList(String brid);
}
