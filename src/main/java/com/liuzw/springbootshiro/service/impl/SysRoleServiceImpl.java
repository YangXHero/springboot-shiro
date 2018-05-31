package com.liuzw.springbootshiro.service.impl;

import com.liuzw.springbootshiro.bean.SysRole;
import com.liuzw.springbootshiro.bean.SysRoleQuery;
import com.liuzw.springbootshiro.constants.Constants;
import com.liuzw.springbootshiro.mapper.SysRoleMapper;
import com.liuzw.springbootshiro.model.SysRoleModel;
import com.liuzw.springbootshiro.service.SysRoleService;
import com.liuzw.springbootshiro.utils.CopyDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 *  角色
 *
 *  @author liuzw
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {
		
		
	@Autowired
	private SysRoleMapper sysRoleMapper;


	@Override
	public List<SysRoleModel> getList(SysRoleQuery sysRoleDto) {
		//PageHelper.startPage(sysRoleDto.getPageNumberByDefault(), sysRoleDto.getPageSizeByDefault());
		return sysRoleMapper.getList(sysRoleDto);
    }

	@Override
	public List<SysRoleModel> getRoleListByStatus() {
		return sysRoleMapper.getRoleListByStatus();
	}

	@Override
    public SysRoleModel getById(Long id) {
    return sysRoleMapper.selectByPrimaryKey(id);
    }

	@Override
	public List<Integer> getMenuByRoleId(Long id) {
		return sysRoleMapper.getMenuByRoleId(id);
	}

	@Override
	public Boolean checkRoleNameIsExist(String roleName) {
		SysRoleModel model = SysRoleModel.builder().roleName(roleName).build();
		return sysRoleMapper.selectOne(model) != null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,
			rollbackFor = {RuntimeException.class, Exception.class})
	public Boolean insert(SysRole dto) {
		SysRoleModel insertObj = CopyDataUtil.copyObject(dto, SysRoleModel.class);
		insertObj.setId(null);
		boolean flag = sysRoleMapper.insertSelective(insertObj) > 0;
		dto.setId(insertObj.getId());
		if (dto.getMenuIds() != null && dto.getMenuIds().size() > 0) {
			sysRoleMapper.insertMenuByRoleId(dto);
		}
		return flag;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,
			rollbackFor = {RuntimeException.class, Exception.class})
	public Boolean update(SysRole dto) {
		SysRoleModel updateObj = CopyDataUtil.copyObject(dto, SysRoleModel.class);
		List<Long> ids = new ArrayList<>();
		ids.add(dto.getId());
		sysRoleMapper.deleteMenuByRoleId(ids);
		if (dto.getMenuIds() != null && dto.getMenuIds().size() > 0) {
			sysRoleMapper.insertMenuByRoleId(dto);
		}
		return sysRoleMapper.updateByPrimaryKeySelective(updateObj) > 0;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,
			rollbackFor = {RuntimeException.class, Exception.class})
	public Boolean editStatus(SysRole dto) {
		return sysRoleMapper.editStatus(dto) > 0;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,
			rollbackFor = {RuntimeException.class, Exception.class})
	public Boolean delete(Long id) {
		List<Long> ids = new ArrayList<>();
		ids.add(id);
		sysRoleMapper.deleteMenuByRoleId(ids);
		return sysRoleMapper.deleteByPrimaryKey(id) > 0;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,
			rollbackFor = {RuntimeException.class, Exception.class})
	public Boolean batchRemove(String ids) {
		String[] str = ids.split(",");
		List<Long> list = Arrays.stream(str).map(Long::valueOf).collect(Collectors.toList());
		list.remove(Constants.DEFULT_ROLE);
		sysRoleMapper.deleteMenuByRoleId(list);
		return sysRoleMapper.batchRemove(list) > 0;
	}


}