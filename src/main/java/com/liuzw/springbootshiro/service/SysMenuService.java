package com.liuzw.springbootshiro.service;


import com.liuzw.springbootshiro.bean.SysMenu;
import com.liuzw.springbootshiro.bean.SysMenuQuery;
import com.liuzw.springbootshiro.model.SysMenuModel;

import java.util.List;
import java.util.Map;

/**
 *  菜单管理
 * 
 * @author liuzw
 */
public interface SysMenuService {

	/**
	 * 返回菜单分页列表信息
	 *
	 * @param bean    菜单数据
	 * @return       list<SysMenuModel>
	 */
	List<SysMenuModel> getList(SysMenuQuery bean);

	/**
	 * 获取菜单所有数据
	 *
	 * @return       list<SysMenuModel>
	 */
	List<SysMenuModel> getMenuSelectList();


	/**
	 * 获取菜单不包含button
	 *
	 * @return       List<Map<String, Object>>
	 */
	List<Map<String, Object>> getMenuNoButton();


	/**
	 * 根据id返回菜单信息
	 * @param id     id
	 * @return       SysMenuModel
	 */
	SysMenuModel getById(Long id);

	/**
	 * 判断菜单是否包含下级
	 *
	 * @param id     id
	 * @return       Boolean
	 */
	Boolean checkMenuByParentId(Long id);

	/**
	 *根据ID删除菜单
	 *
	 * @param id     id
	 * @return       Boolean
	 */
	Boolean delete(Long id);

	/**
	 *增加菜单
	 *
	 * @param bean   菜单数据
	 * @return      Boolean
	 */
	Boolean insert(SysMenu bean);

	/**
	 *更新菜单
	 *
	 * @param bean  菜单数据
	 * @return     Boolean
	 */
	Boolean update(SysMenu bean);


	/**
	 * 获取该用户所有菜单权限
	 *
	 * @param id     用户id
	 * @return       List<SysMenuResDto>
	 */
	List<SysMenu> getMenuListById(Long id);

	/**
	 * 更新菜单状态
	 *
	 * @param bean    菜单
	 * @return       Boolean
	 */
	Boolean editStatus(SysMenu bean);

}