package com.liuzw.springbootshiro.service;


import com.liuzw.springbootshiro.bean.SysPwd;
import com.liuzw.springbootshiro.bean.SysUser;
import com.liuzw.springbootshiro.bean.SysUserQuery;
import com.liuzw.springbootshiro.model.SysUserModel;

import java.util.List;
import java.util.Map;

/**
 * 用户
 *
 * @author liuzw
 */
public interface SysUserService {


	/**
	 * 返回用户分页列表信息
	 *
	 * @param bean    用户数据
	 * @return       list<SysUserModel>
	 */
	List<SysUser> getList(SysUserQuery bean);

	/**
	 * 根据id返回用户信息
	 * @param id     id
	 * @return       SysUserModel
	 */
	SysUserModel getById(Long id);


	/**
	 * 获取用户所有角色
	 *
	 * @param id   用户id
	 * @return     List<Map<String, Object>>
	 */
	List<Map<String, Object>> getRoleByUserId(Long id);



	/**
	 *根据ID删除用户
	 *
	 * @param id     id
	 * @return       Boolean
	 */
	Boolean delete(Long id);

	/**
	 * 批量删除用户
	 *
	 * @param id     id
	 * @return       Boolean
	 */
	Boolean batchRemove(String id);

	/**
	 *增加用户
	 *
	 * @param bean   用户数据
	 * @return      Boolean
	 */
	Boolean insert(SysUser bean);

	/**
	 *更新用户
	 *
	 * @param bean  用户数据
	 * @return     Boolean
	 */
	Boolean update(SysUser bean);


	/**
	 * 修改密码
	 *
	 * @param bean  用户数据
	 * @return     Boolean
	 */
	Boolean updatePwd(SysPwd bean);

	/**
	 * 重置密码
	 *
	 * @param id         用户id
	 * @return           Integer
	 */
	Boolean resetPwd(Long id);


	/**
	 * 根据用户名获取用户信息
	 *
	 * @param userName  用户名
	 * @return          SysUserModel
	 */
	SysUserModel queryByUserName(String userName);

	/**
	 * 更新用户状态
	 *
	 * @param bean    用户
	 * @return       Boolean
	 */
	Boolean editStatus(SysUser bean);

}