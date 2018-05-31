package com.liuzw.springbootshiro.bean;

import com.liuzw.springbootshiro.common.BasePage;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * TABLE_NAME:(t_sys_dept)
 *
 * @author liuzw
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SysDeptQuery extends BasePage {

    /**
     * 部门名称
	 */
    @ApiModelProperty(value = "部门名称", name = "部门名称", required = true)
	private String name;

    /**
     * 部门编号
     */
    @ApiModelProperty(value = "部门编号", name = "部门编号", required = true)
    private String deptNum;

}