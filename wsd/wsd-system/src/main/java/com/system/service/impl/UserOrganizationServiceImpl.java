package com.system.service.impl;

import com.common.dao.BaseDao;
import com.common.service.BaseServiceImpl;
import com.system.dao.mapper.UserOrganizationMapper;
import com.system.dao.model.UserOrganization;
import com.system.service.UserOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserOrganizationServiceImpl extends BaseServiceImpl<UserOrganization> implements UserOrganizationService{

    @Autowired
    UserOrganizationMapper userOrganizationMapper;

    @Override
    protected BaseDao<UserOrganization> getMapper() {
        return userOrganizationMapper;
    }
}
