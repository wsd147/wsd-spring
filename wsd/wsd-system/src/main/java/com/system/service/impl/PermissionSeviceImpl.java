package com.system.service.impl;

import com.common.dao.BaseDao;
import com.common.service.BaseServiceImpl;
import com.common.tips.ErrorTip;
import com.common.tips.SuccessTip;
import com.common.tips.Tip;
import com.system.dao.mapper.PermissionMapper;
import com.system.dao.model.Permission;
import com.system.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PermissionSeviceImpl extends BaseServiceImpl<Permission> implements PermissionService{

    @Autowired
    PermissionMapper permissionMapper;

    @Override
    protected BaseDao<Permission> getMapper() {
        return permissionMapper;
    }

    @Override
    public Tip enableOrDisable(Integer id, String type) {
        Permission permission = this.selectByKey(id);
        if(permission==null){
            return  new ErrorTip(500,"该权限不存在，请刷新后再操作");
        }else{
            if("enable".equals(type)){
                if(permission.getStatus() == 1){
                    return new ErrorTip(500,"该权限已经是启用状态");
                }
                Example example = new Example(Permission.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("permissionId",permission.getPid());
                Permission parentPermission = this.selectOne(example);
                if(parentPermission!=null&&parentPermission.getStatus()==0){
                    return new ErrorTip(500,"该组织的父权限为禁用状态，请先启用父权限");
                }
                permission.setStatus(1);
                permission.setUtime(new Date());
                this.updateByKeySelective(permission);
                return new SuccessTip();
            }else if("disable".equals(type)){
                if(permission.getStatus() == 0){
                    return new ErrorTip(500,"该权限已经是禁用状态");
                }else {
                    Example example = new Example(Permission.class);
                    Example.Criteria criteria = example.createCriteria();
                    criteria.andEqualTo("pid",permission.getPermissionId());
                    List<Permission> permissions = this.selectByExample(example);
                    permission.setStatus(0);
                    permission.setUtime(new Date());
                    this.updateByKeySelective(permission);
                    //禁用该组织下的所有子组织
                    for(Permission permission1:permissions){
                        permission1.setStatus(0);
                        permission1.setUtime(new Date());
                        this.updateByKeySelective(permission1);
                    }
                    return  new SuccessTip();
                }
            }
        }
        return  null;
    }
}
