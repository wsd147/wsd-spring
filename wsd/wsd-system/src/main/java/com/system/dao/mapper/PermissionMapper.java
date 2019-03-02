package com.system.dao.mapper;

import com.common.dao.BaseDao;
import com.common.dao.model.Menu;
import com.common.dao.model.ZtreeResult;
import com.system.dao.model.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionMapper extends BaseDao<Permission>{

    List<ZtreeResult> selectPermissionTree(Integer roleId);

    List<Permission> selectByUserId(Integer id);
}
