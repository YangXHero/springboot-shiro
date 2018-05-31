package com.liuzw.springbootshiro.service.impl;

import com.liuzw.springbootshiro.bean.SysMenu;
import com.liuzw.springbootshiro.bean.SysMenuQuery;
import com.liuzw.springbootshiro.mapper.SysMenuMapper;
import com.liuzw.springbootshiro.model.SysMenuModel;
import com.liuzw.springbootshiro.service.SysMenuService;
import com.liuzw.springbootshiro.utils.CopyDataUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 *  SysMenuServiceImpl
 *
 * @author liuzw
 */
@Service("sysMenuService")
public class SysMenuServiceImpl  implements SysMenuService {


	@Autowired
	private SysMenuMapper sysMenuMapper;


	@Override
	public List<SysMenuModel> getList(SysMenuQuery dto) {
		List<SysMenuModel> menuList = sysMenuMapper.getMenu(dto);
		List<Long> ids = menuList.stream().map(SysMenuModel::getId).collect(Collectors.toList());
		if (ids != null && ids.size() > 0) {
			menuList.addAll(sysMenuMapper.getButton(dto.getStatus(), ids));
		}
		return menuList;
	}

	@Override
	public List<SysMenuModel> getMenuSelectList() {
		List<SysMenuModel> menuList = sysMenuMapper.getMenuSelectList();
		List<Long> ids = menuList.stream().map(SysMenuModel::getId).collect(Collectors.toList());
		menuList.addAll(sysMenuMapper.getButton(1, ids));
		return menuList;
	}

	@Override
	public List<Map<String, Object>> getMenuNoButton() {
		List<SysMenuModel> menuList = sysMenuMapper.getMenuSelectList();
		return formatMenu(menuList);
	}

	@Override
	public SysMenuModel getById(Long id) {
		return sysMenuMapper.selectByPrimaryKey(id);
	}

	@Override
	public Boolean checkMenuByParentId(Long id) {
		return sysMenuMapper.checkMenuByParentId(id) > 0;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,
			rollbackFor = {RuntimeException.class, Exception.class})
	public Boolean insert(SysMenu sysMenu) {
		SysMenuModel insertObj = CopyDataUtil.copyObject(sysMenu, SysMenuModel.class);
		insertObj.setId(null);
		sysMenuMapper.insertSelective(insertObj);
		//添加菜单之后给超级管理员加上此菜单的权限
		return sysMenuMapper.insertRoleMenu(insertObj.getId()) > 0;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,
			rollbackFor = {RuntimeException.class, Exception.class})
	public Boolean update(SysMenu sysMenu) {
		SysMenuModel updateObj = CopyDataUtil.copyObject(sysMenu, SysMenuModel.class);
		return sysMenuMapper.updateByPrimaryKeySelective(updateObj) > 0;
	}

	@Override
	public List<SysMenu> getMenuListById(Long id) {
		return sysMenuMapper.getMenuListById(id);
	}

	@Override
	public Boolean editStatus(SysMenu dto) {
		return sysMenuMapper.editStatus(dto) > 0;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,
			rollbackFor = {RuntimeException.class, Exception.class})
	public Boolean delete(Long id) {
		sysMenuMapper.deleteRoleByMenuId(id);
		return sysMenuMapper.deleteByPrimaryKey(id) > 0;
	}


	private List<Map<String, Object>> formatMenu(List<SysMenuModel> rootMenu) {
		List<Map<String, Object>> mapList = new ArrayList<>(rootMenu.size());
		// 先找到所有的一级菜单
		List<SysMenuModel> menuList = rootMenu.stream().filter(menu -> menu.getParentId() == 0).collect(Collectors.toList());

		if (menuList.size() == 0) {
			return null;
		}

		Map<String, Object> map;
		for (SysMenuModel sysMenuModel : menuList) {
			map = new HashMap<>(4);
			map.put("id", sysMenuModel.getId());
			map.put("value", sysMenuModel.getName());
			mapList.add(map);
			mapList.addAll(getChild(sysMenuModel.getId(), rootMenu, ""));
		}
		return mapList;
	}

	/**
	 * 递归查找子菜单
	 *
	 * @param id        当前菜单id
	 * @param rootMenu  要查找的列表
	 * @return          List<SysMenuResDto>
	 */
	private List<Map<String, Object>> getChild(Long id, List<SysMenuModel> rootMenu, String prefix) {
		// 子菜单
		List<Map<String, Object>> childList = new ArrayList<>(rootMenu.size());

		Map<String, Object> map;
		for (SysMenuModel menu : rootMenu) {
			// 遍历所有节点，将父菜单id与传过来的id比较
			if (menu.getParentId().equals(id)) {
				map = new HashMap<>(4);
				map.put("id", menu.getId());
				map.put("value", prefix + "├─ " + menu.getName());
				childList.add(map);
				// 递归
				childList.addAll(getChild(menu.getId(), rootMenu, "　" + prefix));
			}
		}

		if (childList.size() > 0) {
			Integer size = childList.size() - 1 > 0 ? childList.size() - 1 : 0;
			Map<String, Object> tempMap = childList.get(size);
			tempMap.put("value", MapUtils.getString(tempMap, "value").replace("├─", "└─"));
			childList.set(size, tempMap);
		}
		return childList;
	}

}