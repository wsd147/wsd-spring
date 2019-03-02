package com.system.controller;

import com.common.controller.BaseController;
import com.common.dao.model.LayuiTableResult;
import com.common.dao.model.ZtreeResult;
import com.common.tips.ErrorTip;
import com.common.tips.SuccessTip;
import com.common.tips.Tip;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.dao.model.Organization;
import com.system.dao.model.Role;
import com.system.dao.model.User;
import com.system.service.OrganizationService;
import com.system.service.RoleService;
import com.system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/sys/user")
public class UserController extends BaseController{

    private static  final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final String prefix = "system/user";

    @Autowired
    UserService userService;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    RoleService roleService;

    @GetMapping("/user")
    public ModelAndView user(){
        return new ModelAndView(prefix+"/user");
    }

    @GetMapping("/list")
    @ResponseBody
    public LayuiTableResult<User> list(@RequestParam("page") Integer page,
                                       @RequestParam("limit") Integer limit,User user, Integer orgnId){
        LayuiTableResult layuiTableResult = null;
        try {
            PageHelper.startPage(page,limit);
            List<User> users = new ArrayList<>();
            if(orgnId ==null){
                users = userService.select(user);
            }else{
                users = userService.selectUserByOrgnId(orgnId);
            }
            PageInfo<User> PageInfo = new PageInfo<>(users);
            layuiTableResult  = new LayuiTableResult<User>(0,null,PageInfo.getTotal(),PageInfo.getList());
        }catch (Exception e){
            LOGGER.error("用户列表加载失败:+"+e.getMessage());
            e.printStackTrace();
            layuiTableResult = new LayuiTableResult<User>(0,"加载失败",null,null);
        }
        return layuiTableResult;
    }

    @RequestMapping("/add")
    public  ModelAndView add(){
        List<Role> roles = roleService.selectAll();
        return  new ModelAndView(prefix+"/add").addObject("roles",roles);
    }

    @RequestMapping("/edit/{id}")
    public  ModelAndView edit(@PathVariable("id")Integer id){
        ModelAndView modelAndView = new ModelAndView(prefix+"/edit");
        try {
            List<Role> roles = roleService.selectAll();
            User user = userService.selectByKey(id);
            modelAndView.addObject("user",user).addObject("roles",roles);
            return modelAndView;
        }catch (Exception e){
            LOGGER.error("进入修改用户页面失败："+e.getMessage());
            e.printStackTrace();
            return  modelAndView;
        }
    }

    @PostMapping("/save")
    @ResponseBody
    public Tip save(User user,Integer organizaId,@RequestParam("roleId") Integer[] roleIds){
        try {
            return  userService.insertOrUpdate(user,organizaId,roleIds);
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
            if(userService.selectByKey(id)==null){
                return new ErrorTip(500,"删除失败，该用户已经不存在，请刷新");
            }else{
//                userService.removeOne(id);
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
    public Tip removeBatch(@RequestParam("ids[]") Integer [] ids){
        try{
//            userService.removeBatch(ids);
            return new SuccessTip();
        }catch (Exception e){
            LOGGER.error("删除角色失败："+e.getMessage());
            e.printStackTrace();
            return new ErrorTip(500,"程序错误");
        }
    }

    @GetMapping("/getOrignTree")
    @ResponseBody
    public List<ZtreeResult> getAllPermiTree(){
        try{
            Example example = new Example(Organization.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("delFlag",1);
            List<Organization> organizations = organizationService.selectByExample(example);
            List<ZtreeResult> ztreeResults = new ArrayList<>();
            for(Organization organization:organizations){
                ZtreeResult ztreeResult = new ZtreeResult();
                ztreeResult.setId(organization.getId());
                ztreeResult.setName(organization.getName());
                ztreeResult.setpId(organization.getPid());
                ztreeResults.add(ztreeResult);
            }
            return  ztreeResults;
        }catch (Exception e){
            LOGGER.error("加载失败："+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
