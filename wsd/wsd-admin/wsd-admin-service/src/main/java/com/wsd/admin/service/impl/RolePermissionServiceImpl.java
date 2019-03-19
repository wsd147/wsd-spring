package com.wsd.admin.service.impl;

import com.common.dao.BaseDao;
import com.wsd.admin.dao.RolePermissionMapper;
import com.wsd.admin.pojo.RolePermission;
import com.wsd.admin.service.RolePermissionService;
import com.wsd.admin.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RolePermissionServiceImpl extends BaseServiceImpl<RolePermission> implements RolePermissionService {

    @Autowired
    RolePermissionMapper rolePermissionMapper;

    @Override
    protected BaseDao<RolePermission> getMapper() {
        return rolePermissionMapper;
    }
}
