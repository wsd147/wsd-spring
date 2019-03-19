package com.wsd.admin.service;

import com.common.tips.Tip;
import com.wsd.admin.pojo.User;
import com.wsd.admin.service.base.BaseService;

import java.util.List;

public interface UserService extends BaseService<User> {

    List<User> selectUserByOrgnId(Integer orgnId)throws Exception;

    Tip insertOrUpdate(User user, Integer organizaId, Integer[] roleIds);

    void removeOne(Integer id);

    void removeBatch(Integer[] ids);

}
