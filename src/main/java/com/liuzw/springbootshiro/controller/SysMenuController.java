
package com.liuzw.springbootshiro.controller;


import com.liuzw.springbootshiro.bean.Id;
import com.liuzw.springbootshiro.bean.SysMenu;
import com.liuzw.springbootshiro.bean.SysMenuQuery;
import com.liuzw.springbootshiro.constants.Constants;
import com.liuzw.springbootshiro.common.Page;
import com.liuzw.springbootshiro.common.ResultData;
import com.liuzw.springbootshiro.service.SysMenuService;
import com.liuzw.springbootshiro.utils.CopyDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author liuzw
 * @version V1.0
 **/
@RestController
@RequestMapping
@Api(description = "菜单管理")
@Validated
public class SysMenuController extends BaseController {

    @Autowired
    private SysMenuService sysMenuService;


    /**
     *  获取菜单所有数据
     *
     * @param dto  SysMenuDto
     * @return     ResultData<PageInfo<SysMenu>>
     */
     @ApiOperation(value = "获取数据列表", notes = "获取数据列表")
     @PostMapping(value = "/menu/list",  produces = MediaType.APPLICATION_JSON_VALUE)
     @RequiresPermissions("sys:menu:list")
     public ResultData<Page<SysMenu>> getList(@RequestBody SysMenuQuery dto) {
         List<SysMenu> list = CopyDataUtil.copyList(sysMenuService.getList(dto), SysMenu.class);
         list = formatMenuList(list);
         list.sort(Comparator.comparing(SysMenu::getOrderNum));
         return  ResultData.createSelectSuccessResult(createPageInfo(list));
     }


    /**
     *  获取角色获取下拉菜单
     *
     * @return     ResultData<PageInfo<SysMenu>>
     */
    @ApiOperation(value = "获取角色获取下拉菜单", notes = "获取角色获取下拉菜单")
    @PostMapping(value = "/menu/getMenuSelectList",  produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("sys:menu:list")
    public ResultData<Page<SysMenu>> getMenuSelectList() {
        List<SysMenu> list = CopyDataUtil.copyList(sysMenuService.getMenuSelectList(), SysMenu.class);
        return  ResultData.createSelectSuccessResult(createPageInfo(formatMenuList(list)));
    }


    /**
     *  获取菜单不包含button
     *
     * @return     ResultData<PageInfo<SysMenu>>
     */
    @ApiOperation(value = "获取角色获取下拉菜单", notes = "获取角色获取下拉菜单")
    @PostMapping(value = "/menu/getMenuNoButton",  produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("sys:menu:list")
    public ResultData<List<Map<String, Object>>> getMenuNoButton() {
        List<Map<String, Object>> mapList = sysMenuService.getMenuNoButton();
        return  ResultData.createSelectSuccessResult(mapList);
    }


    /**
     *  获取菜单信息
     *
     * @param id   主键id
     * @return     ResultData<SysMenu>
     */
     @ApiOperation(value = "查询指定数据", notes = "查询指定数据")
     @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "数据Id", paramType = "query", dataType = "Long")})
     @GetMapping(value = "/menu/query",  produces = MediaType.APPLICATION_JSON_VALUE)
     @RequiresPermissions("sys:menu:list")
     public ResultData<SysMenu> query(@RequestBody Id id) {
        return ResultData.createSelectSuccessResult(CopyDataUtil.copyObject(sysMenuService.getById(id.getId()), SysMenu.class));
     }


    /**
     *  添加菜单
     *
     * @param dto   SysMenuDto
     * @return      ResultData<SysMenuDto>
     */
     @ApiOperation(value = "增加数据", notes = "增加数据")
     @PostMapping(value = "/menu/add",  produces = MediaType.APPLICATION_JSON_VALUE)
     @RequiresPermissions("sys:menu:insert")
     public ResultData<SysMenu> insert(@Validated @RequestBody SysMenu dto) {
        return ResultData.createInsertResult(sysMenuService.insert(dto));
     }

    /**
     *  更新菜单
     *
     * @param dto  SysMenuDto
     * @return     ResultData<String>
     */
     @ApiOperation(value = "更新数据", notes = "更新数据")
     @PostMapping(value = "/menu/edit",  produces = MediaType.APPLICATION_JSON_VALUE)
     @RequiresPermissions("sys:menu:update")
     public ResultData<SysMenu> update(@Validated @RequestBody SysMenu dto) {
         return ResultData.createUpdateResult(sysMenuService.update(dto));
     }

    /**
     *  更新菜单状态
     *
     * @param dto  SysMenu
     * @return     ResultData<SysMenu>
     */
    @ApiOperation(value = "更新菜单状态", notes = "更新菜单状态")
    @PostMapping(value = "/menu/editStatus",  produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("sys:menu:update")
    public ResultData<SysMenu> editStatus(@Validated @RequestBody SysMenu dto) {
        return ResultData.createUpdateResult(sysMenuService.editStatus(dto));
    }

     /**
      *  删除菜单
      *
      * @param id   主键id
      * @return     ResultData<String>
      */
      @ApiOperation(value = "删除数据", notes = "删除数据")
      @PostMapping(value = "/menu/remove",  produces = MediaType.APPLICATION_JSON_VALUE)
      @RequiresPermissions("sys:menu:delete")
      public ResultData<Void> delete(@RequestBody Id id) {
          //判断要删除的菜单是否包含下级
          Boolean flag = sysMenuService.checkMenuByParentId(id.getId());
          if (flag) {
              return ResultData.createErrorResult(Constants.MENU_ERROR);
          }
          return ResultData.createDeleteResult(sysMenuService.delete(id.getId()));
      }


      private List<SysMenu> formatMenuList(List<SysMenu> rootMenu) {
          // 先找到所有的一级菜单
          List<SysMenu> menuList = rootMenu.stream().filter(menu -> menu.getParentId() == 0).collect(Collectors.toList());

          if (menuList.size() == 0) {
              menuList = rootMenu.stream().filter(menu -> menu.getType() == 2).collect(Collectors.toList());
          }

          // 为一级菜单设置子菜单，getChild是递归调用的
          for (SysMenu menu : menuList) {
              menu.setChildren(getChild(menu.getId(), rootMenu));
          }
          return menuList;
      }

    /**
     * 递归查找子菜单
     *
     * @param id        当前菜单id
     * @param rootMenu  要查找的列表
     * @return          List<SysMenu>
     */
    private List<SysMenu> getChild(Long id, List<SysMenu> rootMenu) {
        // 子菜单
        List<SysMenu> childList = new ArrayList<>();
        for (SysMenu menu : rootMenu) {
            menu.setMenuType(getType(menu.getType()));
            menu.setMenuStatus(getStatus(menu.getStatus()));
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (menu.getParentId().equals(id)) {
                childList.add(menu);
            }
        }
        // 把子菜单的子菜单再循环一遍
        // 没有url子菜单还有子菜单
        for (SysMenu menu : childList) {
            // 递归
            menu.setMenuType(getType(menu.getType()));
            menu.setMenuStatus(getStatus(menu.getStatus()));
            menu.setChildren(getChild(menu.getId(), rootMenu));
        }
        // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }


    private String getType(Integer type) {
       return 1 == type ? "目录" : 2 == type ? "菜单" : "按钮";
    }

    private String getStatus(Integer status) {
        return 1 == status ? "启用" : "停用";
    }
}
