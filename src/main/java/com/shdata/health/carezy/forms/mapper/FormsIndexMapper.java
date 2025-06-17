package com.shdata.health.carezy.forms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shdata.health.carezy.forms.entity.FormsIndex;
import com.shdata.health.carezy.forms.vo.*;
//import com.shdata.health.carezy.whiteBoard.bean.PressureInjuriesQuery;
import com.shdata.health.carezy.patientmanage.vo.RegistInformationVo;
import com.shdata.health.carezy.whiteBoard.vo.CareSafetyVo;
import com.shdata.health.carezy.whiteBoard.vo.PressureInjuriesVo;
import com.shdata.health.carezy.patientmanage.vo.RegistInformationSearchNoPageVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 病人护理记录单文书索引表Mapper接口
 *
 * @author myy
 * @date 2024-10-21
 */
@Mapper
@Repository
public interface FormsIndexMapper extends BaseMapper<FormsIndex> {

    /**
     *获取索引单信息
     */
    FormsIndexVo getIndex(String id, String brid, String wsid,String wsbm,String wsfl, String yljgid);

//    List<FormsIndexVo> getList(String brid, String wsid,String wsbm,String wsfl, String yljgid);
    List<FormsIndexVo> getList(String brid, String wsid, String rq,String wsbm, String wsfl, String yljgid);
    FormsIndexVo  getInfo(String brid, String wsid, String rq,String yljgid);

    List<FormsIndexVo> getList2(String brid, String wsid, Date rq,String wsbm, String wsfl, String yljgid);

    String getWsidByylgjAndwsbm(String brid, String wsbm, String yljgid);
    String getWsidByylgjAndwsbm2(String wsbm, String yljgid);
    List<DicHljlwsVo> getWs(WsbmSearchVo vo);

    List<FormsIndexVo> getBatchList(List<String> bridList, String wsbm, Date sj, String yljgid);

    FormsIndexVo getIndexList(String id, List<String> bridList, String wsbm, Date sj, String yljgid);

    List<FormsIndexVo> getListByBatch(List<String> ids);

    List<FormsIndexVo> getIndexFormList(String brid, String wsid, Date rq, String wsbm, String wsfl, String yljgid);
    List<FormsIndexVo> getVitalSignsOfIndex(String brid, String wsfl, String wsid, String wsbm, String startTime,String endTime, String yljgid);

    List<NurseVo> getNurse(String bqid);

    FormsIndex findById(String id);

    List<RiskListVo> getRiskList(String brid, String wsfl, String yljgid);

    List<FormsIndexVoo> getIndexFormListByBridBatch(Set<String> bridSet, Date sj, String wsbm, String yljgid);

    List<CareSafetyVo> careSafetyQuery(String yljgid, List<String> wardIds);

    List<String> getRiskFormList(RegistInformationSearchNoPageVo vo);

    List<PressureInjuriesVo> pressureInjuriesQuery(String yljgid, String wsbm, Date rq, List<String> wardIds);

    List<RegistInformationVo> singleDuctStatistics(String yljgid, List<String> wardIds, String wsfl, String dglx);

    List<FormsIndexVo> getDoDuct(String brid, String wsfl, String yljgid);

    List<FormsIndexVo> getAllIndex(List<String> ids, String brid, String organId);

    void deleteByDate(String brid, String wsid, String date, String wsfl, String yljgid);

    List<DoFormsVo> getDoForm(String brid, String yljgid);
}
