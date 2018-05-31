package com.liuzw.springbootshiro.mapper;


import com.liuzw.springbootshiro.bean.SysDeptQuery;
import com.liuzw.springbootshiro.model.SysDeptModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * interface SysDeptMapper
 *
 * @author liuzw
 */

public interface SysDeptMapper extends Mapper<SysDeptModel>, MySqlMapper<SysDeptModel> {

    /**
     * 返回菜单分页列表信息
     *
     * @param dto    部门
     * @return       list<SysMenuModel>
     */
    List<SysDeptModel> getList(SysDeptQuery dto);


    /**
     * 批量删除部门
     *
     * @param ids     id
     * @return       Integer
     */
    Integer batchRemove(@Param("ids") List<Long> ids);

    /**
     * 根据id返回信息
     * @return       SysUserModel
     */
    List<SysDeptModel> getDeptSelectList();

}