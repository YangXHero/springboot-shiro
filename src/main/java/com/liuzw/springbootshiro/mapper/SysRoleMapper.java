package com.liuzw.springbootshiro.mapper;


import com.liuzw.springbootshiro.bean.SysRole;
import com.liuzw.springbootshiro.bean.SysRoleQuery;
import com.liuzw.springbootshiro.model.SysRoleModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * interface SysRoleMapper
 *
 * @author liuzw
 */

public interface SysRoleMapper extends Mapper<SysRoleModel>, MySqlMapper<SysRoleModel> {

    /**
     * 返回分页列表信息
     *
     * @param sysRole    数据
     * @return       list<SysRole>
     */
    List<SysRoleModel> getList(SysRoleQuery sysRole);

    /**
     * 获取角色所有菜单权限
     *
     * @param id   角色id
     * @return     List<String>
     */
    List<Integer> getMenuByRoleId(@Param("roleId") Long id);

    /**
     * 删除角色所有菜单权限
     *
     * @param id   SysRole
     * @return     List<String>
     */
    Integer deleteMenuByRoleId(@Param("roleId") List<Long> id);



    /**
     * 新增角色所有菜单权限
     *
     * @param sysRole   SysRole
     * @return     List<String>
     */
    Integer insertMenuByRoleId(SysRole sysRole);

    /**
     * 更新角色状态
     *
     * @param sysRole    角色
     * @return       Boolean
     */
    Integer editStatus(SysRole sysRole);

    /**
     * 所有没有禁用的角色
     *
     * @return       list<SysRole>
     */
    List<SysRoleModel> getRoleListByStatus();


    /**
     * 批量删除角色
     *
     * @param ids     id
     * @return       Boolean
     */
    Integer batchRemove(@Param("ids") List<Long> ids);

}