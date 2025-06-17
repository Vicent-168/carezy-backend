package com.shdata.health.carezy.whiteBoard.controller;

import com.shdata.health.carezy.common.utils.UserUtils;
import com.shdata.health.carezy.whiteBoard.service.WardScreenOperationService;
import com.shdata.health.carezy.whiteBoard.vo.*;
import com.shdata.health.his.entity.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 病区大屏运营接口
 *
 * @author ljt
 * 3@date 2024-10-17
 */
@Validated
@RestController
@RequestMapping("/whiteBoard/wardScreenOperation")
public class WardScreenOperationController {

    private final WardScreenOperationService wardScreenOperationService;

    public WardScreenOperationController(WardScreenOperationService wardScreenOperationService) {
        this.wardScreenOperationService = wardScreenOperationService;
    }

    /**
     * 获取文本写入信息
     *
     * @return
     */
    @PostMapping("/getTextWriteInformation")
    public ResponseEntity<GetTextWriteInformationOutputVo> getTextWriteInformation(@RequestBody @Validated GetTextWriteInformationInputVo reqParam) {
        return ResponseEntity.ok(wardScreenOperationService.getTextWriteInformation(reqParam));
    }

    /**
     * 保存文本写入
     *
     * @param reqParam
     * @return
     */
    @PostMapping("/saveTextWrite")
    public ResponseEntity<String> saveTextWrite(@RequestBody @Validated SaveTextWriteInputVo reqParam){
        UserInfo userInfo = UserUtils.getUserInfo();
        wardScreenOperationService.saveTextWrite(userInfo, reqParam);
        return ResponseEntity.ok("保存成功");
    }

    /**
     * 获取白板维护信息
     */
    @PostMapping("/getCustomProjectList")
    public ResponseEntity<List<GetCustomInfoOutputVo>> getCustomProjectList(@RequestBody @Validated GetCustomProjectListInputVo reqParam) {
        UserInfo userInfo = UserUtils.getUserInfo();
        return ResponseEntity.ok(wardScreenOperationService.getCustomProjectList(userInfo, reqParam));
    }


    /**
     * 保存白板维护信息
     */
    @PostMapping("/saveCustomInfo")
    public ResponseEntity<String> saveCustomInfo(@RequestBody @Validated SaveCustomInfoInputVo reqParam) throws Exception {
        UserInfo userInfo = UserUtils.getUserInfo();
        wardScreenOperationService.saveWhiteBoardCustomInfo(userInfo,reqParam);
        return ResponseEntity.ok("保存成功");
    }


    /**
     * 获取病区基本信息
     *
     * @return
     */
    @PostMapping("/wardBaseInfo")
    public ResponseEntity<WardBaseInfoOutputVo> getWardBaseInfo(@RequestBody @Validated WardBaseInfoInputVo reqParam) {
        UserInfo userInfo = UserUtils.getUserInfo();
        return ResponseEntity.ok(wardScreenOperationService.getWardBaseInfo(userInfo, reqParam));
    }

    /**
     * 保存护士信息
     *
     * @param reqParam
     * @return
     */
    @PostMapping("/saveNurseInfo")
    public ResponseEntity<String> saveNurseInfo(@RequestBody @Validated SaveNurseInfoInputVo reqParam) {
        UserInfo userInfo = UserUtils.getUserInfo();
        return ResponseEntity.ok(wardScreenOperationService.saveNurseInfo(userInfo, reqParam));
    }

    /**
     * 保存病区房间明细
     *
     * @param wardRoomDetails 房间明细数据
     * @return 响应结果
     */
    @PostMapping("/saveWardRoomDetails")
    public ResponseEntity<String> saveWardRoomDetails(@RequestBody @Validated WardRoomDetailsInputVo wardRoomDetails) {
        UserInfo userInfo = UserUtils.getUserInfo();
        return ResponseEntity.ok(wardScreenOperationService.saveWardRoomDetails(userInfo, wardRoomDetails));
    }

    /**
     * 查询病区运营自定义数据
     */
    @PostMapping("/getOperationCustomData")
    public ResponseEntity<List<GetCustomInfoOutputVo>> getOperationCustomData(@RequestBody @Validated GetCustomProjectListInputVo reqParam) {
        UserInfo userInfo = UserUtils.getUserInfo();
        return ResponseEntity.ok(wardScreenOperationService.getOperationCustomData(userInfo, reqParam));
    }

    /**
     * 保存病区运营自定义数据
     */
    @PostMapping("/saveOperationCustomData")
    public ResponseEntity<String> saveOperationCustomData(@RequestBody @Validated SaveCustomInfoInputVo reqParam) {
        UserInfo userInfo = UserUtils.getUserInfo();
        wardScreenOperationService.saveOperationCustomData(userInfo, reqParam);
        return ResponseEntity.ok("保存成功");
    }

    /**
     * 查询综合大屏数据
     */
    @PostMapping("/getWardScreenData")
    public ResponseEntity<List<GetWardScreenDataOutputVo>> getWardScreenData(@RequestBody @Validated GetWardScreenDataInputVo reqParam) {
        UserInfo userInfo = UserUtils.getUserInfo();
        return ResponseEntity.ok(wardScreenOperationService.getWardScreenData(userInfo,reqParam));
    }

    /**
     * 保存综合大屏数据
     */
    @PostMapping("/saveWardScreenData")
    public ResponseEntity<String> saveWardScreenData(@RequestBody @Validated SaveWardScreenDataInputVo reqParam) {
        UserInfo userInfo = UserUtils.getUserInfo();
        wardScreenOperationService.saveWardScreenData(userInfo, reqParam);
        return ResponseEntity.ok("保存成功");
    }

}
