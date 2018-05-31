package com.liuzw.springbootshiro.bean;

import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * 修改密码
 *
 * @author liuzw
 * @version V1.0
 * @date 2018/4/26 9:02
 **/
@Data
public class SysPwd {


    /**
     * id
     */
    private Long id;

    /**
     * 原始密码
     */
    @NotNull(message = "旧密码不能为空")
    private String password;

    /**
     * 新密码
     */
    @NotNull(message = "新密码不能为空")
    private String newPassword;

    /**
     * 确认密码
     */
    @NotNull(message = "新密码不能为空")
    private String newPasswordOnce;
}
