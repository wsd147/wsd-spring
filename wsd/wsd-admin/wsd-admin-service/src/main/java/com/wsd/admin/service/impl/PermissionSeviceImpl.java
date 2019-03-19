package com.wsd.admin.service.impl;

import com.common.dao.BaseDao;
import com.common.model.Menu;
import com.common.model.ZtreeResult;
import com.common.tips.ErrorTip;
import com.common.tips.SuccessTip;
import com.common.tips.Tip;
import com.common.util.BuildMeun;
import com.wsd.admin.dao.PermissionMapper;
import com.wsd.admin.pojo.Permission;
import com.wsd.admin.service.PermissionService;
import com.wsd.admin.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

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

    @Override
    public List<ZtreeResult> selectPermissionTree(Integer roleId) {
        return permissionMapper.selectPermissionTree(roleId);
    }

    @Override
    public List<Menu<Permission>> listMenuTree(Integer id) {
        List<Menu<Permission>> menus = new ArrayList<Menu<Permission>>();

        List<Permission> permissions = permissionMapper.selectByUserId(id);

        for (Permission permission : permissions) {

            Menu<Permission> menu = new Menu<Permission>();

            menu.setId(permission.getPermissionId().toString());

            menu.setParentId(permission.getPid().toString());

            menu.setText(permission.getName());

            Map<String, Object> attributes = new HashMap<>(16);

            attributes.put("url", permission.getUri());

            attributes.put("icon", permission.getIcon());

            menu.setAttributes(attributes);

            menus.add(menu);

        }

        // 默认顶级菜单为０，根据数据库实际情况调整

        List<Menu<Permission>> list = BuildMeun.buildList(menus, "0");

        return list;
    }
}
