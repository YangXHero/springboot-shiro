package com.liuzw.springbootshiro.service.impl;


import com.github.pagehelper.PageHelper;
import com.liuzw.springbootshiro.bean.SysPwd;
import com.liuzw.springbootshiro.bean.SysUser;
import com.liuzw.springbootshiro.bean.SysUserQuery;
import com.liuzw.springbootshiro.constants.Constants;
import com.liuzw.springbootshiro.mapper.SysUserMapper;
import com.liuzw.springbootshiro.model.SysUserModel;
import com.liuzw.springbootshiro.service.SysUserService;
import com.liuzw.springbootshiro.utils.CopyDataUtil;
import com.liuzw.springbootshiro.utils.EncryptMD5Util;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户service
 *
 * @author liuzw
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
		
		
	@Autowired
	private SysUserMapper sysUserMapper;

	@Override
	public List<SysUser> getList(SysUserQuery dto) {
		PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
		//用户列表数据
		return sysUserMapper.getList(dto);
	}

	@Override
	public SysUserModel getById(Long id) {
		return sysUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Map<String, Object>> getRoleByUserId(Long id) {
		return sysUserMapper.getRoleByUserId(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,
			rollbackFor = {RuntimeException.class, Exception.class})
	public Boolean insert(SysUser dto) {
		SysUserModel sysUserModel = CopyDataUtil.copyObject(dto, SysUserModel.class);
		sysUserModel.setId(null);
		sysUserModel.setPassword(EncryptMD5Util.getMD5(Constants.DEFULT_PASSWORD));
		//新增用户数据
		boolean flag = sysUserMapper.insertSelective(sysUserModel) > 0;
		//新增用户角色对应表数据
		if (dto.getRoleIds() != null && dto.getRoleIds().size() > 0) {
			sysUserMapper.insertRoleByUserId(sysUserModel.getId(), dto.getRoleIds());
		}
		return flag;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,
			rollbackFor = {RuntimeException.class, Exception.class})
	public Boolean update(SysUser dto) {
		SysUserModel updateObj = CopyDataUtil.copyObject(dto, SysUserModel.class);
		List<Long> ids = new ArrayList<>();
		ids.add(updateObj.getId());
		//删除用户角色对应表数据
		sysUserMapper.deleteRoleByUserId(ids);
		if (dto.getRoleIds() != null && dto.getRoleIds().size() > 0) {
			//新增用户角色对应表数据
			sysUserMapper.insertRoleByUserId(updateObj.getId(), dto.getRoleIds());
		}
		return sysUserMapper.updateByPrimaryKeySelective(updateObj) > 0;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,
			rollbackFor = {RuntimeException.class, Exception.class})
	public Boolean updatePwd(SysPwd dto) {
		sysUserMapper.updateLoginStatus(dto.getId());
		return sysUserMapper.updatePwd(dto) > 0;
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED,
			rollbackFor = {RuntimeException.class, Exception.class})
	public Boolean resetPwd(Long id) {
		return sysUserMapper.resetPwd(id, EncryptMD5Util.getMD5(Constants.DEFULT_PASSWORD)) > 0;
	}

	@Override
	public SysUserModel queryByUserName(String userName) {
        if (StringUtils.isEmpty(userName)) {
            return null;
        }
        SysUserModel qryObj = new SysUserModel();
        qryObj.setUserName(userName);
        return sysUserMapper.selectOne(qryObj);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,
			rollbackFor = {RuntimeException.class, Exception.class})
	public Boolean editStatus(SysUser dto) {
		return sysUserMapper.editStatus(dto) > 0;
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED,
			rollbackFor = {RuntimeException.class, Exception.class})
	public Boolean delete(Long id) {
		List<Long> ids = new ArrayList<>();
		ids.add(id);
		sysUserMapper.deleteRoleByUserId(ids);
		return sysUserMapper.deleteByPrimaryKey(id) > 0;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,
			rollbackFor = {RuntimeException.class, Exception.class})
	public Boolean batchRemove(String ids) {
		String[] str = ids.split(",");
		List<Long> list = Arrays.stream(str).map(Long::valueOf).collect(Collectors.toList());
		//默认超级管理员账号不删除
		list.remove(Constants.DEFULT_USER);
		sysUserMapper.deleteRoleByUserId(list);
		return sysUserMapper.batchRemove(list) > 0;
	}
}