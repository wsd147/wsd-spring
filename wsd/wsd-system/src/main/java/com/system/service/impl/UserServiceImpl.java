package com.system.service.impl;

import com.common.dao.BaseDao;
import com.common.service.BaseServiceImpl;
import com.system.dao.mapper.UserMapper;
import com.system.dao.model.User;
import com.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{

    @Autowired
    private  UserMapper userMapper;

    @Override
    protected BaseDao<User> getMapper() {
        return userMapper;
    }
}
