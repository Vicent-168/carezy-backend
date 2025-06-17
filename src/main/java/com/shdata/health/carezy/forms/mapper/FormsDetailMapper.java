package com.shdata.health.carezy.forms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shdata.health.carezy.forms.entity.FormsDetail;
import com.shdata.health.carezy.forms.vo.FormsDetailVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 病人护理记录单明细项表Mapper接口
 *
 * @author myy
 * @date 2024-10-21
 */
@Mapper
@Repository
public interface FormsDetailMapper extends BaseMapper<FormsDetail> {

    /**
     * 获取表单明细
     * @param blwsid
     * @return
     */
    List<FormsDetailVo> getDetail(String blwsid);

    /**
     * 保存明细
     * @return
     */
    void insertDetails(List<FormsDetail> list);

    /**
     * 更新明细
     * @return
     */
    void updateDetail(FormsDetail formsDetail);

    boolean deleteDetail(String blwsid);

    List<FormsDetailVo> getDetailByBatch(List<String> ids);
}
