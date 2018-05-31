
package com.liuzw.springbootshiro.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *
 * @author liuzw
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginInfo implements Serializable {

    public static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", dataType="String")
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", dataType="String")
    private String userName;

    /**
     * 用户姓名
     */
    @ApiModelProperty(value = "用户姓名", dataType="String")
    private String name;

    /**
     * token
     */
    @ApiModelProperty(value = "token", dataType="String")
    private String token;


    /**
     * 菜单
     */
    @ApiModelProperty(value = "菜单")
    private List<SysMenu> menuList;

    /**
     * 权限
     */
    @ApiModelProperty(value = "权限")
    private Set<String> permsList;

    /**
     * 角色
     */
    @ApiModelProperty(value = "角色")
    private Set<String> roleList;

    /**
     * 是否第一次登陆
     */
    private Boolean firstLoginFlag;

    /**
     * 超级管理员
     */
    private Integer adminFlag;
}
