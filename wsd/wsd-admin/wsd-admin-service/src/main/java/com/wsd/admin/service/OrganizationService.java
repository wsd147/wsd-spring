package com.wsd.admin.service;

import com.common.tips.Tip;
import com.wsd.admin.pojo.Organization;
import com.wsd.admin.service.base.BaseService;

public interface OrganizationService extends BaseService<Organization> {

    Tip enableOrDisable(Integer id, String type);
}
