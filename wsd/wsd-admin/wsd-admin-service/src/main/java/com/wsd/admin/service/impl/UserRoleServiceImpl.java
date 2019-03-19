package com.wsd.admin.service.impl;

import com.common.dao.BaseDao;
import com.wsd.admin.dao.UserRoleMapper;
import com.wsd.admin.pojo.UserRole;
import com.wsd.admin.service.UserRoleService;
import com.wsd.admin.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole> implements UserRoleService {

    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    protected BaseDao<UserRole> getMapper() {
        return userRoleMapper;
    }
}
