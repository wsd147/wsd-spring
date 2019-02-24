package com.system.service;

import com.common.service.BaseService;
import com.common.tips.Tip;
import com.system.dao.model.Organization;

public interface OrganizationService extends BaseService<Organization>{

    Tip enableOrDisable(Integer id,String type);
}
