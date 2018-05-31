package com.liuzw.springbootshiro.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TABLE_NAME:(t_sys_dept)
 *
 * @author liuzw
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysDept {

    /**
     * id
	 */
    @ApiModelProperty(value = "id", name = "id" )
	private Long id;
    /**
     * 部门编码
	 */
    @ApiModelProperty(value = "部门编码", name = "部门编码", required = true)
	private String deptNum;

    /**
     * 上级部门ID，一级部门为0
	 */
    @ApiModelProperty(value = "上级部门ID，一级部门为0", name = "上级部门ID，一级部门为0", required = true)
	private Long parentId;

    /**
     * 部门名称
	 */
    @ApiModelProperty(value = "部门名称", name = "部门名称", required = true)
	private String name;

    /**
     * 排序
	 */
    @ApiModelProperty(value = "排序", name = "排序", required = true)
	private Integer orderNum;

    /**
     * 是否分页(主要用于其他地方调用getList时不用分页)
     */
    @ApiModelProperty(value = "是否分页", name = "是否分页")
    private Boolean isPage;

}