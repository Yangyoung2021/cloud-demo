package com.yang.test;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 将集合中的数据类型转换成目标数据类型并设置日期属性
 * @param <S> 集合中原始的数据类型
 * @param <T> 目标数据类型
 */
public class ListPropertiesCopy<S, T> {

    public List<T> copyProperties(List<S> source, Class<T> targetType) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ArrayList<T> list = new ArrayList<>();

        for (S o : source) {
            T target = targetType.newInstance();
            BeanUtils.copyProperties(o, target);
            Method method = targetType.getDeclaredMethod("setCreateTime", Date.class);
            method.invoke(target, new Date());
            list.add(target);
        }

        return list;
    }

}
