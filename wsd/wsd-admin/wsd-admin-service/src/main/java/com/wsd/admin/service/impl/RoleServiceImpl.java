package com.wsd.admin.service.impl;

import com.common.dao.BaseDao;
import com.common.tips.SuccessTip;
import com.common.tips.Tip;
import com.wsd.admin.dao.RoleMapper;
import com.wsd.admin.pojo.Role;
import com.wsd.admin.pojo.RolePermission;
import com.wsd.admin.service.RolePermissionService;
import com.wsd.admin.service.RoleService;
import com.wsd.admin.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService{

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    RolePermissionService rolePermissionService;

    @Override
    protected BaseDao<Role> getMapper() {
        return roleMapper;
    }

    @Override
    public Integer removeOne(Integer id) {
        //先删除关联
        Example example = new Example(RolePermission.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId",id);
        rolePermissionService.deleteByExample(example);
        return this.deleteByKey(id);
    }

    @Override
    public Integer removeBatch(Integer[] ids) throws Exception{
        int count = 0;
        for (Integer id:ids){
            int num = this.removeOne(id);
            count += num;
        }
        return count;
    }

    @Override
    public Tip saveAuth(Integer roleId, Integer[] ids) throws Exception {
        //删除旧授权添加新授权
        Example example = new Example(RolePermission.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId",roleId);
        rolePermissionService.deleteByExample(example);
        for(Integer id:ids){
            RolePermission rolePermission = new RolePermission();
            rolePermission.setPermissionId(id);
            rolePermission.setRoleId(roleId);
            rolePermissionService.insert(rolePermission);
        }
        Role role = new Role();
        role.setRoleId(roleId);
        role.setStatus(1);
        role.setUtime(new Date());
        roleMapper.updateByPrimaryKeySelective(role);
        return new SuccessTip();
    }
}
