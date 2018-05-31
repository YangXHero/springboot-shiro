package com.liuzw.springbootshiro.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * TABLE_NAME:(t_sys_menu)
 * 菜单
 *
 * @author liuzw
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_sys_menu")
public class SysMenuModel {


    /**
     * id
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;


    /**
     * 父菜单ID，一级菜单为0
	 */
    @Column(name = "parent_id")
	private Long parentId;


    /**
     * 菜单名称
	 */
    @Column(name = "name")
	private String name;


    /**
     * 菜单URL
	 */
    @Column(name = "path")
	private String path;


    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
	 */
    @Column(name = "perms")
	private String perms;

    /**
     * 类型   0：目录   1：菜单   2：按钮
	 */
    @Column(name = "type")
	private Integer type;

    /**
     * 菜单图标
	 */
    @Column(name = "iconCls")
	private String iconCls;


    /**
     * 排序
	 */
    @Column(name = "order_num")
	private Integer orderNum;

    /**
     * 状态
     */
    @Column(name = "status")
    private Integer status;


}