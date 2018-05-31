package com.liuzw.springbootshiro.mapper;


import com.liuzw.springbootshiro.bean.SysPwd;
import com.liuzw.springbootshiro.bean.SysUser;
import com.liuzw.springbootshiro.bean.SysUserQuery;
import com.liuzw.springbootshiro.model.SysUserModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Map;

/**
 * interface TSysUserMapper
 *
 * @author liuzw
 */

public interface SysUserMapper extends Mapper<SysUserModel>, MySqlMapper<SysUserModel> {


    /**
     * 返回用户分页列表信息
     *
     * @param bean    用户数据
     * @return       list<SysUserModel>
     */
    List<SysUser> getList(SysUserQuery bean);


    /**
     * 获取用户所有角色
     *
     * @param id   用户id
     * @return     List<Map<String, Object>>
     */
    List<Map<String, Object>> getRoleByUserId(@Param("userId") Long id);

    /**
     * 更新用户状态
     *
     * @param bean    用户
     * @return       Boolean
     */
    Integer editStatus(SysUser bean);

    /**
     * 修改密码
     *
     * @param bean  用户数据
     * @return     boolean
     */
    Integer updatePwd(SysPwd bean);

    /**
     * 重置密码
     *
     * @param id         用户数据
     * @param password   密码
     * @return           Integer
     */
    Integer resetPwd(@Param("id") Long id, @Param("password") String password);


    /**
     * 新增用户角色
     *
     * @param userId     用户id
     * @param roleList   角色id
     * @return           Integer
     */
    Integer insertRoleByUserId(@Param("userId") Long userId, @Param("roleList") List<Long> roleList);

    /**
     * 新增用户所在仓库
     *
     * @param userId     用户id
     * @param roleList   仓库id
     * @return           Integer
     */
    Integer insertStoreByUserId(@Param("userId") Long userId, @Param("storeList") List<Long> roleList);

    /**
     * 删除用户角色
     *
     * @param ids     用户id
     * @return        Integer
     */
    Integer deleteRoleByUserId(@Param("ids") List<Long> ids);


    /**
     * 删除用户仓库
     *
     * @param ids     用户id
     * @return        Integer
     */
    Integer deleteStoreByUserId(@Param("ids") List<Long> ids);

    /**
     * 批量删除用户
     *
     * @param ids     id
     * @return       Integer
     */
    Integer batchRemove(@Param("ids") List<Long> ids);


    /**
     * 修改登录状态
     *
     * @param id    主键Id
     */
    void updateLoginStatus(Long id);
}