package com.system.service;

import com.common.service.BaseService;
import com.system.dao.model.Role;

public interface RoleService extends BaseService<Role>{
    Integer removeBatch(Long [] ids) throws Exception;
}
