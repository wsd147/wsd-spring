package com.system.controller;

import com.common.controller.BaseController;
import com.common.dao.model.LayuiTableResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.dao.model.User;
import com.system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/sys/user")
public class UserController extends BaseController{

    private static  final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private String prefix = "system/user";

    @Autowired
    UserService userService;

    @GetMapping("/user")
    public ModelAndView user(){
        return new ModelAndView(prefix+"/user");
    }

    @GetMapping("/list")
    @ResponseBody
    public LayuiTableResult<User> list(@RequestParam("page") Integer page,
                                       @RequestParam("limit") Integer limit,User user){
        LayuiTableResult layuiTableResult = null;
        try {
            PageHelper.startPage(page,limit);
            List<User> users = userService.select(user);
            PageInfo<User> PageInfo = new PageInfo<>(users);
            layuiTableResult  = new LayuiTableResult<User>(0,null,PageInfo.getTotal(),PageInfo.getList());
        }catch (Exception e){
            LOGGER.error("用户列表加载失败:+"+e.getMessage());
            layuiTableResult = new LayuiTableResult<User>(0,"加载失败",null,null);
        }
        return layuiTableResult;
    }

    @GetMapping("/add")
    public String add(){

        return prefix+"/add";
    }
}
