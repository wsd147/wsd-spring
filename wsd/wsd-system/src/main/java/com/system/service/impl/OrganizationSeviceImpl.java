package com.system.service.impl;

import com.common.dao.BaseDao;
import com.common.service.BaseServiceImpl;
import com.common.tips.ErrorTip;
import com.common.tips.SuccessTip;
import com.common.tips.Tip;
import com.system.dao.mapper.OrganizationMapper;
import com.system.dao.model.Organization;
import com.system.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrganizationSeviceImpl extends BaseServiceImpl<Organization> implements OrganizationService{

    @Autowired
    OrganizationMapper organizationMapper;

    @Override
    protected BaseDao<Organization> getMapper() {
        return organizationMapper;
    }

    @Override
    public Tip enableOrDisable(Integer id, String type) {
        Organization organization = this.selectByKey(id);
        if(organization==null){
            return  new ErrorTip(500,"该组织不存在，请刷新后再操作");
        }else{
            if("enable".equals(type)){
                if(organization.getDelFlag() == 1){
                    return new ErrorTip(500,"该组织已经是启用状态");
                }
                Example example = new Example(Organization.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("id",organization.getPid());
                Organization parentOrganization = this.selectOne(example);
                if(parentOrganization!=null&&parentOrganization.getDelFlag()==0){
                    return new ErrorTip(500,"该组织的父组织为禁用状态，请先启用父组织");
                }
                organization.setDelFlag(1);
                organization.setUtime(new Date());
                this.updateByKeySelective(organization);
                return new SuccessTip();
            }else if("disable".equals(type)){
                if(organization.getDelFlag() == 0){
                    return new ErrorTip(500,"该组织已经是禁用状态");
                }else {
                    Example example = new Example(Organization.class);
                    Example.Criteria criteria = example.createCriteria();
                    criteria.andEqualTo("pid",organization.getId());
                    List<Organization> organizations = this.selectByExample(example);
                    organization.setDelFlag(0);
                    organization.setUtime(new Date());
                    this.updateByKeySelective(organization);
                    //禁用该组织下的所有子组织
                    for(Organization organization1:organizations){
                        organization1.setDelFlag(0);
                        organization1.setUtime(new Date());
                        this.updateByKeySelective(organization1);
                    }
                    return  new SuccessTip();
                }
            }
        }
        return  null;
    }
}
