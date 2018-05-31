
package com.liuzw.springbootshiro.controller;


import com.liuzw.springbootshiro.bean.Id;
import com.liuzw.springbootshiro.bean.SysDept;
import com.liuzw.springbootshiro.bean.SysDeptQuery;
import com.liuzw.springbootshiro.constants.Constants;
import com.liuzw.springbootshiro.common.Page;
import com.liuzw.springbootshiro.common.ResultData;
import com.liuzw.springbootshiro.service.SysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author liuzw
 * @version V1.0
 **/
@RestController
@RequestMapping
@Api(description = "部门管理")
@Validated
public class SysDeptController extends BaseController {

    @Autowired
    private SysDeptService sysDeptService;


    /**
     *  获取部门所有数据
     *
     * @param dto  SysDeptDto
     * @return     ResultData<Page<SysDept>>
     */
     @ApiOperation(value = "获取部门数据列表", notes = "获取部门数据列表")
     @PostMapping(value = "/dept/list",  produces = MediaType.APPLICATION_JSON_VALUE)
     @RequiresPermissions("sys:dept:list")
     public ResultData<Page<SysDept>> getList(@Validated @RequestBody SysDeptQuery dto) {
         return  ResultData.createSelectSuccessResult(convertPageInfo(sysDeptService.getList(dto), SysDept.class));
     }


    /**
     *  用户获取部门下拉菜单
     *
     * @return     ResultData<Page<SysDept>>
     */
     @ApiOperation(value = "用户获取部门下拉菜单", notes = "用户获取部门下拉菜单")
     @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "数据Id", paramType = "query", dataType = "Long")})
     @GetMapping(value = "/dept/getDeptSelectList",  produces = MediaType.APPLICATION_JSON_VALUE)
     @RequiresPermissions("sys:dept:list")
     public ResultData<Page<SysDept>> getDeptSelectList() {
        return ResultData.createSelectSuccessResult(convertPageInfo(sysDeptService.getDeptSelectList(), SysDept.class));
     }


    /**
     *  增加部门数据
     *
     * @param dto   SysDeptDto
     * @return      ResultData<SysDept>
     */
     @ApiOperation(value = "增加部门数据", notes = "增加部门数据")
     @PostMapping(value = "/dept/add",  produces = MediaType.APPLICATION_JSON_VALUE)
     @RequiresPermissions("sys:dept:insert")
     public ResultData<SysDept> insert(@RequestBody SysDept dto) {
         Boolean flag = sysDeptService.checkDeptIsExist(dto.getDeptNum());
         if (flag) {
             return ResultData.createErrorResult("部门编号已存在");
         }
        return ResultData.createInsertResult(sysDeptService.insert(dto));
     }

    /**
     *  更新部门数据
     *
     * @param dto  SysDeptDto
     * @return     ResultData<SysDept>
     */
     @ApiOperation(value = "更新部门数据", notes = "更新部门数据")
     @PostMapping(value = "/dept/edit",  produces = MediaType.APPLICATION_JSON_VALUE)
     @RequiresPermissions("sys:dept:update")
     public ResultData<SysDept> update(@Validated @RequestBody SysDept dto) {
         return ResultData.createUpdateResult(sysDeptService.update(dto));
     }


     /**
      *  删除部门
      *
      * @param id   主键id
      * @return     ResultData<Void>
      */
      @ApiOperation(value = "删除部门", notes = "删除部门")
      @PostMapping(value = "/dept/remove",  produces = MediaType.APPLICATION_JSON_VALUE)
      @RequiresPermissions("sys:dept:delete")
      public ResultData<Void> delete(@RequestBody Id id) {
         return ResultData.createDeleteResult(sysDeptService.delete(id.getId()));
      }

    /**
     *  批量删除部门
     *
     * @param id      SysDept
     * @return        ResultData<Void>
     */
    @ApiOperation(value = "批量删除部门", notes = "批量删除部门")
    @PostMapping(value = "/dept/batchRemove",  produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("sys:dept:delete")
    public ResultData<Void> batchRemove(@RequestBody Id id) {
        if (StringUtils.isEmpty(id.getIds())) {
            return ResultData.createErrorResult(Constants.ID_NOT_EMPTY);
        }
        return ResultData.createDeleteResult(sysDeptService.batchRemove(id.getIds()));
    }

}
