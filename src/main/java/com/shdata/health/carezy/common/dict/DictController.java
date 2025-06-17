package com.shdata.health.carezy.common.dict;

import com.shdata.health.carezy.common.utils.UserUtils;
import com.shdata.health.common.dict.Dict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 通用接口
 */
@RestController
@RequestMapping("dict")
public class DictController {

    private final DictService dictService;

    public DictController(DictService dictService) {
        this.dictService = dictService;
    }

    /**
     * 通用字典查询
     * <p>
     * 若同时查询多个字典，需用逗号隔开。
     * <p>
     */
    @GetMapping("types")
    public ResponseEntity<Map<String, List<Dict>>> type(String types) {
        return ResponseEntity.ok(dictService.findByTypes(types));
    }

    /**
     * 通过类型和层级查询字典
     */
    @GetMapping("findByTypeAndCj")
    public ResponseEntity<List<Dict>> findByTypeAndCj(String type, String cj) {
        return ResponseEntity.ok(dictService.findByTypeAndCj(type, cj));
    }

    /**
     * 通过类型和上级代码查询字典
     */
    @GetMapping("findByTypeAndSjdm")
    public ResponseEntity<List<Dict>> findByTypeAndSjdm(String type, String sjdm) {
        return ResponseEntity.ok(dictService.findByTypeAndSjdm(type, sjdm));
    }

    /**
     * 登录用户护士类型
     */
    @GetMapping("nurseType")
    public ResponseEntity<String> nurseType() {
        return ResponseEntity.ok(UserUtils.getNurseType());
    }

    /**
     * 具有树形结构的字典查询
     */
    @GetMapping("typesByTree")
    public ResponseEntity<Map<String, List<DictVo>>> typesByTree(String types) {
        return ResponseEntity.ok(dictService.findByTypesByTree(types));
    }

}
