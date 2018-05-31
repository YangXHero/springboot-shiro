package com.liuzw.springbootshiro.bean;

import com.liuzw.springbootshiro.common.BasePage;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * TABLE_NAME:(t_sys_user)
 *  查询用
 * @author liuzw
 */


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SysUserQuery extends BasePage {

    /**
     * 账户
     */
    @ApiModelProperty(value = "账户", name = "账户")
    private String userName;


    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", name = "姓名")
    private String name;


}