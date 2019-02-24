package com.system.controller;


import com.common.controller.BaseController;
import com.common.tips.ErrorTip;
import com.common.tips.SuccessTip;
import com.common.tips.Tip;
import com.system.dao.model.Permission;
import com.system.service.PermissionService;
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
@RequestMapping("/sys/permission")
public class PermissionController extends BaseController{

    private static  final Logger LOGGER = LoggerFactory.getLogger(PermissionController.class);
    private String prefix = "system/permission";

    @Autowired
    PermissionService permissionService;

    @RequestMapping("/permission")
    public ModelAndView permission(){
        return  new  ModelAndView(prefix+"/permission");
    }

    @GetMapping("/list")
    @ResponseBody
    public List<Permission> list(){
        try {
            Example example = new Example(Permission.class);
            example.setOrderByClause("orderNum asc");
            List<Permission> permissionList = permissionService.selectByExample(example);
            return  permissionList;
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("权限列表加载失败:+" + e.getMessage());
            return  null;
        }
    }

    @RequestMapping("/showIcon")
    public ModelAndView showIcon(){
        return  new  ModelAndView(prefix+"/FontIconList");
    }

    @RequestMapping("/add/{pid}")
    public ModelAndView add(@PathVariable("pid")Integer pid){
        try{
            ModelAndView modelAndView = new ModelAndView(prefix+"/add");
            Permission permission = permissionService.selectByKey(pid);
            if(permission!=null){
                modelAndView.addObject("pId",permission.getPermissionId());
                modelAndView.addObject("pName",permission.getName());
            }else{
                modelAndView.addObject("pId",0);
                modelAndView.addObject("pName","根权限");
            }
            return modelAndView;
        }catch (Exception e){
            LOGGER.error("打开权限添加页面失败："+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("edit/{id}")
    public ModelAndView edit(@PathVariable("id") Integer id){
        try{
            ModelAndView modelAndView = new ModelAndView(prefix+"/edit");
            Permission permission = permissionService.selectByKey(id);
            Permission parentPermission = permissionService.selectByKey(permission.getPid());
            if(parentPermission==null){
                modelAndView.addObject("parentName","根权限");
            }else{
                modelAndView.addObject("parentName",permissionService.selectByKey(permission.getPid()).getName());
            }
            modelAndView.addObject("permission",permission);
            return modelAndView;
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("打开组织修改页面失败："+e.getMessage());
            return null;
        }
    }

    @PostMapping("/save")
    @ResponseBody
    public Tip save(Permission permission){
        try{
            if(permission.getPermissionId()==null){
                Example example = new Example(Permission.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("name",permission.getName());
                if(permissionService.selectByExample(example).size()>0){
                    return new ErrorTip(500,"该权限已经存在");
                }else{
                    try{
                        permission.setCtime(new Date());
                        permission.setStatus(1);
                        permissionService.insert(permission);
                        return  new SuccessTip();
                    }catch (Exception e){
                        e.printStackTrace();
                        return new ErrorTip(500,"添加权限失败");
                    }
                }
            }else{
                if(permissionService.selectByKey(permission.getPermissionId())==null){
                    return new ErrorTip(500,"此权限不存在，请刷新");
                }else {
                    try{
                        permission.setUtime(new Date());
                        permissionService.updateByKeySelective(permission);
                        return  new SuccessTip();
                    }catch (Exception e){
                        e.printStackTrace();
                        return new ErrorTip(500,"修改失败失败");
                    }
                }
            }
        }catch (Exception e){
            LOGGER.error("权限保存失败："+e.getMessage());
            return new ErrorTip(500,"程序异常");
        }
    }

    @DeleteMapping("/remove/{id}")
    @ResponseBody
    public Tip remove(@PathVariable("id")Integer id){
        try{
            Permission permission = permissionService.selectByKey(id);
            if(permission==null){
                return  new ErrorTip(500,"删除失败！该权限不存在");
            }else{
                Example example = new Example(Permission.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("pid",permission.getPermissionId());
                List<Permission> permissions = permissionService.selectByExample(example);
                if(permissions.size()>0){
                    return new ErrorTip(500,"删除失败！该权限下包含其他权限");
                }else{
                    permissionService.deleteByKey(id);
                    return  new SuccessTip();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("权限删除失败"+e.getMessage());
            return new ErrorTip(500,"程序异常");
        }
    }

    @PutMapping("/enableOrDisable/{type}/{id}")
    @ResponseBody
    public Tip enableOrDisable(@PathVariable("type") String type,@PathVariable("id") Integer id){
        try {
            return  permissionService.enableOrDisable(id,type);
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("权限禁用/启用失败"+e.getMessage());
            return new ErrorTip(500,"程序异常");
        }
    }
}
