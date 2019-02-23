package com.system.service.impl;

import com.common.dao.BaseDao;
import com.common.service.BaseServiceImpl;
import com.system.dao.mapper.PermissionMapper;
import com.system.dao.model.Permission;
import com.system.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PermissionSeviceImpl extends BaseServiceImpl<Permission> implements PermissionService{

    @Autowired
    PermissionMapper permissionMapper;

    @Override
    protected BaseDao<Permission> getMapper() {
        return permissionMapper;
    }
}
