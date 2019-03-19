package com.wsd.admin.dao;

import com.common.dao.BaseDao;
import com.common.model.ZtreeResult;
import com.wsd.admin.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionMapper extends BaseDao<Permission>{

    List<ZtreeResult> selectPermissionTree(Integer roleId);

    List<Permission> selectByUserId(Integer id);
}
