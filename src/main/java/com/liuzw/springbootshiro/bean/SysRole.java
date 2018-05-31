package com.liuzw.springbootshiro.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * TABLE_NAME:(t_sys_role)
 *
 * @author liuzw 
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysRole {

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    @ApiModelProperty(value = "id", name = "id", required = true)
    private Long id;

    /**
     * 角色名称
     */
    @NotNull(message = "角色名称不能为空")
    @ApiModelProperty(value = "角色名称", name = "角色名称")
    private String roleName;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", name = "备注")
    private String remark;

    /**
     * 部门ID
     */
    @ApiModelProperty(value = "部门ID", name = "部门ID")
    private Long deptId;

    /**
     * 状态(1,启用;0,禁用)
     */
    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态(1,启用;0,禁用)", name = "状态(1,启用;0,禁用)")
    private Integer status;

    /**
     * 是否查询禁用角色
     */
    @ApiModelProperty(value = "是否查询禁用角色", name = "是否查询禁用角色")
    private Boolean isSelectDisableRole;

    /**
     * 角色拥有菜单id
     */
    private List<Integer> menuIds;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", name = "创建时间")
    private String createTime;

    /**
     * 是否管理员角色(1,是;0,否)
     */
    @ApiModelProperty(value = "是否管理员角色", name = "是否管理员角色")
    private Integer adminRoleFlag;

}