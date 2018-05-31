package com.liuzw.springbootshiro.service;


import com.liuzw.springbootshiro.bean.SysDept;
import com.liuzw.springbootshiro.bean.SysDeptQuery;
import com.liuzw.springbootshiro.model.SysDeptModel;

import java.util.List;

/**
 * interface SysDeptservice
 *
 * @author liuzw
 */
public interface SysDeptService {


	/**
	 * 返回分页列表信息
	 *
	 * @param sysDept    数据
 	 * @return       list<SysDept>
     */
    List<SysDeptModel> getList(SysDeptQuery sysDept);

	/**
	 * 判断当前部门是否存在
	 *
	 * @param deptNum 部门编码
	 * @return Boolean
	 */

	Boolean checkDeptIsExist(String deptNum);

	/**
     * 根据id返回信息
     * @return       SysUserModel
     */
	List<SysDeptModel> getDeptSelectList();


    /**
     * 根据ID删除
     *
     * @param id     id
     * @return       Boolean
     */
    Boolean delete(Long id);

    /**
     *增加
     *
     * @param sysDept   数据
     * @return      Boolean
     */
    Boolean insert(SysDept sysDept);

    /**
	 *更新
	 *
	 * @param sysDept  数据
	 * @return     Boolean
	 */
    Boolean update(SysDept sysDept);

	/**
	 * 批量删除用户
	 *
	 * @param id     id
	 * @return       Boolean
	 */
	Boolean batchRemove(String id);

}