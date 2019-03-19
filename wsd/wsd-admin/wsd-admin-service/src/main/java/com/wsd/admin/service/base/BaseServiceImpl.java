package com.wsd.admin.service.base;

import com.common.dao.BaseDao;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

public abstract class BaseServiceImpl<T>  implements BaseService<T>{

    protected abstract BaseDao<T> getMapper();

    @Override
    public List<T> selectAll() {
        return getMapper().selectAll();
    }

    @Override
    public List<T> selectByExample(Example example) {
        return getMapper().selectByExample(example);
    }

    @Override
    public List<T> select(T t) {
        return getMapper().select(t);
    }

    @Override
    public Integer selectCount(T t) {
        return getMapper().selectCount(t);
    }

    @Override
    public Integer selectCountByExample(Example example) {
        return getMapper().selectCountByExample(example);
    }

    @Override
    public T selectByKey(Object o) {
        return getMapper().selectByPrimaryKey(o);
    }

    @Override
    public T selectOne(T t) {
        return getMapper().selectOne(t);
    }

    @Override
    public T selectOne(Example example) {
        return getMapper().selectOneByExample(example);
    }

    @Override
    public Integer insert(T t) {
        return getMapper().insert(t);
    }

    @Override
    public Integer updateByExample(T t,Example example) {
        return getMapper().updateByExample(t,example);
    }

    @Override
    public Integer updateByKey(T t) {
        return getMapper().updateByPrimaryKey(t);
    }

    @Override
    public Integer updateByExampleSelective(T t, Example example) {
        return getMapper().updateByExampleSelective(t,example);
    }

    @Override
    public Integer updateByKeySelective(T t) {
        return getMapper().updateByPrimaryKeySelective(t);
    }

    @Override
    public Integer deleteByKey(Object o) {
        return getMapper().deleteByPrimaryKey(o);
    }

    @Override
    public Integer delete(T t) {
        return getMapper().delete(t);
    }

    @Override
    public Integer deleteByExample(Object o) {
        return getMapper().deleteByExample(o);
    }
}
