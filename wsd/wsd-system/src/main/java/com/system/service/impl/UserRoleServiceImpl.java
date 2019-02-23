package com.system.service.impl;

import com.common.dao.BaseDao;
import com.common.service.BaseServiceImpl;
import com.system.dao.mapper.UserRoleMapper;
import com.system.dao.model.UserRole;
import com.system.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole> implements UserRoleService{

    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    protected BaseDao<UserRole> getMapper() {
        return userRoleMapper;
    }
}
