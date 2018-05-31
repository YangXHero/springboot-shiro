package com.liuzw.springbootshiro.bean;

import lombok.Data;

import java.util.List;

/**
 * 主键Id
 *
 * @author liuzw
 * @date 2018/5/28 15:29
 **/

@Data
public class Id {

    /**
     * id
     */
    private Long id;

    /**
     * 逗号分隔的id 如; "1,2,3"
     */
    private String ids;

    /**
     * 集合id 前端传递数组id
     */
    private List<Long> idList;
}
