package com.system.service.impl;

import com.common.dao.BaseDao;
import com.common.service.BaseServiceImpl;
import com.common.tips.ErrorTip;
import com.common.tips.SuccessTip;
import com.common.tips.Tip;
import com.system.dao.mapper.UserMapper;
import com.system.dao.model.User;
import com.system.dao.model.UserOrganization;
import com.system.dao.model.UserRole;
import com.system.service.UserOrganizationService;
import com.system.service.UserRoleService;
import com.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{

    @Autowired
    private  UserMapper userMapper;

    @Autowired
    private UserOrganizationService userOrganizationService;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    protected BaseDao<User> getMapper() {
        return userMapper;
    }

    @Override
    public List<User> selectUserByOrgnId(Integer orgnId) throws Exception {
        return userMapper.selectUserByOrgnId(orgnId);
    }

    @Override
    public Tip insertOrUpdate(User user, Integer organizaId, Integer[] roleIds) {
        if(user.getUserId()==null){
            Example example = new Example(User.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("username",user.getUsername());
            List<User> roles = this.selectByExample(example);
            if(roles.size()>0){
                return new ErrorTip(500,"添加失败！该用户已经存在");
            }else{
                user.setCtime(new Date());
                user.setLocked(1);
                this.insert(user);
                //设置用户组织关联
                UserOrganization userOrganization = new UserOrganization();
                userOrganization.setUserId(user.getUserId());
                userOrganization.setOrganizationId(organizaId);
                userOrganizationService.insert(userOrganization);
                //设置用户角色关系
                for(Integer roleId:roleIds){
                    UserRole userRole = new UserRole();
                    userRole.setUserId(user.getUserId());
                    userRole.setRoleId(roleId);
                    userRoleService.insert(userRole);
                }
                return new SuccessTip();
            }
        }else{
            if(this.selectByKey(user.getUserId())==null){
                return  new ErrorTip(500,"修改失败，该用户不存在，请刷新");
            }else{
                user.setUtime(new Date());
                this.updateByKeySelective(user);
                return  new SuccessTip();
            }
        }
    }
}
