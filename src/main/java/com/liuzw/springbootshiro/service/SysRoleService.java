package com.liuzw.springbootshiro.service;


import com.liuzw.springbootshiro.bean.SysRole;
import com.liuzw.springbootshiro.bean.SysRoleQuery;
import com.liuzw.springbootshiro.model.SysRoleModel;

import java.util.List;

/**
 * interface SysRoleservice
 *
 * @author liuzw
 */
public interface SysRoleService {


	/**
	 * 返回分页列表信息
	 *
	 * @param sysRole    数据
 	 * @return       list<SysRole>
     */
    List<SysRoleModel> getList(SysRoleQuery sysRole);


	/**
	 * 所有没有禁用的角色
	 *
	 * @return       list<SysRole>
	 */
	List<SysRoleModel> getRoleListByStatus();


    /**
     * 根据id返回信息
     * @param id     id
     * @return       SysUserModel
     */
	SysRoleModel getById(Long id);

	/**
	 * 获取角色所有菜单权限
	 *
	 * @param id   角色id
	 * @return     List<String>
	 */
	List<Integer> getMenuByRoleId(Long id);

	/**
	 * 判断当前角色是否存在
	 *
	 * @param roleName 角色名称
	 * @return Boolean
	 */

	Boolean checkRoleNameIsExist(String roleName);

    /**
     * 根据ID删除
     *
     * @param id     id
     * @return       Boolean
     */
    Boolean delete(Long id);

	/**
	 * 批量删除角色
	 *
	 * @param ids     id
	 * @return       Boolean
	 */
	Boolean batchRemove(String ids);

    /**
     *增加
     *
     * @param sysRole   数据
     * @return      Boolean
     */
    Boolean insert(SysRole sysRole);

    /**
	 *更新
	 *
	 * @param sysRole  数据
	 * @return     Boolean
	 */
    Boolean update(SysRole sysRole);

	/**
	 * 更新角色状态
	 *
	 * @param dto    角色
	 * @return       Boolean
	 */
	Boolean editStatus(SysRole dto);
	
}