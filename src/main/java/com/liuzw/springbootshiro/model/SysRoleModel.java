package com.liuzw.springbootshiro.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * TABLE_NAME:(t_sys_role)
 *
 * @author liuzw
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_sys_role")
public class SysRoleModel {


    /**
     * id
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;


    /**
     * 角色名称
	 */
    @Column(name = "role_name")
	private String roleName;


    /**
     * 备注
	 */
    @Column(name = "remark")
	private String remark;


    /**
     * 部门ID
	 */
    @Column(name = "dept_id")
	private Long deptId;


    /**
     * 创建时间
	 */
    @Column(name = "create_time")
	private String createTime;

    /**
     * 状态
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 是否管理员角色(1,是;0,否)
     */
    @Column(name = "admin_role_flag")
    private Integer adminRoleFlag;

}