package com.wsd.admin.service.impl;

import com.common.dao.BaseDao;
import com.wsd.admin.dao.UserPermissionMapper;
import com.wsd.admin.pojo.UserPermission;
import com.wsd.admin.service.UserPermissionService;
import com.wsd.admin.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserPermissionServiceImpl extends BaseServiceImpl<UserPermission> implements UserPermissionService {

    @Autowired
    UserPermissionMapper userPermissionMapper;

    @Override
    protected BaseDao<UserPermission> getMapper() {
        return userPermissionMapper;
    }
}
