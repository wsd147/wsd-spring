package com.wsd.admin.service.impl;

import com.common.dao.BaseDao;
import com.wsd.admin.dao.OperationLogMapper;
import com.wsd.admin.pojo.OperationLog;
import com.wsd.admin.service.OperationLogService;
import com.wsd.admin.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OperationLogServiceImpl extends BaseServiceImpl<OperationLog> implements OperationLogService {

    @Autowired
    OperationLogMapper operationLogMapper;

    @Override
    protected BaseDao<OperationLog> getMapper() {
        return operationLogMapper;
    }
}
