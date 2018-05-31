
package com.liuzw.springbootshiro.controller;



import com.liuzw.springbootshiro.common.Page;
import com.liuzw.springbootshiro.utils.CopyDataUtil;

import java.util.List;

/**
 * @author ben
 **/
public class BaseController {

    protected <T> Page<T> createPageInfo(List<T> list) {
        return new Page<>(list);
    }

    protected <T, V> Page<V> convertPageInfo(List<T> list, Class<V> clazz) {
        return copyPageList(list, clazz);
    }

    private static <T, V> Page<V> copyPageList(List<T> list, Class<V> clazz) {
        if (list == null) {
            return null;
        } else {
            return new Page<>(CopyDataUtil.copyList(list, clazz));
        }
    }

}
