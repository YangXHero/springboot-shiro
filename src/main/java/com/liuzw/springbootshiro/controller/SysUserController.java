package com.liuzw.springbootshiro.controller;

import com.liuzw.springbootshiro.bean.*;
import com.liuzw.springbootshiro.common.*;
import com.liuzw.springbootshiro.constants.Constants;
import com.liuzw.springbootshiro.constants.ResultDataConstants;
import com.liuzw.springbootshiro.exception.UnauthorizedException;
import com.liuzw.springbootshiro.model.SysUserModel;
import com.liuzw.springbootshiro.service.SysUserService;
import com.liuzw.springbootshiro.utils.CopyDataUtil;
import com.liuzw.springbootshiro.utils.EncryptMD5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户管理
 *
 * @author liuzw
 * @version V1.0
 * @date 2018/4/19 10:07
 **/
@RestController
@RequestMapping
@Api(description = "用户管理")
@Validated
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;


    /**
     *  获取所有用户数据
     *
     * @param bean   SysUserQuery
     * @return       ResultData<PageInfo<SysUser>>
     */
    @ApiOperation(value = "获取数据列表", notes = "获取数据列表")
    @PostMapping(value = "/user/list",  produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("sys:user:list")
    public ResultData<Page<SysUser>> getList(@RequestBody SysUserQuery bean) {
        return  ResultData.createSelectSuccessResult(createPageInfo(sysUserService.getList(bean)));
    }

    /**
     *  获取用户信息
     *
     * @param id   user 主键id
     * @return     ResultData<SysUserResDto>
     */
    @ApiOperation(value = "查询指定数据", notes = "查询指定数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "数据Id", paramType = "query", dataType = "Long")})
    @PostMapping(value = "/user/query",  produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("sys:user:list")
    public ResultData<SysUser> selectOne(@Validated @RequestBody Id id) {
        return ResultData.createSelectSuccessResult(CopyDataUtil.copyObject(sysUserService.getById(id.getId()), SysUser.class));
    }



    /**
     *  查询角色所有菜单权限
     *
     * @param id    主键id
     * @return     ResultData<SysRoleDto>
     */
    @ApiOperation(value = "查询角色所有菜单权限", notes = "查询角色所有菜单权限")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "数据Id", paramType = "query", dataType = "Long")})
    @PostMapping(value = "/user/getRoleByUserId",  produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("sys:user:list")
    public ResultData<List<Integer>> getRoleByUserId(@Validated @RequestBody Id id) {
        List<Map<String, Object>> mapList = sysUserService.getRoleByUserId(id.getId());
        List<Integer> list = mapList.stream().map(map -> MapUtils.getInteger(map, "roleId")).collect(Collectors.toList());
        return ResultData.createSelectSuccessResult(list);
    }


    /**
     *  添加用户
     *
     * @param bean   SysUserDto
     * @return      ResultData<SysUserReqDto>
     */
    @ApiOperation(value = "增加数据", notes = "增加数据")
    @PostMapping(value = "/user/add",  produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("sys:user:insert")
    public ResultData<SysUser> insert(@Validated @RequestBody SysUser bean) {
        SysUserModel userModel = sysUserService.queryByUserName(bean.getUserName());
        if (userModel != null) {
            return ResultData.createInsertResult(false, ResultDataConstants.MSG_INSERT_ERROR);
        }
        return ResultData.createInsertResult(sysUserService.insert(bean));
    }

    /**
     *  更新用户
     *
     * @param bean  用户数据
     * @return     ResultData<SysUser>
     */
    @ApiOperation(value = "更新数据", notes = "更新数据")
    @PostMapping(value = "/user/edit",  produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("sys:user:update")
    public ResultData<SysUser> update(@Validated @RequestBody SysUser bean) {
        return ResultData.createUpdateResult(sysUserService.update(bean));
    }

    /**
     *  修改密码
     *
     * @param bean  SysDeptDto
     * @return     ResultData<SysUser>
     */
    @ApiOperation(value = "修改密码", notes = "修改密码")
    @PostMapping(value = "/user/updatePwd",  produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("sys:user:updatePwd")
    public ResultData<SysPwd> updatePwd(@Validated @RequestBody SysPwd bean) {

        UserLoginInfo userLoginDto = new UserLoginInfo();
        if (userLoginDto == null) {
            throw new UnauthorizedException();
        }

        SysUserModel userModel = sysUserService.queryByUserName(userLoginDto.getUserName());

        if ((userModel == null) || (!(bean.getPassword().equals(userModel.getPassword())))) {
            return  ResultData.createErrorResult(Constants.USERNAME_PASSWORD_ERROR);
        }
        String password = EncryptMD5Util.getMD5(bean.getNewPassword());
        if (!password.equals(bean.getNewPasswordOnce())) {
            return ResultData.createErrorResult(Constants.PASSWORD_ERROR);
        }
        bean.setId(userLoginDto.getId());
        bean.setNewPassword(password);
        return ResultData.createUpdateResult(sysUserService.updatePwd(bean));
    }


    /**
     *  重置密码
     *
     * @param id   Id
     * @return     ResultData<SysUser>
     */
    @ApiOperation(value = "重置密码", notes = "重置密码")
    @PostMapping(value = "/user/resetPwd",  produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("sys:user:update")
    public ResultData<Void> resetPwd(@RequestBody Id id) {
//        UserLoginInfo bean = tokenService.getCurrentTokenInf();
//        if (bean == null) {
//            throw new UnauthorizedException();
//        }
//        if (Constants.DEFULT_USER.equals(id.getId()) && !Constants.DEFULT_USER.equals(bean.getId())) {
//            return  ResultData.createErrorResult(Constants.USER_NOT_PERMS_ERROR);
//        }
//        return ResultData.createUpdateResult(sysUserService.resetPwd(id.getId()));
       return ResultData.createAuthErrorResult();
    }

    /**
     *  更新用户状态
     *
     * @param bean  SysDeptDto
     * @return     ResultData<SysUser>
     */
    @ApiOperation(value = "更新用户状态", notes = "更新用户状态")
    @PostMapping(value = "/user/editStatus",  produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("sys:user:update")
    public ResultData<SysUser> editStatus(@Validated @RequestBody SysUser bean) {
        return ResultData.createUpdateResult(sysUserService.editStatus(bean));
    }

    /**
     *  删除用户
     *
     * @param id   user 主键id
     * @return        ResultData<Void>
     */
    @ApiOperation(value = "删除用户数据", notes = "删除用户数据")
    @PostMapping(value = "/user/remove",  produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("sys:user:delete")
    public ResultData<Void> delete(@RequestBody Id id) {
        if (id.getId().equals(Constants.DEFULT_USER)) {
            return  ResultData.createErrorResult(Constants.DEFULT_USER_ERROR);
        }
        return ResultData.createDeleteResult(sysUserService.delete(id.getId()));
    }

    /**
     *  批量删除用户
     *
     * @param bean      SysUser
     * @return        ResultData<Void>
     */
    @ApiOperation(value = "批量删除用户", notes = "批量删除用户")
    @PostMapping(value = "/user/batchRemove",  produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("sys:user:delete")
    public ResultData<Void> batchRemove(@RequestBody Id bean) {
        if (StringUtils.isEmpty(bean.getIds())) {
            return ResultData.createErrorResult(Constants.ID_NOT_EMPTY);
        }
        return ResultData.createDeleteResult(sysUserService.batchRemove(bean.getIds()));
    }

}
