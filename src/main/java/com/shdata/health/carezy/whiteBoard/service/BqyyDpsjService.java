package com.shdata.health.carezy.whiteBoard.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.shdata.health.carezy.common.utils.DataUtil;
import com.shdata.health.carezy.whiteBoard.entity.BqyyDpsj;
import com.shdata.health.carezy.whiteBoard.mapper.BqyyDpsjMapper;
import com.shdata.health.common.base.BaseService;
import com.shdata.health.common.config.SystemConfig;
import com.shdata.health.common.exception.ConfigException;
import com.shdata.health.common.lock.LockUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * 病区运营大屏数据表  Service服务
 *
 * @author xgb
 * @date 2024-11-19
 */
@Service
@Transactional(readOnly = true)
public class BqyyDpsjService extends BaseService<BqyyDpsjMapper, BqyyDpsj> {

    private final SystemConfig systemConfig;

    public BqyyDpsjService(SystemConfig systemConfig) {
        this.systemConfig = systemConfig;
    }

    @Transactional
    public void save(String yljgid, String bqid, String mkxmid, Date rq, String xmz) {
        Set<String> morning = getConfig(yljgid, "morning");
        Set<String> noon = getConfig(yljgid, "noon");
        Set<String> evening = getConfig(yljgid, "evening");
        Set<String> all = new HashSet<>();
        all.addAll(morning);
        all.addAll(noon);
        all.addAll(evening);
        if (all.size() != 24) {
            throw new ConfigException(StrUtil.format("机构ID为{}，ename为morning,noon,evening配置不足24小时", yljgid));
        }
        String h = String.valueOf(DateUtil.hour(new Date(), true));
        String sd;
        if (morning.contains(h)) {
            sd = "1";
        } else if (noon.contains(h)) {
            sd = "2";
        } else if (evening.contains(h)) {
            sd = "3";
        } else {
            throw new ConfigException(StrUtil.format("机构ID为{}，ename为morning,noon,evening配置出现错误", yljgid));
        }
        String key = StrUtil.format("{}:{}:{}:{}:{}:{}", systemConfig.getPrefix(), yljgid, bqid, mkxmid, DateUtil.format(rq, "yyyyMMdd"), sd);
        LockUtil.execute(key, 30, 30, () -> {
            String val = xmz.length() > 100 ? xmz.substring(0, 100) : xmz;
            BqyyDpsj po = baseMapper.findBy(yljgid, bqid, mkxmid, rq, sd);
            if (po == null) {
                po = new BqyyDpsj();
                po.setYljgid(yljgid);
                po.setBqid(bqid);
                po.setMkxmid(mkxmid);
                po.setRq(rq);
                po.setSd(sd);
                po.setXmz(val);
                po.init();
                save(po);
            } else if (!StrUtil.equals(po.getXmz(), val)) {
                po.setXmz(val);
                po.initByUpdate();
                updateById(po);
            }
        });
    }

    private Set<String> getConfig(String yljgid, String key) {
        String config = DataUtil.getConfig(yljgid, key);
        return Set.of(config.split(","));
    }


    /**
     * 根据日期删除数据
     *
     * @param organId
     * @param ward
     * @param dates
     */
    public void deleteByRq(String organId, String ward, List<Date> dates, String todoMPId) {
        LambdaUpdateWrapper<BqyyDpsj> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(BqyyDpsj::getDelFlag, 1);
        updateWrapper.eq(BqyyDpsj::getYljgid, organId);
        updateWrapper.eq(BqyyDpsj::getBqid, ward);
        updateWrapper.eq(BqyyDpsj::getMkxmid, todoMPId);
        updateWrapper.in(BqyyDpsj::getRq, dates);
        baseMapper.update(updateWrapper);
    }

    /**
     * 批量插入数据
     *
     * @param list 待插入的数据列表
     */
    public void saveBatchData(List<BqyyDpsj> list) {
        this.saveBatch(list);
    }
    /**
     * 保存或更新项目值
     */
    @Transactional
    public void saveOrUpdateXmz(String yljgid, String bqid, String mkxmid, Date rq, String xmz, String xmz1, String xmz2, String xmz3) {
        Set<String> morning = getConfig(yljgid, "morning");
        Set<String> noon = getConfig(yljgid, "noon");
        Set<String> evening = getConfig(yljgid, "evening");
        Set<String> all = new HashSet<>();
        all.addAll(morning);
        all.addAll(noon);
        all.addAll(evening);
        if (all.size() != 24) {
            throw new ConfigException(StrUtil.format("机构ID为{}，ename为morning,noon,evening配置不足24小时", yljgid));
        }
        String h = String.valueOf(DateUtil.hour(new Date(), true));
        String sd;
        if (morning.contains(h)) {
            sd = "1";
        } else if (noon.contains(h)) {
            sd = "2";
        } else if (evening.contains(h)) {
            sd = "3";
        } else {
            throw new ConfigException(StrUtil.format("机构ID为{}，ename为morning,noon,evening配置出现错误", yljgid));
        }
        String key = StrUtil.format("{}:{}:{}:{}:{}:{}", systemConfig.getPrefix(), yljgid, bqid, mkxmid, DateUtil.format(rq, "yyyyMMdd"), sd);
        LockUtil.execute(key, 30, 30, () -> {
            String val = xmz.length() > 100 ? xmz.substring(0, 100) : xmz;
            String val1 = xmz1.length() > 100 ? xmz.substring(0, 100) : xmz1;
            String val2 = xmz2.length() > 100 ? xmz.substring(0, 100) : xmz2;
            String val3 = xmz3.length() > 100 ? xmz.substring(0, 100) : xmz3;
            BqyyDpsj po = baseMapper.findBy(yljgid, bqid, mkxmid, rq, sd);
            if (po == null) {
                po = new BqyyDpsj();
                po.setYljgid(yljgid);
                po.setBqid(bqid);
                po.setMkxmid(mkxmid);
                po.setRq(rq);
                po.setSd(sd);
                po.setXmz(val);
                po.setXmz1(val1);
                po.setXmz2(val2);
                po.setXmz3(val3);
                po.init();
                save(po);
            } else if (!StrUtil.equals(po.getXmz(), val)
                    || !StrUtil.equals(po.getXmz1(), val1)
                    || !StrUtil.equals(po.getXmz2(), val2)
                    || !StrUtil.equals(po.getXmz3(), val3)) {
                po.setXmz(StrUtil.isBlank(val) ? "无" : val);
                po.setXmz1(val1);
                po.setXmz2(val2);
                po.setXmz3(val3);
                po.initByUpdate();
                updateById(po);
            }
        });
    }


}
