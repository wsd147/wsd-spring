package com.common.service;

import tk.mybatis.mapper.entity.Example;

import java.util.List;

public interface BaseService<T> {

    List<T> selectAll();

    List<T> selectByExample(Example example);

    List<T> select(T t);

    Integer selectCount(T t);

    Integer selectCountByExample(Example example);

    T selectByKey(Object o);

    T selectOne(T t);

    T selectOne(Example example);

    Integer insert(T t);

    Integer updateByExample(T t,Example example);

    Integer updateByKey(T t);

    Integer updateByExampleSelective(T t,Example example);

    Integer updateByKeySelective(T t);

    Integer deleteByKey(Object o);

    Integer delete(T t);

    Integer deleteByExample(Object o);
}
