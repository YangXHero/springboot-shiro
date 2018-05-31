package com.liuzw.springbootshiro.mapper;


import com.liuzw.springbootshiro.bean.SysMenu;
import com.liuzw.springbootshiro.bean.SysMenuQuery;
import com.liuzw.springbootshiro.model.SysMenuModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * interface SysMenuMapper
 *
 * @author liuzw
 */

public interface SysMenuMapper extends Mapper<SysMenuModel>, MySqlMapper<SysMenuModel> {

    /**
     * 获取该用户所有菜单权限
     *
     * @param id     用户id
     * @return       List<SysMenu>
     */
    List<SysMenu> getMenuListById(@Param("id") Long id);

    /**
     * 返回菜单信息(不包含按钮)
     *
     * @param dto    菜单数据
     * @return       list<SysMenuModel>
     */
    List<SysMenuModel> getMenu(SysMenuQuery dto);


    /**
     * 获取菜单所有数据
     *
     * @return       list<SysMenuModel>
     */
    List<SysMenuModel> getMenuSelectList();

    /**
     * 返回按钮信息
     *
     * @param ids     菜单id
     * @param status  状态
     * @return       list<SysMenuModel>
     */
    List<SysMenuModel> getButton(@Param("status") Integer status, @Param("ids") List<Long> ids);

    /**
     * 判断菜单是否包含下级
     *
     * @param id     id
     * @return       Boolean
     */
    Integer checkMenuByParentId(Long id);

    /**
     * 更新菜单状态
     *
     * @param dto    菜单
     * @return       Boolean
     */
    Integer editStatus(SysMenu dto);


    /**
     * 新增菜单时，默认给超级管理员添加改菜单权限
     *
     * @param id    菜单id
     * @return      Integer
     */
    Integer insertRoleMenu(Long id);

    /**
     * 删除菜单时，将角色菜单中相应的数据删除
     *
     * @param id    菜单id
     * @return      Integer
     */
    Integer deleteRoleByMenuId(Long id);
}