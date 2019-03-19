package com.wsd.admin.service;

import com.common.tips.Tip;
import com.wsd.admin.pojo.Role;
import com.wsd.admin.service.base.BaseService;

public interface RoleService extends BaseService<Role> {

    Integer removeOne(Integer id);
    Integer removeBatch(Integer[] ids) throws Exception;
    Tip saveAuth(Integer roleId, Integer[] ids) throws Exception;
}
