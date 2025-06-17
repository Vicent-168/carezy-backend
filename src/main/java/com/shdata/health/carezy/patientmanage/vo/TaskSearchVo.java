package com.shdata.health.carezy.patientmanage.vo;

import com.shdata.health.common.base.PageSearch;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * 住院护理任务  搜索VO
 *
 * @author panwei
 * @date 2024-10-23
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TaskSearchVo extends PageSearch<TaskVo> {

}
