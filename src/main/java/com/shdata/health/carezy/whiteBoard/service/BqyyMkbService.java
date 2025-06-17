package com.shdata.health.carezy.whiteBoard.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.shdata.health.carezy.whiteBoard.bean.BaseQuery;
import com.shdata.health.carezy.whiteBoard.entity.BqyyMkb;
import com.shdata.health.carezy.whiteBoard.entity.MkMkxmSj;
import com.shdata.health.carezy.whiteBoard.mapper.BqyyMkbMapper;
import com.shdata.health.carezy.whiteBoard.mapper.BqyyMkxmbMapper;
import com.shdata.health.carezy.whiteBoard.vo.BqyyMkXmVo;
import com.shdata.health.carezy.whiteBoard.vo.BqyyMkbVo;
import com.shdata.health.carezy.whiteBoard.vo.BqyyMkxmbVo;
import com.shdata.health.carezy.whiteBoard.vo.TestQueryVo;
import com.shdata.health.common.base.BaseService;
import com.shdata.health.common.exception.ConfigException;
import com.shdata.health.common.exception.ParameterException;
import com.shdata.health.his.entity.Dept;
import com.shdata.health.his.utils.DataUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 病区运营模块表  Service服务
 *
 * @author xgb
 * @date 2024-11-19
 */
@Service
@Transactional(readOnly = true)
public class BqyyMkbService extends BaseService<BqyyMkbMapper, BqyyMkb> {

    private final BqyyMkxmbMapper mkxmbMapper;

    public BqyyMkbService(BqyyMkxmbMapper mkxmbMapper) {
        this.mkxmbMapper = mkxmbMapper;
    }

    public List<BqyyMkbVo> findByOrganId(String mklx, String organId) {
        Set<String> mklxSet = null;
        if (StrUtil.isNotBlank(mklx)) {
            mklxSet = Set.of(mklx.split(","));
        } else {
            throw new ParameterException("模块类型不能为空");
        }
        List<BqyyMkbVo> result = findMkbByOrganId(organId, mklxSet);
        List<String> mkdIds = result.stream().map(BqyyMkbVo::getId).toList();
        List<BqyyMkxmbVo> mkxmbList = mkxmbMapper.findByMkdIds(mkdIds);
        Map<String, List<BqyyMkxmbVo>> map = mkxmbList.stream().collect(Collectors.groupingBy(BqyyMkxmbVo::getMkid));
        result.forEach(item -> item.setMkxmbList(CollUtil.sortByProperty(map.get(item.getId()), "xmsx")));
        return result;
    }

    public List<BqyyMkbVo> findMkbByOrganId(String organId, Set<String> mklxSet) {
        List<BqyyMkb> mkbs = baseMapper.findByOrganId(organId, mklxSet);
        Map<String, BqyyMkb> map = new HashMap<>();
        for (BqyyMkb po : mkbs) {
            if (StrUtil.isNotBlank(po.getYljgid())) {
                map.put(po.getMklx(), po);
            }
        }
        List<BqyyMkbVo> res = new ArrayList<>();
        for (BqyyMkb po : mkbs) {
            if (StrUtil.isBlank(po.getYljgid())) {
                res.add(BeanUtil.toBean(map.getOrDefault(po.getMklx(), po), BqyyMkbVo.class));
            }
        }
        return res;
    }

    public Map<String, String> testQuery(TestQueryVo query) {
        BqyyMkXmVo mkxm = mkxmbMapper.findDefualtByBean(query.getBean());
        if (mkxm == null) {
            throw new ParameterException("未找到bean为" + query.getBean() + "的配置");
        }
        List<Dept> wardList = DataUtil.findAllWardList();
        if (wardList.isEmpty()) {
            throw new ConfigException("未配置病区");
        }
        List<String> bqIds = wardList.stream().map(Dept::getId).toList();
        return BaseQuery.create(mkxm).queryXmzs(query.getYljgid(), query.getRq(), bqIds);
    }

    public BqyyMkb selectById(String id) {
        LambdaQueryWrapper<BqyyMkb> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BqyyMkb::getId, id)
                .eq(BqyyMkb::getDelFlag, 0);
        return baseMapper.selectOne(queryWrapper);
    }

    public void updateById(String id, String moduleName) {
        //更新模块名
        LambdaUpdateWrapper<BqyyMkb> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(BqyyMkb::getMkmc, moduleName)
                .set(BqyyMkb::getUpdateTime, new Date())
                .eq(BqyyMkb::getId, id)
                .eq(BqyyMkb::getDelFlag, 0);
        baseMapper.update(updateWrapper);
    }

    /**
     * 根据模块类型机构id查询数据
     *
     * @param organId
     * @param type
     * @return
     */
    public BqyyMkb selectByOrganIdType(String organId, String type) {
        LambdaQueryWrapper<BqyyMkb> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BqyyMkb::getYljgid, organId)
                .eq(BqyyMkb::getMklx, type)
                .eq(BqyyMkb::getDelFlag, 0);
        return baseMapper.selectOne(queryWrapper);
    }



    /**
     * 根据机构id查询所有模块
     *
     * @param organId
     * @return
     */
    public List<BqyyMkb> selectByOrganId(String organId) {
        LambdaQueryWrapper<BqyyMkb> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BqyyMkb::getYljgid, organId)
                .eq(BqyyMkb::getDelFlag, 0);
        return baseMapper.selectList(queryWrapper);
    }
    /**
     * 根据病区,医疗机构,模块类型查询通用数据(不包含历史未删除数据)
     */
    public List<MkMkxmSj> selectLastDefaultMkXmSj(String organId, List<String> moduleTypeList, String ward) {
        return baseMapper.selectLastDefaultMkXmSj(organId, moduleTypeList, ward);
    }


    /**
     * 根据病区查询通用数据(包含历史未删除数据)
     *
     * @param moduleTypeList
     * @param ward
     * @return
     */
    public List<MkMkxmSj> selectDefaultMkAndSjByMklx(List<String> moduleTypeList, String ward) {
        return baseMapper.selectDefaultMkAndSj(moduleTypeList, ward);
    }

    /**
     * 根据机构查询通用数据
     *
     * @param organId
     * @param moduleTypeList
     * @param ward
     * @return
     */
    public List<MkMkxmSj> selectMkAndSjByOrganId(String organId, List<String> moduleTypeList, String ward) {
        return baseMapper.selectMkAndSjByOrganId(organId, moduleTypeList, ward);
    }

    public void updateBatch(List<BqyyMkb> update) {
        if (CollectionUtil.isEmpty(update)) {
            return;
        }
        baseMapper.updateBatch(update);
    }

    public List<MkMkxmSj> selectDefaultMkXmSj(List<String> moduleTypeList,String organId){
        return baseMapper.selectDefaultMkXmSj(moduleTypeList,organId);
    }
}
