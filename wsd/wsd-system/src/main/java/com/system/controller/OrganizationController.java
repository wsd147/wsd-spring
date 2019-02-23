package com.system.controller;

import com.common.dao.model.LayuiTableResult;
import com.common.tips.ErrorTip;
import com.common.tips.SuccessTip;
import com.common.tips.Tip;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.dao.model.Organization;
import com.system.service.OrganizationService;
import javafx.geometry.Orientation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/sys/organization")
public class OrganizationController {

    private static  final Logger LOGGER = LoggerFactory.getLogger(OrganizationController.class);

    private final  String prefix = "system/organization";

    @Autowired
    OrganizationService organizationService;

    @RequestMapping("/organization")
    public ModelAndView dept(){
        return  new ModelAndView(prefix+"/organization");
    }

    @GetMapping("/list")
    @ResponseBody
    public List<Organization> list() {
        try {
            Example example = new Example(Organization.class);
            example.setOrderByClause("orderNum desc");
            List<Organization> users = organizationService.selectAll();
            return users;
        } catch (Exception e) {
            LOGGER.error("部门列表加载失败:+" + e.getMessage());
            return  null;
        }
    }

    @RequestMapping("/add/{pid}")
    public ModelAndView add(@PathVariable("pid") String pid){
        ModelAndView modelAndView = new ModelAndView(prefix+"/add");
        Organization organization = organizationService.selectByKey(pid);
        if(organization!=null){
            modelAndView.addObject("pId",organization.getOrganizationId());
            modelAndView.addObject("pName",organization.getName());
        }else{
            modelAndView.addObject("pId",0);
            modelAndView.addObject("pName","总组织");
        }

        return modelAndView;
    }


    @RequestMapping("edit/{organizationId}")
    public ModelAndView edit(@PathVariable("organizationId") Integer organizationId){
        ModelAndView modelAndView = new ModelAndView(prefix+"/edit");
        Organization organization = organizationService.selectByKey(organizationId);
        modelAndView.addObject("organization",organization);
        return modelAndView;
    }

    @PostMapping("/save")
    @ResponseBody
    public Tip save(Organization organization){
        if(organization.getOrganizationId()==null){
            Example example = new Example(Organization.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("name", organization.getName());
            if(organizationService.selectByExample(example).size()>0){
                return new ErrorTip(500,"该部门已经存在");
            }else{
                try{
                    organization.setCtime(new Date());
                    organization.setDelFlag(1);
                    organizationService.insert(organization);
                    return  new SuccessTip();
                }catch (Exception e){
                    e.printStackTrace();
                    return new ErrorTip(500,"添加失败");
                }
            }
        }else{
            if(organizationService.selectByKey(organization.getOrganizationId())==null){
                return new ErrorTip(500,"此部门不存在，请刷新");
            }else {
                try{
                    organizationService.updateByKeySelective(organization);
                    return  new SuccessTip();
                }catch (Exception e){
                    e.printStackTrace();
                    return new ErrorTip(500,"修改失败失败");
                }
            }
        }
    }
}
