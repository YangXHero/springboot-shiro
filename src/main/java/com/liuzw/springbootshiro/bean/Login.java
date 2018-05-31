
package com.liuzw.springbootshiro.bean;


import com.liuzw.springbootshiro.constants.Constants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 *  登录
 *
 * @author liuzw
 **/

@Data
public class Login {

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", dataType="String")
    @NotNull(message = Constants.USERNAME_NOT_EMPTY)
    private String userName;

    /**
     * 用户密码
     */
    @ApiModelProperty(value = "用户密码", dataType="String")
    @NotNull(message = Constants.PASSWORD_NOT_EMPTY)
    private String userPwd;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码", dataType="String")
    @NotNull(message = Constants.CAPTCHA_NOT_EMPTY)
    private String captcha;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "uuid", dataType="uuid")
    private String uuid;
}
