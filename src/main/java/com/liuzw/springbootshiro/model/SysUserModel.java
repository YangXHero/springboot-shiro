package com.liuzw.springbootshiro.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * TABLE_NAME:(t_sys_user)
 *
 * @author liuzw
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_sys_user")
public class SysUserModel {

    /**
     * id
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;


    /**
     * 用户名
	 */
    @Column(name = "name")
	private String name;


    /**
     * 账户
	 */
    @Column(name = "username")
	private String userName;


    /**
     * 密码
	 */
    @Column(name = "password")
	private String password;


    /**
     * 邮箱
	 */
    @Column(name = "email")
	private String email;


    /**
     * 性别  0：女   1：男
	 */
    @Column(name = "sex")
	private Integer sex;


    /**
     * 手机号
	 */
    @Column(name = "mobile")
	private String mobile;


    /**
     * 状态  0：禁用   1：正常
	 */
    @Column(name = "status")
	private Integer status;


    /**
     * 部门ID
	 */
    @Column(name = "dept_id")
	private Long deptId;


    /**
     * 上次登录时间
	 */
    @Column(name = "last_login_time")
	private String lastLoginTime;


    /**
     * 创建时间
	 */
    @Column(name = "create_time")
	private String createTime;


    /**
     * 更新时间
	 */
    @Column(name = "update_time")
	private String updateTime;

    /**
     * 是否第一次登陆
     */
    @Column(name = "first_login_flag")
    private Integer firstLoginFlag;

    /**
     * 是否管理员
     */
    @Column(name = "admin_flag")
    private Integer adminFlag;

}