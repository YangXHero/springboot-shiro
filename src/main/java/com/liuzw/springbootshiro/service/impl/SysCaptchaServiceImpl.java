package com.liuzw.springbootshiro.service.impl;


import com.google.code.kaptcha.Producer;
import com.liuzw.springbootshiro.constants.Constants;
import com.liuzw.springbootshiro.constants.RedisConstants;
import com.liuzw.springbootshiro.service.RedisService;
import com.liuzw.springbootshiro.service.SysCaptchaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;


/**
 * 验证码
 *
 * @author lizw
 */
@Service("sysCaptchaService")
public class SysCaptchaServiceImpl implements SysCaptchaService {

    @Autowired
    private Producer producer;

    @Autowired
    private RedisService redisService;

    /**
     * 默认验证码，仅供测试使用
     */
    @Value("${default_captcha}")
    private String defaultCaptcha;

    @Value("${default_checkCaptcha}")
    private Boolean defaultCheckCaptcha;


    @Override
    public BufferedImage getCaptcha(String uuid) {

        if (StringUtils.isEmpty(uuid)) {
            return null;
        }

        //生成文字验证码
        String code = producer.createText();
        //放到redis中
        redisService.set(RedisConstants.CAPTCHA + uuid, code, 60);

        return producer.createImage(code);
    }

    @Override
    public boolean validate(String uuid, String captcha) {

        if (StringUtils.isEmpty(uuid) || StringUtils.isEmpty(captcha)) {
            return false;
        }

        if(defaultCaptcha.equals(captcha) && defaultCheckCaptcha) {
            return true;
        }

        String code = String.valueOf(redisService.get(RedisConstants.CAPTCHA + uuid));

        if (StringUtils.isEmpty(code)) {
            return false;
        }

        return captcha.equalsIgnoreCase(code);

    }
}
