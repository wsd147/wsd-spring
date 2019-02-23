package com.system.service.impl;

import com.common.dao.BaseDao;
import com.common.service.BaseServiceImpl;
import com.system.dao.mapper.UserPermissionMapper;
import com.system.dao.model.UserPermission;
import com.system.service.UserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserPermissionServiceImpl extends BaseServiceImpl<UserPermission> implements UserPermissionService{

    @Autowired
    UserPermissionMapper userPermissionMapper;

    @Override
    protected BaseDao<UserPermission> getMapper() {
        return userPermissionMapper;
    }
}
