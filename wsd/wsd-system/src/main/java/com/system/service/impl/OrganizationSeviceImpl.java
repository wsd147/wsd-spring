package com.system.service.impl;

import com.common.dao.BaseDao;
import com.common.service.BaseServiceImpl;
import com.system.dao.mapper.OrganizationMapper;
import com.system.dao.model.Organization;
import com.system.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrganizationSeviceImpl extends BaseServiceImpl<Organization> implements OrganizationService{

    @Autowired
    OrganizationMapper organizationMapper;

    @Override
    protected BaseDao<Organization> getMapper() {
        return organizationMapper;
    }
}
