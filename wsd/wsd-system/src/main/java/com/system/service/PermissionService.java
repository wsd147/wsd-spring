package com.system.service;

import com.common.service.BaseService;
import com.common.tips.Tip;
import com.system.dao.model.Permission;

public interface PermissionService extends BaseService<Permission>{
   Tip enableOrDisable(Integer id, String type);
}
