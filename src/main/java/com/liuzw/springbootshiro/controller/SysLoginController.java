
package com.liuzw.springbootshiro.controller;

import com.liuzw.springbootshiro.bean.Login;
import com.liuzw.springbootshiro.bean.UserLoginInfo;
import com.liuzw.springbootshiro.common.ResultData;
import com.liuzw.springbootshiro.constants.Constants;
import com.liuzw.springbootshiro.service.SysCaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 登录相关
 * 
 * @author liuzw
 */
@Api(description = "登陆接口")
@Validated
@RestController
@RequestMapping
public class SysLoginController extends BaseController {

	@Autowired
	private SysCaptchaService sysCaptchaService;


	/**
	 * 获取验验证码
	 *
	 */
	@ApiOperation(value = "获取验验证码", notes = "获取验验证码")
	@GetMapping(value = "/sys/captcha")
	public void captcha(HttpServletResponse response, String uuid) throws IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");
		// 获取图片验证码
		BufferedImage image = sysCaptchaService.getCaptcha(uuid);
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
		IOUtils.closeQuietly(out);
	}

	/**
	 * 登录
	 */
	@ApiOperation(value = "用户登陆", notes = "用户登陆")
	@PostMapping(value = "/sys/login")
	public ResultData<UserLoginInfo> login(@Valid @RequestBody Login bean) {
		 Boolean captcha = sysCaptchaService.validate(bean.getUuid(), bean.getCaptcha());
		 if(!captcha){
		 	return ResultData.createErrorResult(Constants.CAPTCHA_ERROR);
		 }
		UsernamePasswordToken token = new UsernamePasswordToken(bean.getUserName(), bean.getUserPwd());
		 //shiro核心主体
		Subject subject = SecurityUtils.getSubject();
		try {
			//shiro登录 会调用 MyshiroRealm 中 doGetAuthenticationInfo 方法
			subject.login(token);
			UserLoginInfo userLoginInfo = (UserLoginInfo) subject.getPrincipal();
			userLoginInfo.setToken(subject.getSession().getId().toString());
			return ResultData.createSelectSuccessResult(userLoginInfo);
		} catch (DisabledAccountException e) {
			return ResultData.createErrorResult(Constants.STATUS_ERROR);
		}  catch (UnknownAccountException | IncorrectCredentialsException e) {
			return ResultData.createErrorResult(Constants.USERNAME_PASSWORD_ERROR);
		}
	}


	/**
	 *  动态更新权限
	 *
	 * @return        ResultData<UserLoginDto>
	 */
	@ApiOperation(value = "动态更新权限", notes = "动态更新权限")
	@PostMapping(value = "/sys/dynamicUpdatePerms",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResultData<UserLoginInfo> dynamicUpdatePerms() {

		return ResultData.createAuthErrorResult();
	}

	/**
	 * 退出
	 */
	@ApiOperation(value = "退出登录", notes = "用户退出")
	@PostMapping(value = "/sys/logout", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResultData<Integer> logout(@RequestBody UserLoginInfo dto) {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return ResultData.createSuccessResult(null,null);
	}

	@ApiOperation(value = "退出登录", notes = "用户退出")
	@PostMapping(value = "/sys/unAuth", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResultData unAuth(){
		return ResultData.createAuthErrorResult();
	}

}
