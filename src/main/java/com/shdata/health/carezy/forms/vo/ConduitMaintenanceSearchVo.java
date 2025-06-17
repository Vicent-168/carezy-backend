package com.shdata.health.carezy.forms.vo;

import com.shdata.health.common.base.PageSearch;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * 导管置管_导管维护表  搜索VO
 *
 * @author myy
 * @date 2024-11-20
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ConduitMaintenanceSearchVo extends PageSearch<ConduitMaintenanceVo> {

}
