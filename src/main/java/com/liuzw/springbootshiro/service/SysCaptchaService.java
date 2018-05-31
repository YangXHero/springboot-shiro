package com.liuzw.springbootshiro.service;


import java.awt.image.BufferedImage;

/**
 * 验证码
 *
 * @author
 */
public interface SysCaptchaService {

    /**
     * 获取图片验证码
     *
     * @param uuid 验证码唯一主键id
     * @return     BufferedImage
     */
    BufferedImage getCaptcha(String uuid);

    /**
     * 验证码效验
     * @param uuid  uuid
     * @param captcha  验证码
     * @return  true：成功  false：失败
     */
    boolean validate(String uuid, String captcha);
}
