package com.shdata.health.carezy.whiteBoard.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.shdata.health.carezy.whiteBoard.entity.BqyyMkxmb;
import com.shdata.health.carezy.whiteBoard.entity.MkMkxmSj;
import com.shdata.health.carezy.whiteBoard.mapper.BqyyMkxmbMapper;
import com.shdata.health.carezy.whiteBoard.vo.BqyyMkXmVo;
import com.shdata.health.carezy.whiteBoard.vo.BqyyMkxmbSearchVo;
import com.shdata.health.carezy.whiteBoard.vo.BqyyMkxmbVo;
import com.shdata.health.common.base.BaseService;
import com.shdata.health.common.base.PageData;
import com.shdata.health.common.exception.ParameterException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * 病区运营模块项目表  Service服务
 *
 * @author xgb
 * @date 2024-11-19
 */
@Service
@Transactional(readOnly = true)
public class BqyyMkxmbService extends BaseService<BqyyMkxmbMapper,BqyyMkxmb> {

    /**
     * 保存或更新
     *
     * @param vo 提交参数
     * @return 主键id
     */
    @Transactional
    public String saveOrUpdate(BqyyMkxmbVo vo) {
        validate(vo);
        BqyyMkxmb po = null;
        if (StrUtil.isNotBlank(vo.getId())) {
            po = getById(vo.getId());
            if (po == null) {
                throw new ParameterException("无效的ID");
            }
        }
        if (po == null) { //新增
            po = BeanUtil.toBean(vo, BqyyMkxmb.class);
            po.setId(IdUtil.objectId());
            po.init();
            save(po);
            return po.getId();
        } else {
            BeanUtil.copyProperties(vo, po);
            po.initByUpdate();
            updateById(po);
            return vo.getId();
        }
    }

    /**
     * 验证对象
     *
     * @param vo 提交参数
     */
    private void validate(BqyyMkxmbVo vo) {
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
    public PageData<BqyyMkxmbVo> findByPage(BqyyMkxmbSearchVo search) {
        return PageData.of(baseMapper.findByPage(search));
    }

    public List<BqyyMkXmVo> findByMkIds(List<String> mkdIds) {
        return baseMapper.findByMkIds(mkdIds);
    }

    /**
     * 根据项目id批量删除
     */
    public void deleteByXmid(List<String> xmidList) {
        if (CollectionUtil.isEmpty(xmidList)) {
            return;
        }
        LambdaUpdateWrapper<BqyyMkxmb> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(BqyyMkxmb::getDelFlag, 1)
                .set(BqyyMkxmb::getUpdateTime, new Date())
                .in(BqyyMkxmb::getXmid, xmidList);
        baseMapper.update(updateWrapper);
    }

    /**
     *查询此机构现有关联关系
     */
    public List<BqyyMkXmVo> selectByOrganIdModuleType(String organId,List<String> moduleTypeList) {
        return baseMapper.selectByOrganId(organId,moduleTypeList);
    }
    /**
     * 批量更新
     */
    public void updateBatch(List<BqyyMkxmb> list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        baseMapper.updateBatch(list);
    }

    public List<MkMkxmSj> selectDefaultMkxm(List<String> moduleTypeList) {
       return baseMapper.selectDefaultMkxm(moduleTypeList);
    }
}
