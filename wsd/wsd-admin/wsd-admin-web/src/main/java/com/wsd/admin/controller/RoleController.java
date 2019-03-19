package com.wsd.admin.controller;

import com.common.model.LayuiTableResult;
import com.common.model.ZtreeResult;
import com.common.tips.ErrorTip;
import com.common.tips.SuccessTip;
import com.common.tips.Tip;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wsd.admin.pojo.Role;
import com.wsd.admin.service.PermissionService;
import com.wsd.admin.service.RoleService;
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
@RequestMapping("/sys/role")
public class RoleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    private  static  final  String prefix = "system/role";

    @Autowired
    RoleService roleService;

    @Autowired
    PermissionService permissionService;

    @RequestMapping("/role")
    public ModelAndView role(){
        return  new ModelAndView(prefix+"/role");
    }

    @GetMapping("/list")
    @ResponseBody
    public LayuiTableResult<Role> list(@RequestParam(name = "page") Integer page ,
                                       @RequestParam(name = "limit") Integer limit, Role role){
        LayuiTableResult layuiTableResult = null;
        try {
            PageHelper.startPage(page,limit);
            List<Role> roles = roleService.select(role);
            PageInfo<Role> PageInfo = new PageInfo<>(roles);
            layuiTableResult  = new LayuiTableResult<Role>(0,null,PageInfo.getTotal(),PageInfo.getList());
        }catch (Exception e){
            LOGGER.error("角色列表加载失败:+"+e.getMessage());
            e.printStackTrace();
            layuiTableResult = new LayuiTableResult<Role>(0,"加载失败",null,null);
        }
        return layuiTableResult;
    }

    @RequestMapping("/add")
    public  ModelAndView add(){
        return  new ModelAndView(prefix+"/add");
    }

    @RequestMapping("/edit/{id}")
    public  ModelAndView edit(@PathVariable("id")Integer id){
        ModelAndView modelAndView = new ModelAndView(prefix+"/edit");
        try {
            Role role = roleService.selectByKey(id);
            modelAndView.addObject("role",role);
            return modelAndView;
        }catch (Exception e){
            LOGGER.error("进入修改角色页面失败："+e.getMessage());
            e.printStackTrace();
            return  modelAndView;
        }
    }

    @PostMapping("/save")
    @ResponseBody
    public Tip save(Role role){
        try {
            if(role.getRoleId()==null){
                Example example = new Example(Role.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("name",role.getName());
                List<Role> roles = roleService.selectByExample(example);
                if(roles.size()>0){
                    return new ErrorTip(500,"添加失败！该角色已经存在");
                }else{
                    role.setCtime(new Date());
                    role.setStatus(0);
                    roleService.insert(role);
                    return new SuccessTip();
                }
            }else{
                if(roleService.selectByKey(role.getRoleId())==null){
                    return  new ErrorTip(500,"修改失败，该角色不存在，请刷新");
                }else{
                    role.setUtime(new Date());
                    roleService.updateByKeySelective(role);
                    return  new SuccessTip();
                }
            }
        }catch (Exception e){
            LOGGER.error("保存角色失败："+e.getMessage());
            e.printStackTrace();
            return  new ErrorTip(500,"程序错误");
        }
    }

    @DeleteMapping("/removeOne/{id}")
    @ResponseBody
    public Tip removeOne(@PathVariable("id") Integer id){
        try{
            if(roleService.selectByKey(id)==null){
                return new ErrorTip(500,"删除失败，该角色已经不存在，请刷新");
            }else{
                roleService.removeOne(id);
                return  new SuccessTip();
            }
        }catch (Exception e){
            LOGGER.error("删除角色失败："+e.getMessage());
            e.printStackTrace();
            return new ErrorTip(500,"程序错误");
        }
    }

    @PostMapping("/removeBatch")
    @ResponseBody
    public  Tip removeBatch(@RequestParam("ids[]") Integer [] ids){
        try{
            roleService.removeBatch(ids);
            return new SuccessTip();
        }catch (Exception e){
            LOGGER.error("删除角色失败："+e.getMessage());
            e.printStackTrace();
            return new ErrorTip(500,"程序错误");
        }
    }

    @GetMapping("/getPermiTree/{roleId}")
    @ResponseBody
    public List<ZtreeResult> getAllPermiTree(@PathVariable("roleId")Integer roleId){
        try{
            return permissionService.selectPermissionTree(roleId);
        }catch (Exception e){
            LOGGER.error("打开授权页面失败："+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/saveAuth")
    @ResponseBody
    public Tip saveAuth(@RequestParam("roleId")Integer roleId ,@RequestParam("ids[]")Integer [] ids){
        try{
            return roleService.saveAuth(roleId,ids);
        }catch (Exception e){
            LOGGER.error("授权失败："+e.getMessage());
            e.printStackTrace();
            return new ErrorTip(500,"程序错误");
        }
    }
}
