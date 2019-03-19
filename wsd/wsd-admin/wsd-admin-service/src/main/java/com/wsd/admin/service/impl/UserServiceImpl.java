package com.wsd.admin.service.impl;

import com.common.dao.BaseDao;
import com.common.tips.ErrorTip;
import com.common.tips.SuccessTip;
import com.common.tips.Tip;
import com.wsd.admin.dao.UserMapper;
import com.wsd.admin.pojo.User;
import com.wsd.admin.pojo.UserOrganization;
import com.wsd.admin.pojo.UserRole;
import com.wsd.admin.service.UserOrganizationService;
import com.wsd.admin.service.UserRoleService;
import com.wsd.admin.service.UserService;
import com.wsd.admin.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{

    @Autowired
    private UserMapper userMapper;

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
                user.setSalt(UUID.randomUUID().toString().replace("-","").substring(0,6));
                user.setPassword(DigestUtils.md5DigestAsHex(("123456"+user.getUsername()+user.getSalt()).getBytes()));
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
                //删除组织角色关联关系
                Example example = new Example(UserOrganization.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("userId",user.getUserId());
                userOrganizationService.deleteByExample(example);
                Example example2 = new Example(UserRole.class);
                Example.Criteria criteria2 = example2.createCriteria();
                criteria2.andEqualTo("userId",user.getUserId());
                userRoleService.deleteByExample(example2);
                //建立组织角色关联关系
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
                return  new SuccessTip();
            }
        }
    }

    @Override
    public void removeOne(Integer id) {
        this.deleteByKey(id);
        Example example = new Example(UserOrganization.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",id);
        userOrganizationService.deleteByExample(example);
        Example example2 = new Example(UserRole.class);
        Example.Criteria criteria2 = example2.createCriteria();
        criteria2.andEqualTo("userId",id);
        userRoleService.deleteByExample(example2);
    }

    @Override
    public void removeBatch(Integer[] ids) {
        for(Integer id:ids){
            removeOne(id);
        }
    }
}
