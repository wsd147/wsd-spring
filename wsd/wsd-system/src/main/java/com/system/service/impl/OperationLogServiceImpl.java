package com.system.service.impl;

import com.common.dao.BaseDao;
import com.common.service.BaseServiceImpl;
import com.system.dao.mapper.OperationLogMapper;
import com.system.dao.model.OperationLog;
import com.system.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OperationLogServiceImpl extends BaseServiceImpl<OperationLog> implements OperationLogService{

    @Autowired
    OperationLogMapper operationLogMapper;

    @Override
    protected BaseDao<OperationLog> getMapper() {
        return operationLogMapper;
    }
}
