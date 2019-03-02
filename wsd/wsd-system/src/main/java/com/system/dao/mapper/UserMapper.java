package com.system.dao.mapper;

import com.common.dao.BaseDao;
import com.system.dao.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseDao<User>{

    List<User> selectUserByOrgnId(Integer orgnId)throws Exception;

}
