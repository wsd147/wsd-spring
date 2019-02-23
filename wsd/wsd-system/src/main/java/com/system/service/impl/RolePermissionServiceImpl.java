package com.system.service.impl;

import com.common.dao.BaseDao;
import com.common.service.BaseServiceImpl;
import com.system.dao.mapper.RolePermissionMapper;
import com.system.dao.model.RolePermission;
import com.system.service.RolePermissionService;
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
