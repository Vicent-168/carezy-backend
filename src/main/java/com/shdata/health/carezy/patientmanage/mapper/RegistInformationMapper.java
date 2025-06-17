package com.shdata.health.carezy.patientmanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shdata.health.carezy.forms.vo.DicHljlwsVo;
import com.shdata.health.carezy.forms.vo.FormsIndexVo;
import com.shdata.health.carezy.patientmanage.entity.RegistInformation;
import com.shdata.health.carezy.patientmanage.vo.*;
import com.shdata.health.carezy.whiteBoard.entity.PatientRegistrationInformation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 病人登记信息Mapper接口
 *
 * @author panwei
 * @date 2024-10-21
 */
@Mapper
@Repository
public interface RegistInformationMapper extends BaseMapper<RegistInformation> {

    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return 返回分页数据
     */
    IPage<RegistInformationListWithNoneConertVo> findByPage0(RegistInformationSearchVo search);

    IPage<RegistInformationListWithNoneConertVo> findByPage1(RegistInformationSearchVo search);

    RegistInformationVo getInfoByBrid(String brid);

    Integer updatePatientStatus(RegistInformationSearchVo vo);
    /**
     * 批量查询患者的生命体征
     */
    List<PatientVitalSignInfoVo> batchRetrievePatientVitalSigns(VitalSignSearchFiltersVo vo);
    /**
     * 批量查询患者的血糖数据
     */
    List<PatientBloodGlucoseDataVo> batchBloodGlucoseQuery(PatientBloodGlucoseBatchQueryParamsVo vo);
    /**
     * 护理告知列表
     */
    IPage<NursingNoticeVo> CareNotificationList(NursingNoticeSearchVo vo);
    /**
     * 有创治疗列表
     */
    IPage<InvasiveTreatmentVo> findInvasiveTreatmentList(InvasiveTreatmentSearchVo vo);
    /**
     * 护理任务列表
     */
    IPage<NursingTaskVo> findNursingTaskList(NursingTaskSearchVo vo);

    /**
     * 健康宣教查询
     */
    HealthEducationVo getHealthEducationQuery(HealthEducationSearchVo vo);
    /**
     * 获取患者登记信息
     */
    PatientRegistrationInformation getInfoByHealthEducationSearchVo(HealthEducationSearchVo vo);
    List<DicHljlwsVo> getWsList(String organId);
    /**
     * 获取在院患者列表基本信息
     */
    List<RegistInformationVo> listRetrievePatients(VitalSignSearchFiltersVo vo);

    List<RegistInformationVo> findAll(String yljgid);

    RegistInformationBchVo findBybchList(String bch, String bqid, String organId);
    // 批量查询病人的体征索引表
    List<FormsIndexVo> getBatchList(List<String> bridList, String wsbm, String sj, String yljgid);

    IPage<RiskBaseInfo> riskSummary(RegistInformationSearchVo vo);

    RiskVo riskCount(RegistInformationSearchNoPageVo vo);

    RiskVo happenCount(String yljgid,String fswsid, String patientStatus, String wards, List<String> bqList);

    IPage<RegistInformationVo> riskSummaryPress(RegistInformationSearchVo vo);

    List<RegistInformationBchListVo> findAllBchList(String organId);

    RegistInformationVo findById2(String id);

    Integer saveYszdById(RegistInformationVo vo);

    List<RegistInformationVo> queryNewIn(String yljgid, List<String> wardIds);

    List<RegistInformationVo> queryNewOut(String yljgid, List<String> wardIds);

    List<RegistInformationVo> queryTomorrowOut(String yljgid, List<String> wardIds);
    /**
     * 批量查询患者的信息
     */

    List<RegistInformationVo> queryBeDying(String yljgid, List<String> wardIds);
    List<RegistInformationListVo> listRetrievePatientss(PatientSearchVo vo);

    List<RegistInformationVo> queryInAllQuery(String yljgid, List<String> wardIds);

    List<RegistInformationVo> singleRiskSummary(String yljgid, List<String> wardIds, String wsid);

    List<RegistInformationVo> hldjQuery(String yljgid, List<String> wardIds, String hldj);

    List<RegistInformationVo> ConstraintRiskSummary(String yljgid, List<String> wardIds, String wsid);
}
