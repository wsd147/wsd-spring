package com.wsd.admin.service.impl;

import com.common.dao.BaseDao;
import com.wsd.admin.dao.UserOrganizationMapper;
import com.wsd.admin.pojo.UserOrganization;
import com.wsd.admin.service.UserOrganizationService;
import com.wsd.admin.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserOrganizationServiceImpl extends BaseServiceImpl<UserOrganization> implements UserOrganizationService {

    @Autowired
    UserOrganizationMapper userOrganizationMapper;

    @Override
    protected BaseDao<UserOrganization> getMapper() {
        return userOrganizationMapper;
    }
}
