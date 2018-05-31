
package com.liuzw.springbootshiro.constants;

/**
 * 常量
 *
 * @author liuzw
 */
public class Constants {

    /**
     * 用户名不能为空
     */
    public static final String USERNAME_NOT_EMPTY = "用户名不能为空";

    /**
     * 用户名不能为空
     */
    public static final String PASSWORD_NOT_EMPTY = "密码不能为空";

    /**
     * 验证码不能为空
     */
    public static final String CAPTCHA_NOT_EMPTY = "验证码不能为空";


    /**
     * 用户名或密码错误
     */
    public static final String USERNAME_PASSWORD_ERROR = "用户名或密码错误";

    /**
     * 密码错误
     */
    public static final String PASSWORD_ERROR = "密码错误";

    /**
     * 验证码错误
     */
    public static final String CAPTCHA_ERROR = "验证码不能为空";


    /**
     * 用户被禁用
     */
    public static final String STATUS_ERROR = "用户被禁用";


    /**
     * id不能为空
     */
    public static final String ID_NOT_EMPTY = "id不能为空";


    /**
     * 当前账户没有权限重置管理员密码
     */
    public static final String USER_NOT_PERMS_ERROR = "当前账户没有权限重置管理员密码";


    /**
     * 系统内置超级管理员
     */
    public static final Long DEFULT_USER = 1L;

    /**
     * 内置管理员不能删除
     */
    public static final String DEFULT_USER_ERROR = "内置管理员不能删除";

    /**
     * 系统内置超级管理员角色
     */
    public static final Long DEFULT_ROLE = 1L;

    /**
     * 系统内置管理员角色不能修改
     */
    public static final String DEFULT_ROLE_UPDATE_ERROR = "系统内置管理员角色不能修改";

    /**
     * 系统内置管理员角色不能修改
     */
    public static final String DEFULT_ROLE_DELETE_ERROR = "系统内置管理员角色不能删除";

    /**
     * 默认密码
     */
    public static final String DEFULT_PASSWORD = "123456";

    /**
     * 当前菜单包含子菜单或按钮，不能删除
     */
    public static final String MENU_ERROR = "当前菜单包含子菜单或按钮，不能删除";

    /**
     * 角色名称已存在，不能删除
     */
    public static final String ROLE_ERROR = "角色名称已存在";

}
