
package com.liuzw.springbootshiro.common;

import com.github.pagehelper.PageInfo;
import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;


/**
 * 封装分页信息
 *
 * @author liuzw
 */
@Data
public class Page<T> {

    /**
     * 结果集
     */
    private List<T> data;

    /**
     * 分页信息
     */
    private PageInfo<T> pageInfo;
    

    /**
     * 包装Page对象
     *
     * @param data  List<T>
     */
    public Page(List<T> data) {
       pageInfo = new com.github.pagehelper.PageInfo<>(data);
       pageInfo.setList(Collections.emptyList());
       this.data = data;
    }

    public Page(List<T> data, int navigatePages) {
        pageInfo = new com.github.pagehelper.PageInfo<>(data, navigatePages);
        pageInfo.setList(Collections.emptyList());
        this.data = data;
    }
}
