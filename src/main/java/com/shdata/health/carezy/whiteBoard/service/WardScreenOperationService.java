package com.shdata.health.carezy.whiteBoard.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.shdata.health.carezy.whiteBoard.entity.*;
import com.shdata.health.carezy.whiteBoard.vo.*;
import com.shdata.health.his.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ljt
 * @date 2024年10月18日 16:18
 */
@Service
@Slf4j
public class WardScreenOperationService {
    private final PatientBedDictionaryService patientBedDictionaryService;
    private final WardExtensionService wardExtensionService;
    private final BqyyMkbService bqyyMkbService;
    private final BqyyXmbService bqyyXmbService;
    private final BqyyDpsjService bqyyDpsjService;
    private final BqyyMkxmbService bqyyMkxmbService;


    public WardScreenOperationService(PatientBedDictionaryService patientBedDictionaryService, WardExtensionService wardExtensionService, BqyyMkbService bqyyMkbService, BqyyXmbService bqyyXmbService, BqyyDpsjService bqyyDpsjService1, BqyyMkxmbService bqyyMkxmbService) {
        this.patientBedDictionaryService = patientBedDictionaryService;
        this.wardExtensionService = wardExtensionService;
        this.bqyyMkbService = bqyyMkbService;
        this.bqyyXmbService = bqyyXmbService;
        this.bqyyDpsjService = bqyyDpsjService1;
        this.bqyyMkxmbService = bqyyMkxmbService;
    }

    /**
     * 获取文本写入信息
     *
     * @return
     */
    public GetTextWriteInformationOutputVo getTextWriteInformation(GetTextWriteInformationInputVo reqParam) {
        List<String> moduleTypeList = Arrays.asList(reqParam.getModuleType().split(","));
        //查询备忘录和注意事项和今日待办
        List<MkMkxmSj> mkMkxmSjs = bqyyMkbService.selectDefaultMkAndSjByMklx(moduleTypeList, reqParam.getWard());

        //筛选注意事项
        List<MkMkxmSj> mkMkxmSjList = mkMkxmSjs.stream().filter(mkMkxmSj -> "17".equals(mkMkxmSj.getMklx())).sorted(Comparator.comparing(MkMkxmSj::getRq)).toList();
        //筛选备忘录
        List<MkMkxmSj> mkMkxmSjList1 = mkMkxmSjs.stream().filter(mkMkxmSj -> mkMkxmSj.getMklx().equals("18")).sorted(Comparator.comparing(MkMkxmSj::getRq)).toList();
        //筛选待办任务
        List<MkMkxmSj> mkMkxmSjList2 = mkMkxmSjs.stream().filter(mkMkxmSj -> mkMkxmSj.getMklx().equals("07")).toList();
        //今日待办
        List<MkMkxmSj> todayTodoList = mkMkxmSjList2.stream()
                .filter(mkMkxmSj -> getCurrentDate().equals(mkMkxmSj.getRq()))
                .sorted(Comparator.comparing(MkMkxmSj::getXmz1))
                .toList();
        //明日待办
        List<MkMkxmSj> tomorrowTodoList = mkMkxmSjList2.stream()
                .filter(mkMkxmSj -> getTomorrowDate().equals(mkMkxmSj.getRq()))
                .sorted(Comparator.comparing(MkMkxmSj::getXmz1))
                .toList();
        //后日待办
        List<MkMkxmSj> theDayAfterTomorrowTodoList = mkMkxmSjList2.stream()
                .filter(mkMkxmSj -> getDayAfterTomorrowDate().equals(mkMkxmSj.getRq()))
                .sorted(Comparator.comparing(MkMkxmSj::getXmz1))
                .toList();
        //封装待办任务
        List<GetTextWriteInformationOutputVo.TodoTasks> todoTasksList = new ArrayList<>();

        //封装今日待办
        List<GetTextWriteInformationOutputVo.ToDoItems> toDoItemsList1 = new ArrayList<>();
        GetTextWriteInformationOutputVo.TodoTasks todoTasks1 = new GetTextWriteInformationOutputVo.TodoTasks();
        todoTasks1.setTodoDateType("1");
        todoTasks1.setToDoItemsList(toDoItemsList1);
        todoTasksList.add(todoTasks1);
        for (MkMkxmSj mkMkxmSj : todayTodoList) {
            GetTextWriteInformationOutputVo.ToDoItems toDoItems = new GetTextWriteInformationOutputVo.ToDoItems();
            toDoItems.setTodoDate(mkMkxmSj.getXmz1());
            toDoItems.setTodoItem(mkMkxmSj.getXmz2());
            toDoItems.setBedNumber(mkMkxmSj.getXmz3());
            toDoItemsList1.add(toDoItems);
        }
        //封装明日待办
        List<GetTextWriteInformationOutputVo.ToDoItems> toDoItemsList2 = new ArrayList<>();
        GetTextWriteInformationOutputVo.TodoTasks todoTasks2 = new GetTextWriteInformationOutputVo.TodoTasks();
        todoTasks2.setTodoDateType("2");
        todoTasks2.setToDoItemsList(toDoItemsList2);
        todoTasksList.add(todoTasks2);
        for (MkMkxmSj mkMkxmSj : tomorrowTodoList) {
            GetTextWriteInformationOutputVo.ToDoItems toDoItems = new GetTextWriteInformationOutputVo.ToDoItems();
            toDoItems.setTodoDate(mkMkxmSj.getXmz1());
            toDoItems.setTodoItem(mkMkxmSj.getXmz2());
            toDoItems.setBedNumber(mkMkxmSj.getXmz3());
            toDoItemsList2.add(toDoItems);
        }
        //封装后日待办
        List<GetTextWriteInformationOutputVo.ToDoItems> toDoItemsList3 = new ArrayList<>();
        GetTextWriteInformationOutputVo.TodoTasks todoTasks3 = new GetTextWriteInformationOutputVo.TodoTasks();
        todoTasks3.setTodoDateType("3");
        todoTasks3.setToDoItemsList(toDoItemsList3);
        todoTasksList.add(todoTasks3);
        for (MkMkxmSj mkMkxmSj : theDayAfterTomorrowTodoList) {
            GetTextWriteInformationOutputVo.ToDoItems toDoItems = new GetTextWriteInformationOutputVo.ToDoItems();
            toDoItems.setTodoDate(mkMkxmSj.getXmz1());
            toDoItems.setTodoItem(mkMkxmSj.getXmz2());
            toDoItems.setBedNumber(mkMkxmSj.getXmz3());
            toDoItemsList3.add(toDoItems);
        }
        //封装返参
        GetTextWriteInformationOutputVo getTextWriteInformationOutputVo = new GetTextWriteInformationOutputVo();
        getTextWriteInformationOutputVo.setMemoMPId(mkMkxmSjList1.get(mkMkxmSjList1.size() - 1).getMkxmid());
        getTextWriteInformationOutputVo.setNoticeMPId(mkMkxmSjList.get(mkMkxmSjList.size() - 1).getMkxmid());
        getTextWriteInformationOutputVo.setTodoMPId(mkMkxmSjList2.get(0).getMkxmid());
        String noticeXmz = mkMkxmSjList.get(mkMkxmSjList.size() - 1).getXmz();
        getTextWriteInformationOutputVo.setNotice("无".equals(noticeXmz) ? null : noticeXmz);
        String memoXmz = mkMkxmSjList1.get(mkMkxmSjList1.size() - 1).getXmz();
        getTextWriteInformationOutputVo.setMemo("无".equals(memoXmz) ? null : memoXmz);
        getTextWriteInformationOutputVo.setTodoTasksList(todoTasksList);

        return getTextWriteInformationOutputVo;
    }
    /**
     * 获取病区基本信息
     *
     * @param userInfo
     * @param reqParam
     * @return
     */
    public WardBaseInfoOutputVo getWardBaseInfo(UserInfo userInfo, WardBaseInfoInputVo reqParam) {
        //查询扩展表
        WardExtension wardExtension = wardExtensionService.selectByWardId(reqParam.getWard());
        //查询房间信息
        List<WardRoomDetailsOutputVo> roomAndScreenInfo = getRoomAndScreenInfo(userInfo, reqParam.getWard());
        //封装返参
        WardBaseInfoOutputVo wardBaseInfoOutputVo = new WardBaseInfoOutputVo();
        wardBaseInfoOutputVo.setTotalBeds(ObjectUtil.isNull(wardExtension) ? null : wardExtension.getBqzcws());
        wardBaseInfoOutputVo.setHeadNurseName(ObjectUtil.isNull(wardExtension) ? null : wardExtension.getBqhsz());
        wardBaseInfoOutputVo.setDeputyNurseName(ObjectUtil.isNull(wardExtension) ? null : wardExtension.getBqfhsz());
        wardBaseInfoOutputVo.setRoomList(roomAndScreenInfo);
        return wardBaseInfoOutputVo;
    }

    /**
     * 获取最新病区房间和大屏数据信息
     *
     * @param userInfo
     * @return
     */

    public List<WardRoomDetailsOutputVo> getRoomAndScreenInfo(UserInfo userInfo, String ward) {
        //查询最新房间数据
        List<PatientBedDictionary> patientBedDictionary = patientBedDictionaryService.selectAll(userInfo.getOrganId(), ward);
        //封装返参
        List<WardRoomDetailsOutputVo> wardRoomDetailsInputVos = new ArrayList<>();
        for (PatientBedDictionary bedDictionary : patientBedDictionary) {
            WardRoomDetailsOutputVo wardRoomDetailsOutputVo = new WardRoomDetailsOutputVo();
            wardRoomDetailsOutputVo.setId(bedDictionary.getId());
            wardRoomDetailsOutputVo.setRoomName(bedDictionary.getBfh());
            wardRoomDetailsOutputVo.setBedCount(bedDictionary.getBch().split(",").length);
            wardRoomDetailsOutputVo.setBedNumbers(bedDictionary.getBch().replace(",", "、"));
            wardRoomDetailsInputVos.add(wardRoomDetailsOutputVo);
        }
        return wardRoomDetailsInputVos;
    }

    /**
     * 保存病区房间明细
     *
     * @param userInfo
     * @param wardRoomDetail
     * @return
     */
    @Transactional
    public String saveWardRoomDetails(UserInfo userInfo, WardRoomDetailsInputVo wardRoomDetail) {
        //删除床位信息
        if ("0".equals(wardRoomDetail.getOperationType())) {
            patientBedDictionaryService.deleteByBedId(wardRoomDetail.getId());
            wardExtensionService.updateTotalBedByWard(wardRoomDetail.getWard(),wardRoomDetail.getTotalBed(),userInfo.getId());
            return "删除成功";
        }
        PatientBedDictionaryVo patientBedDictionaryVo = new PatientBedDictionaryVo();
        patientBedDictionaryVo.setId(wardRoomDetail.getId());
        patientBedDictionaryVo.setBcmc(wardRoomDetail.getBedNumbers());
        patientBedDictionaryVo.setYljgid(userInfo.getOrganId());
        patientBedDictionaryVo.setBqid(wardRoomDetail.getWard());
        patientBedDictionaryVo.setBch(wardRoomDetail.getBedNumbers().replace("、", ","));
        patientBedDictionaryVo.setBfh(wardRoomDetail.getRoomName());
        //保存床位信息
        patientBedDictionaryService.saveOrUpdate(patientBedDictionaryVo);
        //更新总床位数
        wardExtensionService.updateTotalBedByWard(wardRoomDetail.getWard(),wardRoomDetail.getTotalBed(),userInfo.getId());
        return "保存成功";
    }


    /**
     * 保存护士信息
     *
     * @param userInfo
     * @param reqParam
     * @return
     */
    public String saveNurseInfo(UserInfo userInfo, SaveNurseInfoInputVo reqParam) {
        wardExtensionService.updateRoleByWard(reqParam.getWard(),userInfo.getId(), reqParam.getHeadNurseId(), reqParam.getDeputyNurseId());
        return "保存成功";
    }


    /**
     * 获取白板维护信息
     *
     * @param userInfo
     * @param reqParam
     * @return
     */
    public List<GetCustomInfoOutputVo> getCustomProjectList(UserInfo userInfo, GetCustomProjectListInputVo reqParam) {
        List<String> moduleTypeList = Arrays.asList(reqParam.getModuleType().split(","));
        List<MkMkxmSj> mkMkxmSjs = getMkMkxmSjs(userInfo, reqParam, moduleTypeList);

        List<GetCustomInfoOutputVo> result = new ArrayList<>();
        //非空校验
        if (CollectionUtil.isEmpty(mkMkxmSjs)) {
            return result;
        }
        for (String moduleType : moduleTypeList) {
            List<MkMkxmSj> mkMkxmSjList = mkMkxmSjs.stream().filter(mkMkxmSj -> mkMkxmSj.getMklx().equals(moduleType)).toList();

            GetCustomInfoOutputVo getCustomInfoOutputVo = new GetCustomInfoOutputVo();
            List<GetCustomInfoOutputVo.Project> projectList = new ArrayList<>();
            getCustomInfoOutputVo.setModuleType(moduleType);
            getCustomInfoOutputVo.setModuleName(mkMkxmSjList.get(0).getMkmc());
            for (MkMkxmSj mkMkxmSj : mkMkxmSjList) {
                GetCustomInfoOutputVo.Project project = new GetCustomInfoOutputVo.Project();
                project.setModuleProjectId(mkMkxmSj.getMkxmid());
                project.setProjectId(mkMkxmSj.getXmid());
                project.setCustomProjectName(mkMkxmSj.getXmmc());
                project.setCustomValue(mkMkxmSj.getXmz());
                project.setOrder(mkMkxmSj.getXmsx());
                project.setIsShow("0".equals(mkMkxmSj.getSfzs()));
                project.setIsCustom(false);
                projectList.add(project);
            }
            getCustomInfoOutputVo.setProjectList(projectList);

            result.add(getCustomInfoOutputVo);
        }
        //根据模块查询现有关联关系
        List<BqyyMkXmVo> bqyyMkXmVos = bqyyMkxmbService.selectByOrganIdModuleType(userInfo.getOrganId(), moduleTypeList);
        //初次编辑
        if (CollectionUtil.isEmpty(bqyyMkXmVos)) {
            return result;
        }
        //编辑过白板维护页面
        List<MkMkxmSj> mkMkxmSjsByOrganId = bqyyMkbService.selectMkAndSjByOrganId(userInfo.getOrganId(), moduleTypeList, reqParam.getWard());
        //查询此机构的自定义项目
        List<BqyyXmb> bqyyXmbs = bqyyXmbService.selectByOrganId(userInfo.getOrganId());
        List<String> bqyyXmbIdList = bqyyXmbs.stream().map(BqyyXmb::getId).toList();

        List<GetCustomInfoOutputVo> customResult = new ArrayList<>();
        for (String moduleType : moduleTypeList) {
            //此模块下定制数据
            List<MkMkxmSj> mkMkxmSjList = optimizeStream(mkMkxmSjsByOrganId, moduleType);
            List<String> mkMKxmSjXmidList = mkMkxmSjList.stream().map(MkMkxmSj::getXmid).toList();
            //此模块下默认数据
            List<MkMkxmSj> defaultMkMkxmSjList = mkMkxmSjs.stream().filter(mkMkxmSj -> mkMkxmSj.getMklx().equals(moduleType)).toList();

            GetCustomInfoOutputVo getCustomInfoOutputVo = new GetCustomInfoOutputVo();
            List<GetCustomInfoOutputVo.Project> customProjectList = new ArrayList<>();
            List<GetCustomInfoOutputVo.Project> defaultCustomProjectList = new ArrayList<>();
            getCustomInfoOutputVo.setModuleType(moduleType);
            getCustomInfoOutputVo.setModuleName(CollectionUtil.isNotEmpty(mkMkxmSjList) ? mkMkxmSjList.get(0).getMkmc() : defaultMkMkxmSjList.get(0).getMkmc());
            //封装定制数据
            for (MkMkxmSj mkMkxmSj : mkMkxmSjList) {
                GetCustomInfoOutputVo.Project project = new GetCustomInfoOutputVo.Project();
                project.setProjectId(mkMkxmSj.getXmid());
                project.setModuleProjectId(mkMkxmSj.getMkxmid());
                project.setCustomProjectName(mkMkxmSj.getXmmc());
                project.setCustomValue(mkMkxmSj.getXmz());
                project.setOrder(mkMkxmSj.getXmsx());
                project.setIsShow("0".equals(mkMkxmSj.getSfzs()));
                project.setIsCustom(bqyyXmbIdList.contains(mkMkxmSj.getXmid()));
                customProjectList.add(project);
            }
            //需要展示的自定项目
            List<GetCustomInfoOutputVo.Project> showCustomProjectList = customProjectList.stream().filter(GetCustomInfoOutputVo.Project::getIsShow).sorted(Comparator.comparing(GetCustomInfoOutputVo.Project::getOrder)).toList();
            //不需要展示的自定项目
            List<GetCustomInfoOutputVo.Project> notShowCustomProjectList = customProjectList.stream().filter(customProject -> !customProject.getIsShow()).sorted(Comparator.comparing(GetCustomInfoOutputVo.Project::getOrder)).toList();
            //封装默认数据
            for (MkMkxmSj mkMkxmSj : defaultMkMkxmSjList) {
                //将默认数据封装
                if (!mkMKxmSjXmidList.contains(mkMkxmSj.getXmid())) {
                    GetCustomInfoOutputVo.Project project = new GetCustomInfoOutputVo.Project();
                    project.setProjectId(mkMkxmSj.getXmid());
                    project.setCustomProjectName(mkMkxmSj.getXmmc());
                    project.setModuleProjectId(mkMkxmSj.getMkxmid());
                    project.setCustomValue(mkMkxmSj.getXmz());
                    project.setOrder(mkMkxmSj.getXmsx());
                    project.setIsShow("0".equals(mkMkxmSj.getSfzs()));
                    project.setIsCustom(bqyyXmbIdList.contains(mkMkxmSj.getXmid()));
                    defaultCustomProjectList.add(project);
                }
            }
            //需要展示的通用项目
            List<GetCustomInfoOutputVo.Project> showDefaultProjectList = defaultCustomProjectList.stream().filter(GetCustomInfoOutputVo.Project::getIsShow).sorted(Comparator.comparing(GetCustomInfoOutputVo.Project::getOrder)).toList();
            //不需要展示的通用项目
            List<GetCustomInfoOutputVo.Project> notShowDefaultProjectList = defaultCustomProjectList.stream().filter(customProject -> !customProject.getIsShow()).sorted(Comparator.comparing(GetCustomInfoOutputVo.Project::getOrder)).toList();

            //合并两个展示集合
            List<GetCustomInfoOutputVo.Project> mergedShowList = new ArrayList<>(showCustomProjectList); // 创建一个新列表，初始化为 list1
            mergedShowList.addAll(showDefaultProjectList); // 将 list2 的元素添加到 mergedList 中
            List<GetCustomInfoOutputVo.Project> finallShowList = mergedShowList.stream().sorted(Comparator.comparing(GetCustomInfoOutputVo.Project::getOrder)).toList();
            //合并两个不展示集合
            List<GetCustomInfoOutputVo.Project> mergedNotShowList = new ArrayList<>(notShowCustomProjectList); // 创建一个新列表，初始化为 list1
            mergedNotShowList.addAll(notShowDefaultProjectList); // 将 list2 的元素添加到 mergedList 中
            List<GetCustomInfoOutputVo.Project> finallNotShowList = mergedNotShowList.stream().sorted(Comparator.comparing(GetCustomInfoOutputVo.Project::getOrder)).toList();
            //合并展示与不展示集合
            List<GetCustomInfoOutputVo.Project> finallList = new ArrayList<>(finallShowList); // 创建一个新列表，初始化为 list1
            finallList.addAll(finallNotShowList); // 将 list2 的元素添加到 mergedList 中

            getCustomInfoOutputVo.setProjectList(finallList);
            customResult.add(getCustomInfoOutputVo);
        }
        return customResult;
    }

    /**
     * 获取默认数据
     *
     * @param userInfo
     * @param reqParam
     * @param moduleTypeList
     * @return
     */
    private List<MkMkxmSj> getMkMkxmSjs(UserInfo userInfo, GetCustomProjectListInputVo reqParam, List<String> moduleTypeList) {
        //查询通用数据(只查询有数据的模块项目对应关系)
        List<MkMkxmSj> mkMkxmSjList = bqyyMkbService.selectLastDefaultMkXmSj(userInfo.getOrganId(), moduleTypeList, reqParam.getWard());
        //根据模块查询通用关联关系
        List<MkMkxmSj> mkMkxmSjs = bqyyMkxmbService.selectDefaultMkxm(moduleTypeList);
        for (MkMkxmSj mkMkxmSj : mkMkxmSjs) {
            for (MkMkxmSj mkxmSj : mkMkxmSjList) {
                if (mkMkxmSj.getMkxmid().equals(mkxmSj.getMkxmid())) {
                    mkMkxmSj.setXmz(mkxmSj.getXmz());
                    break;
                }
            }
        }
        return mkMkxmSjs;
    }


    /**
     * 保存文本写入
     *
     * @param userInfo
     * @param reqParam
     */
    @Transactional
    public void saveTextWrite(UserInfo userInfo, SaveTextWriteInputVo reqParam){
        //保存备忘录
        bqyyDpsjService.save(userInfo.getOrganId(), reqParam.getWard(), reqParam.getMemoMPId(), getCurrentDate(), StrUtil.isBlank(reqParam.getMemo()) ? "无" : reqParam.getMemo());
        //保存注意事项
        bqyyDpsjService.save(userInfo.getOrganId(), reqParam.getWard(), reqParam.getNoticeMPId(), getCurrentDate(), StrUtil.isBlank(reqParam.getNotice()) ? "无" : reqParam.getNotice());
        //删除3日待办数据
        List<Date> list = Arrays.asList(getCurrentDate(), getTomorrowDate(),getDayAfterTomorrowDate());
        bqyyDpsjService.deleteByRq(userInfo.getOrganId(),reqParam.getWard(),list,reqParam.getTodoMPId());
        //保存待办数据
        for (SaveTextWriteInputVo.TodoTasks todoTasks : reqParam.getTodoTasksList()) {
            if ("1".equals(todoTasks.getTodoDateType())) {
                //今日待办
                List<BqyyDpsj> bqyyDpsjs = new ArrayList<>();
                for (SaveTextWriteInputVo.ToDoItems toDoItems : todoTasks.getToDoItemsList()) {
                    packageBqyyDpsjForTodo(userInfo, reqParam, toDoItems, bqyyDpsjs, getCurrentDate());
                }
                bqyyDpsjService.saveBatchData(bqyyDpsjs);
            } else if ("2".equals(todoTasks.getTodoDateType())) {
                //明日待办
                List<BqyyDpsj> bqyyDpsjs = new ArrayList<>();
                for (SaveTextWriteInputVo.ToDoItems toDoItems : todoTasks.getToDoItemsList()) {
                    packageBqyyDpsjForTodo(userInfo, reqParam, toDoItems, bqyyDpsjs, getTomorrowDate());
                }
                bqyyDpsjService.saveBatchData(bqyyDpsjs);
            } else if ("3".equals(todoTasks.getTodoDateType())) {
                //后日待办
                List<BqyyDpsj> bqyyDpsjs = new ArrayList<>();
                for (SaveTextWriteInputVo.ToDoItems toDoItems : todoTasks.getToDoItemsList()) {
                    packageBqyyDpsjForTodo(userInfo, reqParam, toDoItems, bqyyDpsjs, getDayAfterTomorrowDate());
                }
                bqyyDpsjService.saveBatchData(bqyyDpsjs);
            }
        }

    }

    private static void packageBqyyDpsjForTodo(UserInfo userInfo, SaveTextWriteInputVo reqParam, SaveTextWriteInputVo.ToDoItems toDoItems, List<BqyyDpsj> bqyyDpsjs, Date rq) {
        BqyyDpsj bqyyDpsj = new BqyyDpsj();
        bqyyDpsj.setId(IdUtil.objectId());
        bqyyDpsj.setYljgid(userInfo.getOrganId());
        bqyyDpsj.setBqid(reqParam.getWard());
        bqyyDpsj.setRq(rq);
        bqyyDpsj.setSd("1");
        bqyyDpsj.setMkxmid(reqParam.getTodoMPId());
        bqyyDpsj.setXmz(toDoItems.getTodoItem());
        bqyyDpsj.setXmz1(toDoItems.getTodoDate());
        bqyyDpsj.setXmz2(toDoItems.getTodoItem());
        bqyyDpsj.setXmz3(toDoItems.getBedNumber());
        bqyyDpsjs.add(bqyyDpsj);
    }

    /**
     * 获取今日日期年月日部分
     * @return
     */
    public static Date getCurrentDate() {
        LocalDate today = LocalDate.now(); // 获取当前日期
        LocalDateTime localDateTime = today.atStartOfDay(); // 将日期转换为当天的开始时间（00:00:00）
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()); // 转换为Date类型并返回
    }

    /**
     * 获取明日日期年月日部分
     * @return
     */
    public static Date getTomorrowDate() {
        LocalDate tomorrow = LocalDate.now().plusDays(1); // 获取明天的日期
        LocalDateTime localDateTime = tomorrow.atStartOfDay(); // 将日期转换为当天的开始时间（00:00:00）
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()); // 转换为Date类型并返回
    }

    /**
     * 获取后日日期年月日部分
     * @return
     */
    public static Date getDayAfterTomorrowDate() {
        LocalDate dayAfterTomorrow = LocalDate.now().plusDays(2); // 获取后天的日期
        LocalDateTime localDateTime = dayAfterTomorrow.atStartOfDay(); // 将日期转换为当天的开始时间（00:00:00）
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()); // 转换为Date类型并返回
    }

//    @Transactional(rollbackFor = Exception.class)
//    public void saveCustomInfos(UserInfo userInfo, SaveCustomInfoInputVo reqParam) throws Exception {
//        //查询默认模块
//        List<BqyyMkb> bqyyMkbs = bqyyMkbService.selectDefault();
//        //查询原有自定义项目
//        List<BqyyXmb> originalBqyyXmbs = bqyyXmbService.selectByOrganId(userInfo.getOrganId());
//        //查询原有自定义项目模块关系
//        List<BqyyMkxmb> originalCustomMkxms = bqyyMkxmbService.selectByMkIdAndOrganId(userInfo.getOrganId());
//        //通用项目id
//        List<String> generalBqyyXmbIdList = bqyyXmbService.selectByNoOrganId().stream().map(BqyyXmb::getId).toList();
//        //遍历所有模块
//        for (SaveCustomInfoInputVo.Module module : reqParam.getModuleList()) {
//            //无id项目
//            List<BqyyXmb> noIdbqyyXmbs = new ArrayList<>();
//            //自定义项目值
//            Map<String, String> customValueMap = new HashMap<>();
//            //此模块下本次需展示项目
//            List<BqyyMkxmb> showBqyyMkxmbs = new ArrayList<>();
//            //此模块下不需要展示的项目
//            List<BqyyMkxmb> noShowBqyyMkxmbs = new ArrayList<>();
//            //遍历当前模块下的所有项目
//            for (SaveCustomInfoInputVo.Project project : module.getProjectList()) {
//                //项目id为空的项目
//                if (StrUtil.isBlank(project.getProjectId())) {
//                    //没有项目id
//                    BqyyXmb bqyyXmb = new BqyyXmb();
//                    bqyyXmb.setId(IdUtil.objectId());
//                    bqyyXmb.setXmmc(project.getCustomProjectName());
//                    bqyyXmb.setYljgid(userInfo.getOrganId());
//                    noIdbqyyXmbs.add(bqyyXmb);
//                    //记录模块项目对应关系
//                    if (project.getIsShow()){
//                        BqyyMkxmb bqyyMkxmb = new BqyyMkxmb();
//                        bqyyMkxmb.setId(project.getModuleProjectId());
//                        bqyyMkxmb.setXmid(bqyyXmb.getId());
//                        bqyyMkxmb.setXmmc(project.getCustomProjectName());
//                        bqyyMkxmb.setXmsx(project.getOrder());
//                        bqyyMkxmb.setSfzs("0");
//                        showBqyyMkxmbs.add(bqyyMkxmb);
//                        customValueMap.put(bqyyXmb.getId(), project.getCustomValue());
//                    } else {
//                        BqyyMkxmb bqyyMkxmb = new BqyyMkxmb();
//                        bqyyMkxmb.setId(project.getModuleProjectId());
//                        bqyyMkxmb.setXmid(bqyyXmb.getId());
//                        bqyyMkxmb.setXmmc(project.getCustomProjectName());
//                        bqyyMkxmb.setXmsx(project.getOrder());
//                        bqyyMkxmb.setSfzs("1");
//                        noShowBqyyMkxmbs.add(bqyyMkxmb);
//                        customValueMap.put(bqyyXmb.getId(), project.getCustomValue());
//                    }
//
//                } else {
//                    //项目id不为空的项目
//                    //更新自定义项目名
//                    for (BqyyXmb customBqyyXmb : originalBqyyXmbs) {
//                        if (customBqyyXmb.getId().equals(project.getProjectId())) {
//                            //自定义项目名已修改
//                            if (!customBqyyXmb.getXmmc().equals(project.getCustomProjectName())) {
//                                BqyyXmb update = new BqyyXmb();
//                                update.setId(project.getProjectId());
//                                update.setXmmc(project.getCustomProjectName());
//                                bqyyXmbService.updateByEntity(update);
//                                break;
//                            }
//                        }
//                    }
//                    if (project.getIsShow()){
//                        //记录模块项目对应关系
//                        BqyyMkxmb bqyyMkxmb = new BqyyMkxmb();
//                        bqyyMkxmb.setId(project.getModuleProjectId());
//                        bqyyMkxmb.setXmid(project.getProjectId());
//                        bqyyMkxmb.setXmmc(project.getCustomProjectName());
//                        bqyyMkxmb.setXmsx(project.getOrder());
//                        bqyyMkxmb.setSfzs("0");
//                        showBqyyMkxmbs.add(bqyyMkxmb);
//                        customValueMap.put(project.getProjectId(), project.getCustomValue());
//                    } else {
//                        //记录模块项目对应关系
//                        BqyyMkxmb bqyyMkxmb = new BqyyMkxmb();
//                        bqyyMkxmb.setId(project.getModuleProjectId());
//                        bqyyMkxmb.setXmid(project.getProjectId());
//                        bqyyMkxmb.setXmmc(project.getCustomProjectName());
//                        bqyyMkxmb.setXmsx(project.getOrder());
//                        bqyyMkxmb.setSfzs("1");
//                        noShowBqyyMkxmbs.add(bqyyMkxmb);
//                        customValueMap.put(project.getProjectId(), project.getCustomValue());
//                    }
//
//                }
//
//            }
//            //批量保存新自定义项目
//            bqyyXmbService.saveBatch(noIdbqyyXmbs);
//
//            //根据机构id查询模块表
//            BqyyMkb bqyyMkb = bqyyMkbService.selectByOrganIdType(userInfo.getOrganId(), module.getModuleType());
//            if (ObjectUtil.isNull(bqyyMkb)) {
//                //无模块id(需新增模块)
//                //模块类型校验
//                if (!bqyyMkbs.stream().map(BqyyMkb::getMklx).toList().contains(module.getModuleType())) {
//                    throw new Exception("模块类型错误");
//                }
//                String newModuleId = bqyyMkbService.insertNewModule(userInfo.getOrganId(), module.getModuleType(), module.getModuleName());
//                //复制关联关系list
//                ArrayList<BqyyMkxmb> newList1 = new ArrayList<>();
//                ArrayList<BqyyMkxmb> newList2 = new ArrayList<>();
//
//                copyListContents(showBqyyMkxmbs,newList1);
//                copyListContents(noShowBqyyMkxmbs,newList2);
//
//                List<BqyyMkxmb> bqyyMkxmbs = mergeLists(newList1, newList2);
//                //建立新的对应关系
//                for (BqyyMkxmb bqyyMkxmb : bqyyMkxmbs) {
//                    bqyyMkxmb.setId(IdUtil.objectId());
//                    bqyyMkxmb.setMkid(newModuleId);
//                }
//                //批量插入模块项目表
//                bqyyMkxmbService.saveBatch(bqyyMkxmbs);
//                //保存数据表
//                bqyyMkxmbs.forEach(bqyyMkxmb -> {
//                    //项目值非空,保存数据表
//                    if (ObjectUtil.isNotNull(customValueMap.get(bqyyMkxmb.getXmid()))) {
//                        bqyyDpsjService.save(userInfo.getOrganId(), reqParam.getWard(), bqyyMkxmb.getId(), getCurrentDate(), customValueMap.get(bqyyMkxmb.getXmid()));
//                    }
//                });
//            } else {
//                //有模块id
//                //模块名已修改
//                if (!bqyyMkb.getMkmc().equals(module.getModuleName())){
//                    //更新模块名
//                    bqyyMkbService.updateById(bqyyMkb.getId(), module.getModuleName());
//                }
//                //查询此模块下当前机构的自定义项目关系
//                List<BqyyMkxmb> customBqyyMkxmbList = originalCustomMkxms.stream().filter(bqyyMkxmb -> bqyyMkxmb.getMkid().equals(bqyyMkb.getId())).toList();
//                //查询模块与项目的对应关系
//                List<BqyyMkxmb> originalBqyyMkxmbs = bqyyMkxmbService.selectByMkId(bqyyMkb.getId());
//                //更新模块项目表和数据表
//                dealMkxmAndSj(originalBqyyMkxmbs, showBqyyMkxmbs, userInfo, bqyyMkb.getId(), customValueMap, reqParam, customBqyyMkxmbList,noShowBqyyMkxmbs);
//            }
//
//        }
//        //获取需要删除的项目id
//        List<String> delProIds = getSaveAndDelete(reqParam, originalBqyyXmbs,generalBqyyXmbIdList);
//        //批量删除项目
//        bqyyXmbService.deleteByIdList(delProIds);
//        //删除项目所对应的关联关系
//        bqyyMkxmbService.deleteByXmid(delProIds);
//    }

//    /**
//     * 获取需要删除的项目id
//     *
//     * @param reqParam
//     * @param totalNewCustomProject
//     * @param generalBqyyXmbIdList
//     * @return
//     */
//    private List<String> getSaveAndDelete(SaveCustomInfoInputVo reqParam, List<BqyyXmb> totalNewCustomProject, List<String> generalBqyyXmbIdList) {
//        //此机构现有自定义项目id
//        List<String> originalBqyyXmbIdList = totalNewCustomProject.stream().map(BqyyXmb::getId).toList();
//
//        //本次传入项目id
//        List<String> nowProIdList = new ArrayList<>();
//        for (SaveCustomInfoInputVo.Module module : reqParam.getModuleList()) {
//            for (SaveCustomInfoInputVo.Project project : module.getProjectList()) {
//                //新增自定义项目不需要处理
//                if (StrUtil.isNotBlank(project.getProjectId())){
//                    nowProIdList.add(project.getProjectId());
//                }
//            }
//        }
//        //此机构本次需要保存的自定义项目id
//        List<String> saveProIds = new ArrayList<>();
//        for (String nowId : nowProIdList) {
//            if(!generalBqyyXmbIdList.contains(nowId)){
//                saveProIds.add(nowId);
//            }
//        }
//        //删除此机构所有的原有自定义项目
//        if (CollectionUtil.isEmpty(saveProIds)){
//           return originalBqyyXmbIdList;
//        }
//        //此机构本次需要删除的自定义项目id
//        List<String> delProIds = new ArrayList<>();
//        for (String oriId : originalBqyyXmbIdList) {
//            if (!saveProIds.contains(oriId)) {
//                delProIds.add(oriId);
//            }
//        }
//
//        return delProIds;
//    }

//    private void dealMkxmAndSj(List<BqyyMkxmb> originalBqyyMkxmbs, List<BqyyMkxmb> showBqyyMkxmbs, UserInfo userInfo, String moduleId, Map<String, String> customValueMap, SaveCustomInfoInputVo reqParam, List<BqyyMkxmb> customBqyyMkxmbList, List<BqyyMkxmb> noShowBqyyMkxmbs) {
//        //赋值空字段
//        for (BqyyMkxmb showBqyyMkxmb : showBqyyMkxmbs) {
//            //赋值新增自定义项目主键id
//            if (StrUtil.isBlank(showBqyyMkxmb.getId())) {
//                showBqyyMkxmb.setId(IdUtil.objectId());
//            }
//            showBqyyMkxmb.setMkid(moduleId);
//        }
//        for (BqyyMkxmb noShowBqyyMkxmb : noShowBqyyMkxmbs) {
//            //赋值新增自定义项目主键id
//            if (StrUtil.isBlank(noShowBqyyMkxmb.getId())) {
//                noShowBqyyMkxmb.setId(IdUtil.objectId());
//            }
//            noShowBqyyMkxmb.setMkid(moduleId);
//        }
//
//        List<BqyyMkxmb> newBqyyMkxmbs1 = new ArrayList<>();
//        List<BqyyMkxmb> newBqyyMkxmbs2 = new ArrayList<>();
//        copyListContents(showBqyyMkxmbs,newBqyyMkxmbs1);
//        copyListContents(noShowBqyyMkxmbs,newBqyyMkxmbs2);
//
//        List<BqyyMkxmb> bqyyMkxmbs1 = mergeLists(newBqyyMkxmbs1, newBqyyMkxmbs2);
//
//        //需修改的对应关系
//        List<BqyyMkxmb> updateBqyyMkxmbList = new ArrayList<>();
//        for (int i = 0; i < originalBqyyMkxmbs.size(); i++) {
//            for (int i1 = 0; i1 < bqyyMkxmbs1.size(); i1++) {
//                if (originalBqyyMkxmbs.get(i).getId().equals(bqyyMkxmbs1.get(i1).getId())) {
//                    // 修改了对应关系或者项目名或者展示顺序
//                    if (!originalBqyyMkxmbs.get(i).getXmid().equals(bqyyMkxmbs1.get(i1).getXmid())
//                            || !originalBqyyMkxmbs.get(i).getXmmc().equals(bqyyMkxmbs1.get(i1).getXmmc())
//                            || !originalBqyyMkxmbs.get(i).getXmsx().equals(bqyyMkxmbs1.get(i1).getXmsx())
//                            || !originalBqyyMkxmbs.get(i).getSfzs().equals(bqyyMkxmbs1.get(i1).getSfzs())) {
//                        BqyyMkxmb bqyyMkxmb = bqyyMkxmbs1.get(i1);
//                        bqyyMkxmb.setDelFlag("0");
//                        bqyyMkxmb.setCreateBy(originalBqyyMkxmbs.get(i).getCreateBy());
//                        bqyyMkxmb.setCreateTime(originalBqyyMkxmbs.get(i).getCreateTime());
//                        bqyyMkxmb.setUpdateBy(userInfo.getId());
//                        bqyyMkxmb.setUpdateTime(new Date());
//                        // 更新对应关系
//                        updateBqyyMkxmbList.add(bqyyMkxmbs1.get(i1));
//                    }
//                    // 从后往前删除元素
//                    originalBqyyMkxmbs.remove(i);
//                    bqyyMkxmbs1.remove(i1);
//                    i--; // 更新索引，确保不跳过元素
//                    break; // 退出内层循环，防止重复删除
//                }
//            }
//        }
//        //批量新增对应关系
//        bqyyMkxmbService.saveBatch(bqyyMkxmbs1);
//        //批量修改对应关系
//        bqyyMkxmbService.updateBatchById(updateBqyyMkxmbList);
//        //批量删除对应关系
//        if (CollectionUtil.isNotEmpty(originalBqyyMkxmbs)) {
//            bqyyMkxmbService.deleteById(originalBqyyMkxmbs.stream().map(BqyyMkxmb::getId).toList());
//        }
//        //筛选需要修改的数据表的数据
//        List<BqyyMkxmb> needUpdateBqyyMkxmbList = new ArrayList<>();
//        List<BqyyMkxmb> bqyyMkxmbs2 = mergeLists(showBqyyMkxmbs, noShowBqyyMkxmbs);
//        for (BqyyMkxmb showBqyyMkxmb : bqyyMkxmbs2) {
//            for (BqyyMkxmb bqyyMkxmb : customBqyyMkxmbList) {
//                if (showBqyyMkxmb.getId().equals(bqyyMkxmb.getId())){
//                    needUpdateBqyyMkxmbList.add(showBqyyMkxmb);
//                    break;
//                }
//            }
//        }
//        //需要修改或者新增的对应关系
//        List<BqyyMkxmb> bqyyMkxmbs = mergeLists(needUpdateBqyyMkxmbList, bqyyMkxmbs1);
//        //保存数据表
//        bqyyMkxmbs.forEach(bqyyMkxmb -> {
//            //项目值非空,保存数据表
//            if (ObjectUtil.isNotNull(customValueMap.get(bqyyMkxmb.getXmid()))) {
//                bqyyDpsjService.save(userInfo.getOrganId(), reqParam.getWard(), bqyyMkxmb.getId(), getCurrentDate(), customValueMap.get(bqyyMkxmb.getXmid()));
//            }
//        });
//    }

//    /**
//     * 复制集合
//     *
//     * @param copy
//     * @param paste
//     */
//    public static void copyListContents(List<BqyyMkxmb> copy, List<BqyyMkxmb> paste) {
//        paste.clear(); // 清空 list2，确保其为空
//        for (BqyyMkxmb item : copy) {
//            paste.add(new BqyyMkxmb(item)); // 创建新对象并添加到 list2
//        }
//    }

    /**
     * 合并两个集合
     * @param list1
     * @param list2
     * @return
     */
    public static List<BqyyMkxmb> mergeLists(List<BqyyMkxmb> list1, List<BqyyMkxmb> list2) {
        List<BqyyMkxmb> mergedList = new ArrayList<>(list1); // 创建一个新列表，初始化为 list1
        mergedList.addAll(list2); // 将 list2 的元素添加到 mergedList 中
        return mergedList; // 返回合并后的列表
    }

    /**
     * 保存病区运营自定义数据
     *
     * @param userInfo
     * @param reqParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveOperationCustomData(UserInfo userInfo, SaveCustomInfoInputVo reqParam) {
        //获取入参模块类型
        List<String> moduleTypeList = reqParam.getModuleList().stream().map(SaveCustomInfoInputVo.Module::getModuleType).toList();
        //查询现有关联关系
        List<BqyyMkXmVo> bqyyMkXmVos = bqyyMkxmbService.selectByOrganIdModuleType(userInfo.getOrganId(), moduleTypeList);
        //初次编辑
        if (CollectionUtil.isEmpty(bqyyMkXmVos)) {
            dealOperationCustomFirstEdit(userInfo, reqParam);
            return;
        }
        //项目值
        Map<String,String> valueMap = new HashMap<>();
        //处理关联关系
        Map<String, List<BqyyMkxmb>> gxMap = dealOperationCustomDataRelation(userInfo, reqParam, bqyyMkXmVos, valueMap);
        //处理项目
        Map<String, List<BqyyXmb>> projectMap = dealOperationCustomDataProject(userInfo, gxMap);
        //处理模块
        Map<String, List<BqyyMkb>> moduleMap = dealModule(userInfo, reqParam);
        //处理数据
        dealOperationCustomDataValue(userInfo,reqParam,gxMap,valueMap);
        //落库
        dealDataBase(gxMap,projectMap,moduleMap);
    }
    private void dealDataBase(Map<String, List<BqyyMkxmb>> gxMap, Map<String, List<BqyyXmb>> projectMap, Map<String, List<BqyyMkb>> moduleMap){
        //新增关系
        bqyyMkxmbService.saveBatch(gxMap.get("new"));
        //修改关系
        bqyyMkxmbService.updateBatch(gxMap.get("update"));
        //删除关系
        bqyyMkxmbService.deleteByXmid(gxMap.get("delete").stream().map(BqyyMkxmb::getXmid).toList());

        //新增项目
        bqyyXmbService.saveBatch(projectMap.get("new"));
        //修改项目
        bqyyXmbService.updateBatch(projectMap.get("update"));
        //删除项目
        bqyyXmbService.deleteByIdList(projectMap.get("delete").stream().map(BqyyXmb::getId).toList());

        //修改模块
        bqyyMkbService.updateBatch(moduleMap.get("update"));
    }


    private void dealOperationCustomDataValue(UserInfo userInfo, SaveCustomInfoInputVo reqParam, Map<String, List<BqyyMkxmb>> gxMap, Map<String, String> valueMap){
        List<BqyyMkxmb> newBqyyMkxmbs = gxMap.get("new");
        List<BqyyMkxmb> updateBqyyMkxmbs = gxMap.get("update");
        List<BqyyMkxmb> bqyyMkxmbs = mergeLists(newBqyyMkxmbs, updateBqyyMkxmbs);
        //只修改了项目值
        List<String> list = bqyyMkxmbs.stream().map(BqyyMkxmb::getId).toList();
        List<String> list1 = valueMap.keySet().stream().filter(key -> !list.contains(key)).toList();
        for (String key : list1) {
            if (StrUtil.isNotBlank(valueMap.get(key))) {
                bqyyDpsjService.save(userInfo.getOrganId(), reqParam.getWard(), key, getCurrentDate(), valueMap.get(key));
            }
        }

        //修改了对应关系
        for (BqyyMkxmb bqyyMkxmb : bqyyMkxmbs) {
            if (StrUtil.isNotBlank(valueMap.get(bqyyMkxmb.getId()))){
                bqyyDpsjService.save(userInfo.getOrganId(), reqParam.getWard(), bqyyMkxmb.getId(), getCurrentDate(), valueMap.get(bqyyMkxmb.getId()));
            }
        }
    }



    private Map<String, List<BqyyMkb>> dealModule(UserInfo userInfo, SaveCustomInfoInputVo reqParam) {
        List<BqyyMkb> updateMkList = new ArrayList<>();
        Map<String, List<BqyyMkb>> resultMap = new HashMap<>();
        List<SaveCustomInfoInputVo.Module> moduleList = reqParam.getModuleList();
        for (SaveCustomInfoInputVo.Module module : moduleList) {
            BqyyMkb bqyyMkb = bqyyMkbService.selectByOrganIdType(userInfo.getOrganId(), module.getModuleType());
            //todo 当保存的模块多于初次编辑时的模块,可以在此添加新建模块的逻辑
            if (!bqyyMkb.getMkmc().equals(module.getModuleName())) {
                BqyyMkb bqyyMkb1 = new BqyyMkb();
                bqyyMkb1.setId(bqyyMkb.getId());
                bqyyMkb1.setMkmc(module.getModuleName());
                bqyyMkb1.setUpdateBy(userInfo.getId());
                bqyyMkb1.setUpdateTime(new Date());
                updateMkList.add(bqyyMkb1);
            }
        }
        resultMap.put("update", updateMkList);
        return resultMap;
    }

    private Map<String, List<BqyyXmb>> dealOperationCustomDataProject(UserInfo userInfo, Map<String, List<BqyyMkxmb>> gxMap) {
        //新增项目
        List<BqyyXmb> newXmList = new ArrayList<>();
        //更新项目
        List<BqyyXmb> updateXmList = new ArrayList<>();
        //删除项目
        List<BqyyXmb> deleteXmList = new ArrayList<>();

        Map<String, List<BqyyXmb>> resultMap = new HashMap<>();

        List<BqyyMkxmb> newBqyyMkxmbs = gxMap.get("new");
        for (BqyyMkxmb newBqyyMkxmb : newBqyyMkxmbs) {
            newBqyyMkxmb.setXmid(IdUtil.objectId());

            BqyyXmb bqyyXmb = new BqyyXmb();
            bqyyXmb.setId(newBqyyMkxmb.getXmid());
            bqyyXmb.setXmmc(newBqyyMkxmb.getXmmc());
            bqyyXmb.setYljgid(userInfo.getOrganId());
            newXmList.add(bqyyXmb);
        }
        List<BqyyMkxmb> updateBqyyMkxmbs = gxMap.get("update");
        for (BqyyMkxmb updateBqyyMkxmb : updateBqyyMkxmbs) {
            BqyyXmb bqyyXmb = new BqyyXmb();
            bqyyXmb.setId(updateBqyyMkxmb.getXmid());
            bqyyXmb.setXmmc(updateBqyyMkxmb.getXmmc());
            updateXmList.add(bqyyXmb);
        }
        List<BqyyMkxmb> deleteBqyyMkxmbs = gxMap.get("delete");
        for (BqyyMkxmb deleteBqyyMkxmb : deleteBqyyMkxmbs) {
            BqyyXmb bqyyXmb = new BqyyXmb();
            bqyyXmb.setId(deleteBqyyMkxmb.getXmid());
            deleteXmList.add(bqyyXmb);
        }

        //新增项目
        resultMap.put("new", newXmList);
        //更新项目
        resultMap.put("update", updateXmList);
        //删除项目
        resultMap.put("delete", deleteXmList);
        return resultMap;
    }

    private Map<String, List<BqyyMkxmb>> dealOperationCustomDataRelation(UserInfo userInfo, SaveCustomInfoInputVo reqParam, List<BqyyMkXmVo> mkMkxms, Map<String, String> valueMap){
        //修改关系
        List<BqyyMkxmb> updateGxList = new ArrayList<>();
        //删除关系
        List<BqyyMkxmb> deleteGxList = new ArrayList<>();
        //新增关系
        List<BqyyMkxmb> newGxList = new ArrayList<>();
        //删除关系
        List<BqyyMkxmb> deleteGxList2 = new ArrayList<>();
        Map<String, List<BqyyMkxmb>> resultMap = new HashMap<>();
        //重置展示顺序
        for (SaveCustomInfoInputVo.Module module : reqParam.getModuleList()) {
            for (int i = 0; i < module.getProjectList().size(); i++) {
                module.getProjectList().get(i).setOrder(i + 1 + "");
            }
        }

        for (SaveCustomInfoInputVo.Module module : reqParam.getModuleList()) {
            List<BqyyMkXmVo> mkxmVoList = mkMkxms.stream().filter(mkMkxm -> mkMkxm.getMklx().equals(module.getModuleType())).toList();
            List<BqyyMkxmb> nowMkXmList = new ArrayList<>();
            for (BqyyMkXmVo bqyyMkXmVo : mkxmVoList) {
                BqyyMkxmb bqyyMkxmb = new BqyyMkxmb();
                bqyyMkxmb.setId(bqyyMkXmVo.getId());
                bqyyMkxmb.setMkid(bqyyMkXmVo.getMkid());
                bqyyMkxmb.setXmid(bqyyMkXmVo.getXmid());
                bqyyMkxmb.setXmmc(bqyyMkXmVo.getXmmc());
                bqyyMkxmb.setXmsx(bqyyMkXmVo.getXmsx());
                bqyyMkxmb.setSfzs(bqyyMkXmVo.getSfzs());
                nowMkXmList.add(bqyyMkxmb);
            }

            List<SaveCustomInfoInputVo.Project> projectList = module.getProjectList();
            //处理关系
            for (int i = 0; i < nowMkXmList.size(); i++) {
                for (int i1 = 0; i1 < projectList.size(); i1++) {
                    if (nowMkXmList.get(i).getId().equals(projectList.get(i1).getModuleProjectId())) {
                        if (StrUtil.isBlank(projectList.get(i1).getCustomProjectName())) {
                            if (StrUtil.isNotBlank(projectList.get(i1).getProjectId())) {
                                BqyyMkxmb bqyyMkxmb = new BqyyMkxmb();
                                bqyyMkxmb.setId(nowMkXmList.get(i).getId());
                                bqyyMkxmb.setMkid(nowMkXmList.get(i).getMkid());
                                bqyyMkxmb.setXmid(projectList.get(i1).getProjectId());
                                bqyyMkxmb.setUpdateBy(userInfo.getId());
                                bqyyMkxmb.setUpdateTime(new Date());
                                deleteGxList.add(bqyyMkxmb);
                            }
                        } else if (!nowMkXmList.get(i).getXmmc().equals(projectList.get(i1).getCustomProjectName())
                                || !nowMkXmList.get(i).getSfzs().equals(projectList.get(i1).getIsShow() ? "0" : "1")
                                || !nowMkXmList.get(i).getXmsx().equals(projectList.get(i1).getOrder())) {
                            BqyyMkxmb bqyyMkxmb = new BqyyMkxmb();
                            bqyyMkxmb.setId(nowMkXmList.get(i).getId());
                            bqyyMkxmb.setMkid(nowMkXmList.get(i).getMkid());
                            bqyyMkxmb.setXmid(projectList.get(i1).getProjectId());
                            bqyyMkxmb.setXmmc(projectList.get(i1).getCustomProjectName());
                            bqyyMkxmb.setXmsx(projectList.get(i1).getOrder());
                            bqyyMkxmb.setSfzs(projectList.get(i1).getIsShow() ? "0" : "1");
                            bqyyMkxmb.setCreateBy(nowMkXmList.get(i).getCreateBy());
                            bqyyMkxmb.setCreateTime(nowMkXmList.get(i).getCreateTime());
                            bqyyMkxmb.setUpdateBy(userInfo.getId());
                            bqyyMkxmb.setUpdateTime(new Date());
                            updateGxList.add(bqyyMkxmb);
                        }
                        valueMap.put(nowMkXmList.get(i).getId(), StrUtil.isBlank(projectList.get(i1).getCustomValue()) ? "无" : projectList.get(i1).getCustomValue());
                        nowMkXmList.remove(i);
                        projectList.remove(i1);
                        i--;
                        break;
                    }
                }
            }

            for (SaveCustomInfoInputVo.Project project : projectList) {
                //去除补位入参
                if(StrUtil.isNotBlank(project.getCustomProjectName())){
                    BqyyMkxmb bqyyMkxmb = new BqyyMkxmb();
                    bqyyMkxmb.setId(IdUtil.objectId());
                    bqyyMkxmb.setMkid(mkMkxms.get(0).getMkid());
                    bqyyMkxmb.setXmmc(project.getCustomProjectName());
                    bqyyMkxmb.setXmsx(project.getOrder());
                    bqyyMkxmb.setSfzs(project.getIsShow() ? "0" : "1");

                    newGxList.add(bqyyMkxmb);
                    valueMap.put(bqyyMkxmb.getId(), StrUtil.isBlank(project.getCustomValue()) ? "无" : project.getCustomValue());
                }
            }
            deleteGxList2.addAll(mergeLists(nowMkXmList, deleteGxList));
        }
        //查询默认项目
        List<BqyyXmb> bqyyXmbs = bqyyXmbService.selectDefaultXm();
        List<String> defaultXmIdList = bqyyXmbs.stream().map(BqyyXmb::getId).toList();
        //去除删除中的默认项目
        for (int i = 0; i < deleteGxList2.size(); i++) {
            if (defaultXmIdList.contains(deleteGxList2.get(i).getXmid())) {
                deleteGxList2.remove(i);
                i--;
            }
        }

        //新增关系
        resultMap.put("new", newGxList);
        //修改关系
        resultMap.put("update", updateGxList);
        //删除关系
        resultMap.put("delete", deleteGxList2);

        return resultMap;
    }

    private void dealOperationCustomFirstEdit(UserInfo userInfo, SaveCustomInfoInputVo reqParam){
        //保存模块表
        List<SaveCustomInfoInputVo.Module> moduleList = reqParam.getModuleList();
        //模块表list
        List<BqyyMkb> newModuleList = new ArrayList<>();
        //项目表list
        List<BqyyXmb> newProjectList = new ArrayList<>();
        //关联关系list
        List<BqyyMkxmb> newRelationList = new ArrayList<>();
        //数据list
        List<BqyyDpsj> newDataList = new ArrayList<>();

        for (SaveCustomInfoInputVo.Module module : moduleList) {
            //记录模块
            BqyyMkb bqyyMkb = new BqyyMkb();
            bqyyMkb.setId(IdUtil.objectId());
            bqyyMkb.setYljgid(userInfo.getOrganId());
            bqyyMkb.setMklx(module.getModuleType());
            bqyyMkb.setMkmc(module.getModuleName());
            bqyyMkb.setDelFlag("0");
            bqyyMkb.setCreateBy(userInfo.getId());
            bqyyMkb.setUpdateBy(userInfo.getId());
            newModuleList.add(bqyyMkb);
            for (SaveCustomInfoInputVo.Project project : module.getProjectList()) {
                String mkxmid = null;
                //记录项目
                if (StrUtil.isNotBlank(project.getCustomProjectName())) {
                    BqyyXmb bqyyXmb = new BqyyXmb();
                    bqyyXmb.setId(IdUtil.objectId());
                    bqyyXmb.setXmmc(project.getCustomProjectName());
                    bqyyXmb.setYljgid(userInfo.getOrganId());
                    bqyyXmb.setCreateBy(userInfo.getId());
                    bqyyXmb.setUpdateBy(userInfo.getId());
                    newProjectList.add(bqyyXmb);
                    //记录对应关系
                    BqyyMkxmb bqyyMkxmb = new BqyyMkxmb();
                    bqyyMkxmb.setId(IdUtil.objectId());
                    mkxmid = bqyyMkxmb.getId();
                    bqyyMkxmb.setMkid(bqyyMkb.getId());
                    bqyyMkxmb.setXmid(bqyyXmb.getId());
                    bqyyMkxmb.setXmmc(bqyyXmb.getXmmc());
                    bqyyMkxmb.setXmsx(project.getOrder());
                    bqyyMkxmb.setSfzs("0");
                    bqyyMkxmb.setCreateBy(userInfo.getId());
                    bqyyMkxmb.setUpdateBy(userInfo.getId());
                    newRelationList.add(bqyyMkxmb);
                }
                if (StrUtil.isNotBlank(project.getCustomValue())) {
                    //记录数据
                    BqyyDpsj bqyyDpsj = new BqyyDpsj();
                    bqyyDpsj.setId(IdUtil.objectId());
                    bqyyDpsj.setYljgid(userInfo.getOrganId());
                    bqyyDpsj.setBqid(reqParam.getWard());
                    bqyyDpsj.setRq(getCurrentDate());
                    bqyyDpsj.setSd("1");
                    bqyyDpsj.setMkxmid(mkxmid);
                    bqyyDpsj.setXmz(StrUtil.isBlank(project.getCustomValue()) ? "无" : project.getCustomValue());
                    bqyyDpsj.setCreateBy(userInfo.getId());
                    bqyyDpsj.setUpdateBy(userInfo.getId());
                    newDataList.add(bqyyDpsj);
                }
            }
        }
        //保存模块表
        bqyyMkbService.saveBatch(newModuleList);
        //保存项目表
        bqyyXmbService.saveBatch(newProjectList);
        //保存关联关系
        bqyyMkxmbService.saveBatch(newRelationList);
        //保存数据
        bqyyDpsjService.saveBatch(newDataList);
    }

    /**
     * 查询病区运营自定义数据
     *
     * @param userInfo
     * @param reqParam
     * @return
     */
    public List<GetCustomInfoOutputVo> getOperationCustomData(UserInfo userInfo, GetCustomProjectListInputVo reqParam) {
        List<String> moduleTypeList = Arrays.asList(reqParam.getModuleType().split(","));
        //查询默认数据
        List<MkMkxmSj> mkMkxmSjs = bqyyMkbService.selectDefaultMkAndSjByMklx(moduleTypeList, reqParam.getWard());

        List<GetCustomInfoOutputVo> result = new ArrayList<>();
        for (String moduleType : moduleTypeList) {
            List<MkMkxmSj> mkMkxmSjList = mkMkxmSjs.stream().filter(mkMkxmSj -> mkMkxmSj.getMklx().equals(moduleType)).toList();

            GetCustomInfoOutputVo getCustomInfoOutputVo = new GetCustomInfoOutputVo();
            List<GetCustomInfoOutputVo.Project> projectList = new ArrayList<>();
            getCustomInfoOutputVo.setModuleType(moduleType);
            getCustomInfoOutputVo.setModuleName(mkMkxmSjList.get(0).getMkmc());
            for (MkMkxmSj mkMkxmSj : mkMkxmSjList) {
                GetCustomInfoOutputVo.Project project = new GetCustomInfoOutputVo.Project();
                project.setProjectId(mkMkxmSj.getXmid());
                project.setCustomProjectName(mkMkxmSj.getXmmc());
                project.setCustomValue(mkMkxmSj.getXmz());
                project.setOrder(mkMkxmSj.getXmsx());
                project.setIsShow(true);
                project.setIsCustom(false);
                projectList.add(project);
            }
            getCustomInfoOutputVo.setProjectList(projectList);

            result.add(getCustomInfoOutputVo);
        }
        //查询模块信息
        List<BqyyMkb> bqyyMkbs = bqyyMkbService.selectByOrganId(userInfo.getOrganId());
        List<BqyyMkb> list = bqyyMkbs.stream().filter(bqyyMkb -> moduleTypeList.contains(bqyyMkb.getMklx())).toList();
        //没有编辑过白板维护页面
        if (CollectionUtil.isEmpty(list)) {
            return result;
        }
        //编辑过病区运营页面
        List<MkMkxmSj> mkMkxmSjsByOrganId = bqyyMkbService.selectMkAndSjByOrganId(userInfo.getOrganId(), moduleTypeList, reqParam.getWard());

        List<GetCustomInfoOutputVo> customResult = new ArrayList<>();
        for (String moduleType : moduleTypeList) {
            //此模块下定制数据
            List<MkMkxmSj> mkMkxmSjList = optimizeStream(mkMkxmSjsByOrganId, moduleType);
            GetCustomInfoOutputVo getCustomInfoOutputVo = new GetCustomInfoOutputVo();
            List<GetCustomInfoOutputVo.Project> customProjectList = new ArrayList<>();
            getCustomInfoOutputVo.setModuleType(moduleType);
            getCustomInfoOutputVo.setModuleName(mkMkxmSjList.get(0).getMkmc());
            //封装定制数据
            for (MkMkxmSj mkMkxmSj : mkMkxmSjList) {
                GetCustomInfoOutputVo.Project project = new GetCustomInfoOutputVo.Project();
                project.setProjectId(mkMkxmSj.getXmid());
                project.setModuleProjectId(mkMkxmSj.getMkxmid());
                project.setCustomProjectName(mkMkxmSj.getXmmc());
                project.setCustomValue(mkMkxmSj.getXmz());
                project.setOrder(mkMkxmSj.getXmsx());
                project.setIsShow("0".equals(mkMkxmSj.getSfzs()));
                project.setIsCustom(true);
                customProjectList.add(project);
            }


            getCustomInfoOutputVo.setProjectList(customProjectList);
            customResult.add(getCustomInfoOutputVo);
        }
        return customResult;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveWhiteBoardCustomInfo(UserInfo userInfo, SaveCustomInfoInputVo reqParam) {
        //获取入参模块类型
        List<String> moduleTypeList = reqParam.getModuleList().stream().map(SaveCustomInfoInputVo.Module::getModuleType).toList();
        //根据模块查询现有关联关系
        List<BqyyMkXmVo> bqyyMkXmVos = bqyyMkxmbService.selectByOrganIdModuleType(userInfo.getOrganId(), moduleTypeList);
        //初次编辑
        if (CollectionUtil.isEmpty(bqyyMkXmVos)) {
            dealWhiteBoardCustomFirstEdit(userInfo, reqParam);
            return;
        }
        //查询此机构所有自定义项目
        List<BqyyXmb> bqyyXmbs = bqyyXmbService.selectByOrganId(userInfo.getOrganId());
        List<String> list = bqyyXmbs.stream().map(BqyyXmb::getId).toList();
        //筛选当前模块下自定义项目关联关系
        List<BqyyMkXmVo> customRelationship = bqyyMkXmVos.stream().filter(bqyyMkXmVo -> list.contains(bqyyMkXmVo.getXmid())).toList();

        //项目值
        Map<String,String> valueMap = new HashMap<>();
        //处理关联关系
        Map<String, List<BqyyMkxmb>> gxMap = dealWhiteBoardCustomInfoRelation(userInfo, reqParam, bqyyMkXmVos, valueMap);
        //处理项目
        Map<String, List<BqyyXmb>> projectMap = dealWhiteBoardCustomInfoProject(userInfo, gxMap, reqParam, customRelationship);
        //处理模块
        Map<String, List<BqyyMkb>> moduleMap = dealModule(userInfo, reqParam);
        //处理数据
        dealWhiteBoardCustomInfoValue(userInfo, reqParam, gxMap, valueMap);
        //落库
        dealDataBase(gxMap, projectMap, moduleMap);
    }

    private void dealWhiteBoardCustomFirstEdit(UserInfo userInfo, SaveCustomInfoInputVo reqParam){
        //保存模块表
        List<SaveCustomInfoInputVo.Module> moduleList = reqParam.getModuleList();
        //模块表list
        List<BqyyMkb> newModuleList = new ArrayList<>();
        //项目表list
        List<BqyyXmb> newProjectList = new ArrayList<>();
        //关联关系list
        List<BqyyMkxmb> newRelationList = new ArrayList<>();
        //数据list
        List<BqyyDpsj> newDataList = new ArrayList<>();

        for (SaveCustomInfoInputVo.Module module : moduleList) {
            //记录模块
            BqyyMkb bqyyMkb = new BqyyMkb();
            bqyyMkb.setId(IdUtil.objectId());
            bqyyMkb.setYljgid(userInfo.getOrganId());
            bqyyMkb.setMklx(module.getModuleType());
            bqyyMkb.setMkmc(module.getModuleName());
            bqyyMkb.setDelFlag("0");
            bqyyMkb.setCreateBy(userInfo.getId());
            bqyyMkb.setUpdateBy(userInfo.getId());
            newModuleList.add(bqyyMkb);
            for (SaveCustomInfoInputVo.Project project : module.getProjectList()) {
                //记录项目
                BqyyXmb bqyyXmb = new BqyyXmb();
                if (project.getIsCustom()) {
                    bqyyXmb.setId(IdUtil.objectId());
                    bqyyXmb.setXmmc(project.getCustomProjectName());
                    bqyyXmb.setYljgid(userInfo.getOrganId());
                    bqyyXmb.setCreateBy(userInfo.getId());
                    bqyyXmb.setUpdateBy(userInfo.getId());
                    newProjectList.add(bqyyXmb);
                }
                //记录对应关系
                BqyyMkxmb bqyyMkxmb = new BqyyMkxmb();
                bqyyMkxmb.setId(IdUtil.objectId());
                bqyyMkxmb.setMkid(bqyyMkb.getId());
                bqyyMkxmb.setXmid(project.getIsCustom() ? bqyyXmb.getId() : project.getProjectId());
                bqyyMkxmb.setXmmc(project.getIsCustom() ? bqyyXmb.getXmmc() : project.getCustomProjectName());
                bqyyMkxmb.setXmsx(project.getOrder());
                bqyyMkxmb.setSfzs(project.getIsShow() ? "0" : "1");
                bqyyMkxmb.setCreateBy(userInfo.getId());
                bqyyMkxmb.setUpdateBy(userInfo.getId());
                newRelationList.add(bqyyMkxmb);

                if (project.getIsCustom()) {
                    //记录数据
                    BqyyDpsj bqyyDpsj = new BqyyDpsj();
                    bqyyDpsj.setId(IdUtil.objectId());
                    bqyyDpsj.setYljgid(userInfo.getOrganId());
                    bqyyDpsj.setBqid(reqParam.getWard());
                    bqyyDpsj.setRq(getCurrentDate());
                    bqyyDpsj.setSd("1");
                    bqyyDpsj.setMkxmid(bqyyMkxmb.getId());
                    bqyyDpsj.setXmz(StrUtil.isBlank(project.getCustomValue()) ? "无" : project.getCustomValue());
                    bqyyDpsj.setCreateBy(userInfo.getId());
                    bqyyDpsj.setUpdateBy(userInfo.getId());
                    newDataList.add(bqyyDpsj);
                }
            }
        }
        //保存模块表
        bqyyMkbService.saveBatch(newModuleList);
        //保存项目表
        bqyyXmbService.saveBatch(newProjectList);
        //保存关联关系
        bqyyMkxmbService.saveBatch(newRelationList);
        //保存数据
        bqyyDpsjService.saveBatch(newDataList);
    }

    private Map<String, List<BqyyMkxmb>> dealWhiteBoardCustomInfoRelation(UserInfo userInfo, SaveCustomInfoInputVo reqParam, List<BqyyMkXmVo> mkMkxms, Map<String, String> valueMap){
        //修改关系
        List<BqyyMkxmb> updateGxList1 = new ArrayList<>();
        //删除关系
        List<BqyyMkxmb> deleteGxList = new ArrayList<>();
        //新增关系
        List<BqyyMkxmb> newGxList = new ArrayList<>();
        //删除关系
        List<BqyyMkxmb> deleteGxList2 = new ArrayList<>();
        Map<String, List<BqyyMkxmb>> resultMap = new HashMap<>();
        for (SaveCustomInfoInputVo.Module module : reqParam.getModuleList()) {
            //此模块当前关系
            List<BqyyMkXmVo> list4 = mkMkxms.stream().filter(mkMkxm -> mkMkxm.getMklx().equals(module.getModuleType())).toList();
            //删除关系
            List<BqyyMkxmb> list = new ArrayList<>();
            for (BqyyMkXmVo bqyyMkXmVo : list4) {
                BqyyMkxmb bqyyMkxmb = new BqyyMkxmb();
                bqyyMkxmb.setId(bqyyMkXmVo.getId());
                bqyyMkxmb.setMkid(bqyyMkXmVo.getMkid());
                bqyyMkxmb.setXmid(bqyyMkXmVo.getXmid());
                bqyyMkxmb.setXmmc(bqyyMkXmVo.getXmmc());
                bqyyMkxmb.setXmsx(bqyyMkXmVo.getXmsx());
                bqyyMkxmb.setSfzs(bqyyMkXmVo.getSfzs());
                list.add(bqyyMkxmb);
            }

            List<SaveCustomInfoInputVo.Project> projectList = module.getProjectList();
            //处理关系
            for (int i = 0; i < list.size(); i++) {
                for (int i1 = 0; i1 < projectList.size(); i1++) {
                    if (list.get(i).getId().equals(projectList.get(i1).getModuleProjectId())) {
                        if (StrUtil.isBlank(projectList.get(i1).getCustomProjectName())) {
                            if (StrUtil.isNotBlank(projectList.get(i1).getProjectId())) {
                                BqyyMkxmb bqyyMkxmb = new BqyyMkxmb();
                                bqyyMkxmb.setId(list.get(i).getId());
                                bqyyMkxmb.setMkid(list.get(i).getMkid());
                                bqyyMkxmb.setXmid(projectList.get(i1).getProjectId());
                                bqyyMkxmb.setUpdateBy(userInfo.getId());
                                bqyyMkxmb.setUpdateTime(new Date());
                                deleteGxList.add(bqyyMkxmb);
                            }
                        } else if (!list.get(i).getXmmc().equals(projectList.get(i1).getCustomProjectName())
                                || !list.get(i).getSfzs().equals(projectList.get(i1).getIsShow() ? "0" : "1")
                                || !list.get(i).getXmsx().equals(projectList.get(i1).getOrder())) {
                            BqyyMkxmb bqyyMkxmb = new BqyyMkxmb();
                            bqyyMkxmb.setId(list.get(i).getId());
                            bqyyMkxmb.setMkid(list.get(i).getMkid());
                            bqyyMkxmb.setXmid(projectList.get(i1).getProjectId());
                            bqyyMkxmb.setXmmc(projectList.get(i1).getCustomProjectName());
                            bqyyMkxmb.setXmsx(projectList.get(i1).getOrder());
                            bqyyMkxmb.setSfzs(projectList.get(i1).getIsShow() ? "0" : "1");
                            bqyyMkxmb.setCreateBy(list.get(i).getCreateBy());
                            bqyyMkxmb.setCreateTime(list.get(i).getCreateTime());
                            bqyyMkxmb.setUpdateBy(userInfo.getId());
                            bqyyMkxmb.setUpdateTime(new Date());
                            updateGxList1.add(bqyyMkxmb);
                        }
                        //记录自定义项目值
                        if (projectList.get(i1).getIsCustom()) {
                            valueMap.put(list.get(i).getId(), StrUtil.isBlank(projectList.get(i1).getCustomValue()) ? "无" : projectList.get(i1).getCustomValue());
                        }
                        list.remove(i);
                        projectList.remove(i1);
                        i--;
                        break;
                    }
                }
            }

            //新增关系
            for (SaveCustomInfoInputVo.Project project : projectList) {
                BqyyMkxmb bqyyMkxmb = new BqyyMkxmb();
                bqyyMkxmb.setId(IdUtil.objectId());
                bqyyMkxmb.setMkid(list4.get(0).getMkid());
                bqyyMkxmb.setXmid(IdUtil.objectId());
                bqyyMkxmb.setXmmc(project.getCustomProjectName());
                bqyyMkxmb.setXmsx(project.getOrder());
                bqyyMkxmb.setSfzs(project.getIsShow() ? "0" : "1");

                newGxList.add(bqyyMkxmb);
                //记录自定义项目值
                if (project.getIsCustom()) {
                    valueMap.put(bqyyMkxmb.getId(), StrUtil.isBlank(project.getCustomValue()) ? "无" : project.getCustomValue());
                }
            }
            deleteGxList2.addAll(mergeLists(list, deleteGxList));
        }
        //查询默认项目
        List<BqyyXmb> bqyyXmbs = bqyyXmbService.selectDefaultXm();
        List<String> defaultXmIdList = bqyyXmbs.stream().map(BqyyXmb::getId).toList();
        //去除删除中的默认项目
        for (int i = 0; i < deleteGxList2.size(); i++) {
            if (defaultXmIdList.contains(deleteGxList2.get(i).getXmid())) {
                deleteGxList2.remove(i);
                i--;
            }
        }
        //新增关系
        resultMap.put("new", newGxList);
        //修改关系
        resultMap.put("update", updateGxList1);
        //删除关系
        resultMap.put("delete", deleteGxList2);

        return resultMap;
    }

    private Map<String, List<BqyyXmb>> dealWhiteBoardCustomInfoProject(UserInfo userInfo, Map<String, List<BqyyMkxmb>> gxMap, SaveCustomInfoInputVo reqParam, List<BqyyMkXmVo> customRelationship) {
        //新增项目
        List<BqyyXmb> newXmList = new ArrayList<>();
        //更新项目
        List<BqyyXmb> updateXmList = new ArrayList<>();
        //删除项目
        List<BqyyXmb> deleteXmList = new ArrayList<>();

        Map<String, List<BqyyXmb>> resultMap = new HashMap<>();

        List<BqyyMkxmb> newBqyyMkxmbs = gxMap.get("new");
        for (BqyyMkxmb newBqyyMkxmb : newBqyyMkxmbs) {
            BqyyXmb bqyyXmb = new BqyyXmb();
            bqyyXmb.setId(newBqyyMkxmb.getXmid());
            bqyyXmb.setXmmc(newBqyyMkxmb.getXmmc());
            bqyyXmb.setYljgid(userInfo.getOrganId());
            newXmList.add(bqyyXmb);
        }

        List<BqyyMkxmb> updateBqyyMkxmbs = gxMap.get("update");
        List<String> list = customRelationship.stream().map(BqyyMkXmVo::getXmid).toList();
        for (BqyyMkxmb updateBqyyMkxmb : updateBqyyMkxmbs) {
            //只修改自定义项目
            if (list.contains(updateBqyyMkxmb.getXmid())) {
                BqyyXmb bqyyXmb = new BqyyXmb();
                bqyyXmb.setId(updateBqyyMkxmb.getXmid());
                bqyyXmb.setXmmc(updateBqyyMkxmb.getXmmc());
                bqyyXmb.setUpdateTime(new Date());
                bqyyXmb.setUpdateBy(userInfo.getId());
                updateXmList.add(bqyyXmb);
            }
        }
        List<BqyyMkxmb> deleteBqyyMkxmbs = gxMap.get("delete");
        for (BqyyMkxmb deleteBqyyMkxmb : deleteBqyyMkxmbs) {
            if (list.contains(deleteBqyyMkxmb.getXmid())) {
                //只删除自定义项目
                BqyyXmb bqyyXmb = new BqyyXmb();
                bqyyXmb.setId(deleteBqyyMkxmb.getXmid());
                bqyyXmb.setUpdateBy(userInfo.getId());
                bqyyXmb.setUpdateTime(new Date());
                deleteXmList.add(bqyyXmb);
            }
        }

        //新增项目
        resultMap.put("new", newXmList);
        //更新项目
        resultMap.put("update", updateXmList);
        //删除项目
        resultMap.put("delete", deleteXmList);
        return resultMap;
    }

    private void dealWhiteBoardCustomInfoValue(UserInfo userInfo, SaveCustomInfoInputVo reqParam, Map<String, List<BqyyMkxmb>> gxMap, Map<String, String> valueMap){
        List<BqyyMkxmb> newBqyyMkxmbs = gxMap.get("new");
        List<BqyyMkxmb> updateBqyyMkxmbs = gxMap.get("update");
        List<BqyyMkxmb> bqyyMkxmbs = mergeLists(newBqyyMkxmbs, updateBqyyMkxmbs);
        //只修改了项目值
        List<String> list = bqyyMkxmbs.stream().map(BqyyMkxmb::getId).toList();
        List<String> list1 = valueMap.keySet().stream().filter(key -> !list.contains(key)).toList();
        for (String key : list1) {
            if (StrUtil.isNotBlank(valueMap.get(key))) {
                bqyyDpsjService.save(userInfo.getOrganId(), reqParam.getWard(), key, getCurrentDate(), valueMap.get(key));
            }
        }

        //修改了对应关系
        for (BqyyMkxmb bqyyMkxmb : bqyyMkxmbs) {
            if (StrUtil.isNotBlank(valueMap.get(bqyyMkxmb.getId()))) {
                bqyyDpsjService.save(userInfo.getOrganId(), reqParam.getWard(), bqyyMkxmb.getId(), getCurrentDate(), valueMap.get(bqyyMkxmb.getId()));
            }
        }
    }

    /**
     * 保存综合大屏数据
     *
     * @param userInfo
     * @param reqParam
     */
    public void saveWardScreenData(UserInfo userInfo, SaveWardScreenDataInputVo reqParam) {
        for (SaveWardScreenDataInputVo.Module module : reqParam.getModuleList()) {
            //处理存储三个值的模块
            if ("23".equals(module.getModuleType())) {
                for (SaveWardScreenDataInputVo.Project project : module.getProjectList()) {
                    bqyyDpsjService.saveOrUpdateXmz(userInfo.getOrganId(), null, project.getModuleProjectId(), getCurrentDate(), StrUtil.isBlank(project.getCustomValue()) ? "0" : project.getCustomValue(), StrUtil.isBlank(project.getCustomValue()) ? "0" : project.getCustomValue(), StrUtil.isBlank(project.getCustomValue2()) ? "0" : project.getCustomValue2(), StrUtil.isBlank(project.getCustomValue3()) ? "0" : project.getCustomValue3());
                }
                return;
            }
            //处理存储一个值的模块
            for (SaveWardScreenDataInputVo.Project project : module.getProjectList()) {
                bqyyDpsjService.save(userInfo.getOrganId(), null, project.getModuleProjectId(), getCurrentDate(), StrUtil.isBlank(project.getCustomValue()) ? "0" : project.getCustomValue());
            }
        }

    }

    public List<GetWardScreenDataOutputVo> getWardScreenData(UserInfo userInfo, GetWardScreenDataInputVo reqParam) {
        List<String> moduleTypeList = Arrays.asList(reqParam.getModuleType().split(","));
        //查询数据
        List<MkMkxmSj> mkMkxmSjs = bqyyMkbService.selectDefaultMkXmSj(moduleTypeList, userInfo.getOrganId());
        //封装返参
        List<GetWardScreenDataOutputVo> result = new ArrayList<>();

        for (String moduleType : moduleTypeList) {
            List<MkMkxmSj> mkMkxmSjList = optimizeStream(mkMkxmSjs, moduleType);
            GetWardScreenDataOutputVo getWardScreenDataOutputVo = new GetWardScreenDataOutputVo();
            getWardScreenDataOutputVo.setModuleType(mkMkxmSjList.get(0).getMklx());
            getWardScreenDataOutputVo.setModuleName(mkMkxmSjList.get(0).getMkmc());

            List<GetWardScreenDataOutputVo.Project> projectList = new ArrayList<>();
            for (MkMkxmSj mkMkxmSj : mkMkxmSjList) {
                GetWardScreenDataOutputVo.Project project = new GetWardScreenDataOutputVo.Project();
                project.setModuleProjectId(mkMkxmSj.getMkxmid());
                project.setCustomProjectName(mkMkxmSj.getXmmc());
                project.setUnit(mkMkxmSj.getDw());
                if ("23".equals(mkMkxmSj.getMklx())) {
                    project.setCustomValue(mkMkxmSj.getXmz());
                    project.setCustomValue2(mkMkxmSj.getXmz2());
                    project.setCustomValue3(mkMkxmSj.getXmz3());
                } else {
                    project.setCustomValue(mkMkxmSj.getXmz());
                }
                projectList.add(project);
            }
            getWardScreenDataOutputVo.setProjectList(projectList);

            result.add(getWardScreenDataOutputVo);
        }

        return result;
    }

    private static List<MkMkxmSj> optimizeStream(List<MkMkxmSj> mkMkxmSjs, String moduleType) {
        return mkMkxmSjs.stream()
                .filter(mkMkxmSj -> mkMkxmSj.getMklx().equals(moduleType))
                .collect(Collectors.groupingBy(MkMkxmSj::getXmid,
                        Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(MkMkxmSj::getUpdateTime)),
                                Optional::get)))
                .values().stream().sorted(Comparator.comparing(MkMkxmSj::getXmsx))
                .collect(Collectors.toList());
    }
}

