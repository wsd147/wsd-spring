package com.system.service;

import com.common.service.BaseService;
import com.common.tips.Tip;
import com.system.dao.model.Role;

public interface RoleService extends BaseService<Role>{

    Integer removeOne(Integer id);
    Integer removeBatch(Integer [] ids) throws Exception;
    Tip saveAuth(Integer roleId,Integer [] ids) throws Exception;
}
