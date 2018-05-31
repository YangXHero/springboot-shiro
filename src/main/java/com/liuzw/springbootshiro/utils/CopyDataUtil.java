package com.liuzw.springbootshiro.utils;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 对象复制
 *
 * @author liuzw
 */
public class CopyDataUtil {

    public static <T, V> V copyObject(T source, Class<V> clazz) {
        if (source == null) {
            return null;
        }
        V newObj = null;
        try {
            newObj = clazz.newInstance();
            org.springframework.beans.BeanUtils.copyProperties(source, newObj);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return newObj;
    }

    public static <T, V> List<V> copyList(List<T> list, Class<V> clazz) {
        List<V> data = Lists.newArrayList();
        if (list != null) {
            try {
                for (T obj : list) {
                    V dto = clazz.newInstance();
                    org.springframework.beans.BeanUtils.copyProperties(obj, dto);
                    data.add(dto);
                }
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
            return data;
        } else {
            return null;
        }
    }

}
