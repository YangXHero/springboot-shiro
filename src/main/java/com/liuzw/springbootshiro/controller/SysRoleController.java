
package com.liuzw.springbootshiro.controller;


import com.liuzw.springbootshiro.bean.Id;
import com.liuzw.springbootshiro.bean.SysRole;
import com.liuzw.springbootshiro.bean.SysRoleQuery;
import com.liuzw.springbootshiro.constants.Constants;
import com.liuzw.springbootshiro.common.Page;
import com.liuzw.springbootshiro.common.ResultData;
import com.liuzw.springbootshiro.service.SysRoleService;
import com.liuzw.springbootshiro.utils.CopyDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

/**
 * @author liuzw
 * @version V1.0
 **/

@Api(description = "角色管理")
@Validated
@RestController
@RequestMapping
public class SysRoleController extends BaseController {

    @Autowired
    private SysRoleService sysRoleService;


    /**
     *  获取角色所有数据
     *
     * @param sysRole  SysRoleDto
     * @return     ResultData<PageInfo<SysRoleDto>>
     */
     @ApiOperation(value = "获取数据列表", notes = "获取数据列表")
     @PostMapping(value = "/role/list",  produces = MediaType.APPLICATION_JSON_VALUE)
     @RequiresPermissions("sys:role:list")
     public ResultData<Page<SysRole>> getList(@Validated @RequestBody SysRoleQuery sysRole) {
         return  ResultData.createSelectSuccessResult(convertPageInfo(sysRoleService.getList(sysRole), SysRole.class));
     }

    /**
     *  获取所有没有禁用的角色数据
     *
     * @return     ResultData<PageInfo<SysRoleDto>>
     */
    @ApiOperation(value = "获取数据列表", notes = "获取数据列表")
    @PostMapping(value = "/role/getRoleListByStatus",  produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("sys:role:list")
    public ResultData<Page<SysRole>> getRoleList() {
        return  ResultData.createSelectSuccessResult(convertPageInfo(sysRoleService.getRoleListByStatus(), SysRole.class));
    }


    /**
     *  获取角色信息
     *
     * @param id    主键id
     * @return     ResultData<SysRoleDto>
     */
     @ApiOperation(value = "查询指定数据", notes = "查询指定数据")
     @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "数据Id", paramType = "query", dataType = "Long")})
     @PostMapping(value = "/role/query",  produces = MediaType.APPLICATION_JSON_VALUE)
     @RequiresPermissions("sys:role:list")
     public ResultData<SysRole> query(Long id) {
        return ResultData.createSelectSuccessResult(CopyDataUtil.copyObject(sysRoleService.getById(id), SysRole.class));
     }


    /**
     *  查询角色所有菜单权限
     *
     * @param id    主键id
     * @return     ResultData<SysRoleDto>
     */
    @ApiOperation(value = "查询角色所有菜单权限", notes = "查询角色所有菜单权限")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "数据Id", paramType = "query", dataType = "Long")})
    @PostMapping(value = "/role/getMenuByRoleId",  produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("sys:role:list")
    public ResultData<List<Integer>> getMenuByRoleId(@RequestBody Id id) {
        return ResultData.createSelectSuccessResult(sysRoleService.getMenuByRoleId(id.getId()));
    }


    /**
     *  添加角色
     *
     * @param sysRole   SysRoleDto
     * @return      ResultData<SysRole>
     */
     @ApiOperation(value = "增加数据", notes = "增加数据")
     @PostMapping(value = "/role/add",  produces = MediaType.APPLICATION_JSON_VALUE)
     @RequiresPermissions("sys:role:insert")
     public ResultData<SysRole> insert(@Validated @RequestBody SysRole sysRole) {
         Boolean flag = sysRoleService.checkRoleNameIsExist(sysRole.getRoleName());
         if (flag) {
             return ResultData.createErrorResult(Constants.ROLE_ERROR);
         }
        return ResultData.createInsertResult(sysRoleService.insert(sysRole));
     }

    /**
     *  更新角色
     *
     * @param sysRole  SysRoleDto
     * @return     ResultData<SysRole>
     */
     @ApiOperation(value = "更新数据", notes = "更新数据")
     @PostMapping(value = "/role/edit",  produces = MediaType.APPLICATION_JSON_VALUE)
     @RequiresPermissions("sys:role:update")
     public ResultData<SysRole> update(@RequestBody SysRole sysRole) {
         if (Constants.DEFULT_ROLE.equals(sysRole.getId())) {
             return  ResultData.createErrorResult(Constants.DEFULT_ROLE_UPDATE_ERROR);
         }
         return ResultData.createUpdateResult(sysRoleService.update(sysRole));
     }


    /**
     *  更新角色状态
     *
     * @param sysRole  SysRole
     * @return     ResultData<SysRole>
     */
    @ApiOperation(value = "更新角色状态", notes = "更新角色状态")
    @PostMapping(value = "/role/editStatus",  produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("sys:role:update")
    public ResultData<SysRole> editStatus(@RequestBody SysRole sysRole) {
        if (Constants.DEFULT_ROLE.equals(sysRole.getId())) {
            return  ResultData.createErrorResult(Constants.DEFULT_ROLE_UPDATE_ERROR);
        }
        return ResultData.createUpdateResult(sysRoleService.editStatus(sysRole));
    }

     /**
      *  删除角色
      *
      * @param id   主键id
      * @return     ResultData<Void>
      */
      @ApiOperation(value = "删除数据", notes = "删除数据")
      @PostMapping(value = "/role/remove",  produces = MediaType.APPLICATION_JSON_VALUE)
      @RequiresPermissions("sys:role:delete")
      public ResultData<Void> delete(@RequestBody Id id) {
          if (id.getId().equals(Constants.DEFULT_ROLE)) {
              return  ResultData.createErrorResult(Constants.DEFULT_ROLE_DELETE_ERROR);
          }
         return ResultData.createDeleteResult(sysRoleService.delete(id.getId()));
      }

    /**
     *  批量删除角色
     *
     * @param id   主键
     * @return     ResultData<Void>
     */
    @ApiOperation(value = "批量删除角色", notes = "批量删除角色")
    @PostMapping(value = "/role/batchRemove",  produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("sys:role:delete")
    public ResultData<Void> batchRemove(@RequestBody Id id) {
        if (StringUtils.isEmpty(id.getIds())) {
            return ResultData.createErrorResult(Constants.ID_NOT_EMPTY);
        }
        return ResultData.createDeleteResult(sysRoleService.batchRemove(id.getIds()));
    }
}
