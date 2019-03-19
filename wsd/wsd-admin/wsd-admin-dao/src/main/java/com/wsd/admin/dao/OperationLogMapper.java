package com.wsd.admin.dao;

import com.common.dao.BaseDao;
import com.wsd.admin.pojo.OperationLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperationLogMapper extends BaseDao<OperationLog>{
}
