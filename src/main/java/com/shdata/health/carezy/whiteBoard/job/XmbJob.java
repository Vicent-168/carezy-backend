package com.shdata.health.carezy.whiteBoard.job;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.shdata.health.carezy.whiteBoard.bean.BaseQuery;
import com.shdata.health.carezy.whiteBoard.service.BqyyDpsjService;
import com.shdata.health.carezy.whiteBoard.service.BqyyMkbService;
import com.shdata.health.carezy.whiteBoard.service.BqyyMkxmbService;
import com.shdata.health.carezy.whiteBoard.service.BqyyXmbService;
import com.shdata.health.carezy.whiteBoard.vo.BqyyMkXmVo;
import com.shdata.health.carezy.whiteBoard.vo.BqyyMkbVo;
import com.shdata.health.common.config.SystemConfig;
import com.shdata.health.common.exception.ConfigException;
import com.shdata.health.common.lock.LockUtil;
import com.shdata.health.his.entity.Dept;
import com.shdata.health.his.utils.DataUtil;
import com.shdata.health.his.utils.ScreenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 白板项目定时执行任务
 */
@Slf4j
@Component
public class XmbJob {

    private final BqyyMkbService mkbService;
    private final BqyyXmbService xmbService;
    private final BqyyMkxmbService mkxmbService;
    private final BqyyDpsjService dpsjService;
    private final SystemConfig systemConfig;

    public XmbJob(BqyyMkbService mkbService, BqyyXmbService xmbService, BqyyMkxmbService mkxmbService, BqyyDpsjService dpsjService, SystemConfig systemConfig) {
        this.mkbService = mkbService;
        this.xmbService = xmbService;
        this.mkxmbService = mkxmbService;
        this.dpsjService = dpsjService;
        this.systemConfig = systemConfig;
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000)   //每小时执行一次
    public void execute() {
        LockUtil.execute(StrUtil.format("{}:XmbJob", systemConfig.getPrefix()), () -> {
            Set<String> yljgids = xmbService.findAllYljgids();
            for (String yljgid : yljgids) {
                startYljgdm(yljgid);
            }
            ScreenUtil.send("zhdp01,hldp01,bqyy01"); //通知大屏模块数据
        });
    }

    /**
     * 启动指定机构白板数据生成
     */
    public void startYljgdm(String yljgid) {
        //病区列表
        List<Dept> wardList = DataUtil.findAllWardList();
        if (wardList.isEmpty()) {
            throw new ConfigException("未配置病区");
        }
        List<String> bqIds = wardList.stream().map(Dept::getId).toList();
        List<BqyyMkbVo> mkbList = mkbService.findMkbByOrganId(yljgid, null);
        if (!mkbList.isEmpty()) {
            List<String> mkIds = mkbList.stream().map(BqyyMkbVo::getId).toList();
            List<BqyyMkXmVo> xmbList = mkxmbService.findByMkIds(mkIds);
            for (BqyyMkXmVo xmb : xmbList) {
                if (StrUtil.isNotBlank(xmb.getBean())) {
                    BaseQuery query = BaseQuery.create(xmb);
                    Date ds = DateUtil.parse(DateUtil.today());
                    Map<String, String> xmzs = query.queryXmzs(yljgid, ds, bqIds);
                    for (String bqid : bqIds) {
                        String xmz = xmzs.get(bqid);
                        dpsjService.save(yljgid, bqid, xmb.getId(), ds, StrUtil.isBlank(xmz) ? "无" : xmz);
                    }
                }
            }
        }
    }
}
