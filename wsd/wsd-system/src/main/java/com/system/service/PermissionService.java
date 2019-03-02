package com.system.service;

import com.common.dao.model.Menu;
import com.common.dao.model.ZtreeResult;
import com.common.service.BaseService;
import com.common.tips.Tip;
import com.system.dao.model.Permission;

import java.util.List;

public interface PermissionService extends BaseService<Permission>{
   Tip enableOrDisable(Integer id, String type);
   List<ZtreeResult> selectPermissionTree(Integer roleId);
   List<Menu<Permission>> listMenuTree(Integer id);
}
