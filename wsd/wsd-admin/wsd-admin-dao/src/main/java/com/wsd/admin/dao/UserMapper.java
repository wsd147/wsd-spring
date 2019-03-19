package com.wsd.admin.dao;

import com.common.dao.BaseDao;
import com.wsd.admin.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseDao<User>{

    List<User> selectUserByOrgnId(Integer orgnId)throws Exception;

}
