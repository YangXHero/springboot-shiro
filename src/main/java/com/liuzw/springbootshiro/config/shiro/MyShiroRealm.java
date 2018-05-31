package com.liuzw.springbootshiro.config.shiro;

import com.liuzw.springbootshiro.bean.SysMenu;
import com.liuzw.springbootshiro.bean.SysRole;
import com.liuzw.springbootshiro.bean.UserLoginInfo;
import com.liuzw.springbootshiro.model.SysUserModel;
import com.liuzw.springbootshiro.service.SysMenuService;
import com.liuzw.springbootshiro.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;  
import org.apache.shiro.authz.SimpleAuthorizationInfo;  
import org.apache.shiro.realm.AuthorizingRealm;  
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/** 
 * 自定义权限匹配和账号密码匹配
 *
 * @author liuzw
 */

@Slf4j
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService menuService;
  
    @Override  
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("------------权限配置-----> MyShiroRealm.doGetAuthorizationInfo()");

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        UserLoginInfo userInfo = (UserLoginInfo) principals.getPrimaryPrincipal();
        //添加用户角色
        authorizationInfo.addRoles(userInfo.getRoleList());
        //添加用户权限
        authorizationInfo.addStringPermissions(userInfo.getPermsList());
        return authorizationInfo;
    }  
  
    /**
     * 主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。
     */
    @Override  
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {  
        log.info("------身份认证 ------> MyShiroRealm.doGetAuthenticationInfo()");
        //获取用户的输入的账号.  
        String username = (String) authenticationToken.getPrincipal();
        log.info("------------> username：{}", username);
        //通过username从数据库中查找用户数据.
        SysUserModel userModel = sysUserService.queryByUserName(username);

        if ((userModel == null)) {
            throw new UnknownAccountException();
        }

        if (userModel.getStatus() == 0) {
            throw new DisabledAccountException();
        }

        List<Map<String, Object>> mapList = sysUserService.getRoleByUserId(userModel.getId());
        Set<String> set = mapList.stream().map(map -> MapUtils.getString(map, "roleId")).collect(Collectors.toSet());
        // 是否第一次登陆
        Boolean firstLoginFlag = userModel.getFirstLoginFlag() == 0;

        UserLoginInfo userInfo = UserLoginInfo.builder()
                .userName(userModel.getUserName()).name(userModel.getName())
                .id(userModel.getId()).firstLoginFlag(firstLoginFlag)
                .adminFlag(userModel.getAdminFlag())
                .roleList(set)
                .build();

        setUserMenuInfo(userModel.getId(), userInfo);

        return new SimpleAuthenticationInfo(userInfo, userModel.getPassword(), getName());
    }


    private void setUserMenuInfo(Long id, UserLoginInfo dto){

        List<SysMenu> menuList = menuService.getMenuListById(id);

        List<String> list = menuList.stream().map(SysMenu::getPerms).filter(StringUtils::isNotEmpty).collect(Collectors.toList());

        Set<String> perms = new HashSet<>(list.size());
        for (String val : list) {
            if (val.contains(",")){
                perms.addAll(Arrays.asList(val.split(",")));
            } else {
                perms.add(val);
            }
        }
        dto.setPermsList(perms);
        menuList = menuList.stream().filter(sysMenuResDto -> sysMenuResDto.getType() != 3).collect(Collectors.toList());
        menuList = formatMenuList(menuList);
        menuList.sort(Comparator.comparing(SysMenu::getOrderNum));
        dto.setMenuList(menuList);
    }


    private List<SysMenu> formatMenuList(List<SysMenu> rootMenu) {
        // 先找到所有的一级菜单
        List<SysMenu> menuList = rootMenu.stream().filter(sysMenuResDto -> sysMenuResDto.getParentId() == 0).collect(Collectors.toList());


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
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (menu.getParentId().equals(id) && menu.getType() != 3) {
                childList.add(menu);
            }
        }
        // 把子菜单的子菜单再循环一遍
        // 没有url子菜单还有子菜单
        for (SysMenu menu : childList) {
            // 递归
            menu.setChildren(getChild(menu.getId(), rootMenu));
        }
        // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

}  