package com.shdata.health.carezy.forms.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.shdata.health.carezy.common.utils.UserUtils;
import com.shdata.health.carezy.forms.entity.LaboratoryFormDetasils;
import com.shdata.health.carezy.forms.mapper.LaboratoryFormDetasilsMapper;
import com.shdata.health.carezy.forms.mapper.LaboratoryFormIndexMapper;
import com.shdata.health.carezy.forms.vo.LaboratoryFormDetasilsSearchVo;
import com.shdata.health.carezy.forms.vo.LaboratoryFormDetasilsVo;
import com.shdata.health.carezy.forms.vo.LaboratoryFormIndexVo;
import com.shdata.health.carezy.forms.vo.LaboratoryFormVo;
import com.shdata.health.common.base.BaseService;
import com.shdata.health.common.base.PageData;
import com.shdata.health.common.exception.ParameterException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 住院护理化验单详情  Service服务
 *
 * @author myy
 * @date 2024-11-06
 */
@Service
@Transactional(readOnly = true)
public class LaboratoryFormService extends BaseService<LaboratoryFormDetasilsMapper, LaboratoryFormDetasils> {
    private final LaboratoryFormIndexMapper laboratoryFormIndexMapper;
    public LaboratoryFormService(LaboratoryFormIndexMapper laboratoryFormIndexMapper) {
        this.laboratoryFormIndexMapper = laboratoryFormIndexMapper;
    }


    public ResponseEntity<LaboratoryFormVo> getLaboratoryFormById(String id) {
        LaboratoryFormVo ans = new LaboratoryFormVo();
        if (StrUtil.isNotBlank(id)){
            String yljgid = UserUtils.getOrganId();
            LaboratoryFormIndexVo index = laboratoryFormIndexMapper.getIndexById(id,yljgid);
            if(index == null){
                throw new ParameterException("无效id");
            }
            ans.setLaboratoryFormIndexVo(index);
            List<LaboratoryFormDetasilsVo> laboratoryFormDetails = baseMapper.getLaboratoryFormDetails(index.getId());
            ans.setLaboratoryFormDetasilsList(laboratoryFormDetails);
        }else {
            throw new ParameterException("id不能为空");
        }
        return ResponseEntity.ok(ans);

    }

    public ResponseEntity<List<LaboratoryFormVo>> getLaboratoryFormByBridOrBch(String brid, String bch, String bqid) {
        if (StrUtil.isBlank(brid) && StrUtil.isBlank(bch)) {
            throw new ParameterException("brid和bch不能同时为空");
        }
        List<LaboratoryFormVo> ans = new ArrayList<>();
        String yljgid = UserUtils.getOrganId();
        List<LaboratoryFormIndexVo> indexList = laboratoryFormIndexMapper.getLaboratoryIndexByBridOrBch(brid, bch, bqid,yljgid);
        if(CollUtil.isNotEmpty(indexList)){
            for(LaboratoryFormIndexVo index : indexList){
                LaboratoryFormVo laboratoryFormVo = new LaboratoryFormVo();
                laboratoryFormVo.setLaboratoryFormIndexVo(index);//索引表
                //明细表
                //暂时不需要化验明细
//                List<LaboratoryFormDetasilsVo> laboratoryFormDetails = baseMapper.getLaboratoryFormDetails(index.getId());
//                laboratoryFormVo.setLaboratoryFormDetasilsList(laboratoryFormDetails);
                ans.add(laboratoryFormVo);
            }
        }
        return ResponseEntity.ok(ans);
    }

    public PageData<LaboratoryFormDetasilsVo> findByPage(LaboratoryFormDetasilsSearchVo vo) {
        if (vo.getBgbh() == null) {
            throw new ParameterException("报告编号不能为空");
        }
        vo.setYljgid(UserUtils.getOrganId());
        return PageData.of(baseMapper.findByPage(vo));
    }

    public ResponseEntity<List<LaboratoryFormVo>> getLaboratoryFormByBrid(String brid) {
        if (StrUtil.isBlank(brid)) {
            throw new ParameterException("brid不能同时为空");
        }
        List<LaboratoryFormVo> ans = new ArrayList<>();
        String yljgid = UserUtils.getOrganId();
        List<LaboratoryFormIndexVo> indexList = laboratoryFormIndexMapper.getLaboratoryIndexByBridOrBch(brid, "", "",yljgid);
        if(CollUtil.isNotEmpty(indexList)){
            for(LaboratoryFormIndexVo index : indexList){
                LaboratoryFormVo laboratoryFormVo = new LaboratoryFormVo();
                laboratoryFormVo.setLaboratoryFormIndexVo(index);//索引表
                //明细表
                //暂时不需要化验明细
//                List<LaboratoryFormDetasilsVo> laboratoryFormDetails = baseMapper.getLaboratoryFormDetails(index.getId());
//                laboratoryFormVo.setLaboratoryFormDetasilsList(laboratoryFormDetails);
                ans.add(laboratoryFormVo);
            }
        }
        return ResponseEntity.ok(ans);
    }
}

