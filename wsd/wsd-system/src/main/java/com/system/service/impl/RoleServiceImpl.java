package com.system.service.impl;

import com.common.dao.BaseDao;
import com.common.service.BaseServiceImpl;
import com.system.dao.mapper.RoleMapper;
import com.system.dao.model.Role;
import com.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService{

    @Autowired
    RoleMapper roleMapper;

    @Override
    protected BaseDao<Role> getMapper() {
        return roleMapper;
    }

    @Override
    public Integer removeBatch(Long[] ids) throws Exception{
        int count = 0;
        for (long id:ids){
            int num = roleMapper.deleteByPrimaryKey(new Long(id).intValue());
            count += num;
        }
        return count;
    }
}
