package com.system.service;

import com.common.service.BaseService;
import com.common.tips.Tip;
import com.system.dao.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService extends BaseService<User>{

    List<User> selectUserByOrgnId(Integer orgnId)throws Exception;

    Tip insertOrUpdate(User user,Integer organizaId,Integer [] roleIds);
}
