package com.wsd.admin.service;

import com.common.model.Menu;
import com.common.model.ZtreeResult;
import com.common.tips.Tip;
import com.wsd.admin.pojo.Permission;
import com.wsd.admin.service.base.BaseService;

import java.util.List;

public interface PermissionService extends BaseService<Permission> {
   Tip enableOrDisable(Integer id, String type);
   List<ZtreeResult> selectPermissionTree(Integer roleId);
   List<Menu<Permission>> listMenuTree(Integer id);
}
