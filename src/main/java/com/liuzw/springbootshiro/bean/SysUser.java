package com.liuzw.springbootshiro.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * TABLE_NAME:(t_sys_user)
 *
 * @author liuzw
 */


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUser {

    /**
     * id
     */
    @NotNull(message = "Id不能为空")
    @ApiModelProperty(value = "id", name = "id", required = true)
    private Long id;

    /**
     * 账户
     */
    @NotNull(message = "账号不能为空")
    @ApiModelProperty(value = "账户", name = "账户", required = true)
    private String userName;


    /**
     * 姓名
     */
    @NotNull(message = "姓名不能为空")
    @ApiModelProperty(value = "姓名", name = "姓名")
    private String name;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱", name = "邮箱")
    private String email;

    /**
     * 性别  0：女   1：男
     */
    @NotNull(message = "性别不能为空")
    @ApiModelProperty(value = "性别  0：女   1：男", name = "性别  0：女   1：男")
    private Integer sex;

    /**
     * 手机号
     */
    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "1[34578]\\d{9}")
    @ApiModelProperty(value = "手机号", name = "手机号")
    private String mobile;

    /**
     * 状态  0：禁用   1：正常
     */
    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态  0：禁用   1：正常", name = "状态  0：禁用   1：正常")
    private Integer status;

    /**
     * 部门ID
     */
    @NotNull(message = "部门不能为空")
    @ApiModelProperty(value = "部门ID", name = "部门ID")
    private Long deptId;

    /**
     * 是否管理员
     */
    @ApiModelProperty(value = "是否管理员", name = "是否管理员")
    private Integer adminFlag;

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID", name = "角色ID")
    private List<Long> roleIds;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", name = "密码")
    private String password;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门", name = "部门")
    private String deptName;


    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称", name = "角色名称")
    private String roleName;

}