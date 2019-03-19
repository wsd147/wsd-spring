package com.wsd.admin.dao;

import com.common.dao.BaseDao;
import com.wsd.admin.pojo.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface RoleMapper extends BaseDao<Role>{

    List<Role> selectRolesByUserId(Integer userId);
}
