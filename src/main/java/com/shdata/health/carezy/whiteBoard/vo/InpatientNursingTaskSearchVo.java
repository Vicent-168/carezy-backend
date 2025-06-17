package com.shdata.health.carezy.whiteBoard.vo;

import com.shdata.health.common.base.PageSearch;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 住院护理任务表  搜索VO
 *
 * @author ljt
 * @date 2024-10-21
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class InpatientNursingTaskSearchVo extends PageSearch<InpatientNursingTaskVo> {

}
