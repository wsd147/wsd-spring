package com.wsd.admin.controller;

import com.common.controller.BaseController;
import com.common.tips.ErrorTip;
import com.common.tips.SuccessTip;
import com.common.tips.Tip;
import com.wsd.admin.pojo.Organization;
import com.wsd.admin.service.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/sys/organization")
public class OrganizationController extends BaseController{

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
            example.setOrderByClause("orderNum asc");
            List<Organization> users = organizationService.selectAll();
            return users;
        } catch (Exception e) {
            LOGGER.error("组织列表加载失败:+" + e.getMessage());
            return  null;
        }
    }

    @RequestMapping("/add/{pid}")
    public ModelAndView add(@PathVariable("pid") String pid){
        try{
            ModelAndView modelAndView = new ModelAndView(prefix+"/add");
            Organization organization = organizationService.selectByKey(pid);
            if(organization!=null){
                modelAndView.addObject("pId",organization.getId());
                modelAndView.addObject("pName",organization.getName());
            }else{
                modelAndView.addObject("pId",0);
                modelAndView.addObject("pName","总组织");
            }
            return modelAndView;
        }catch (Exception e){
            LOGGER.error("打开组织添加页面失败："+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    @RequestMapping("edit/{id}")
    public ModelAndView edit(@PathVariable("id") Integer id){
        try{
            ModelAndView modelAndView = new ModelAndView(prefix+"/edit");
            Organization organization = organizationService.selectByKey(id);
            Organization Parentorganization = organizationService.selectByKey(organization.getPid());
            if(Parentorganization==null){
                modelAndView.addObject("parentName","总组织");
            }else{
                modelAndView.addObject("parentName",organizationService.selectByKey(organization.getPid()).getName());
            }
            modelAndView.addObject("organization",organization);
            return modelAndView;
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("打开组织修改页面失败："+e.getMessage());
            return null;
        }
    }

    @PostMapping("/save")
    @ResponseBody
    public Tip save(Organization organization){
        try{
            if(organization.getId()==null){
                Example example = new Example(Organization.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("organizationId",organization.getOrganizationId());
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
                if(organizationService.selectByKey(organization.getId())==null){
                    return new ErrorTip(500,"此部门不存在，请刷新");
                }else {
                    try{
                        organization.setUtime(new Date());
                        organizationService.updateByKeySelective(organization);
                        return  new SuccessTip();
                    }catch (Exception e){
                        e.printStackTrace();
                        return new ErrorTip(500,"修改失败失败");
                    }
                }
            }
        }catch (Exception e){
            LOGGER.error("组织保存失败："+e.getMessage());
            return new ErrorTip(500,"程序异常");
        }
    }

    @DeleteMapping("/remove/{id}")
    @ResponseBody
    public Tip remove(@PathVariable("id")Integer id){
        try{
            Organization organization = organizationService.selectByKey(id);
            if(organization==null){
                return  new ErrorTip(500,"删除失败！该组织不存在");
            }else{
                Example example = new Example(Organization.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("pid",organization.getId());
                List<Organization> organizations = organizationService.selectByExample(example);
                if(organizations.size()>0){
                    return new ErrorTip(500,"删除失败！该组织下包含其他组织");
                }else{
                    organizationService.deleteByKey(id);
                    return  new SuccessTip();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("组织删除失败"+e.getMessage());
            return new ErrorTip(500,"程序异常");
        }
    }

    @PutMapping("/enableOrDisable/{type}/{id}")
    @ResponseBody
    public Tip enableOrDisable(@PathVariable("type") String type,@PathVariable("id") Integer id){
        try {
            return  organizationService.enableOrDisable(id,type);
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("组织禁用/启用失败"+e.getMessage());
            return new ErrorTip(500,"程序异常");
        }
    }
}
