package com.liuzw.springbootshiro.bean;

import com.liuzw.springbootshiro.common.BasePage;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * TABLE_NAME:(t_sys_role)
 * 角色查询使用
 * @author liuzw 
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SysRoleQuery extends BasePage {

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称", name = "角色名称")
    private String roleName;

}