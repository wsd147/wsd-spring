package com.wsd.admin.controller;

import com.common.controller.BaseController;
import com.common.model.Menu;
import com.common.tips.ErrorTip;
import com.common.tips.SuccessTip;
import com.common.tips.Tip;
import com.wsd.admin.pojo.Permission;
import com.wsd.admin.pojo.User;
import com.wsd.admin.service.PermissionService;
import com.wsd.shiro.listener.ShiroSessionListener;
import com.wsd.shiro.util.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping
public class LoginController extends BaseController{

    @Autowired
    PermissionService permissionService;

    @Autowired
    ShiroSessionListener shiroSessionListener;
    @GetMapping("/")
    public ModelAndView toLogin(){
        return new ModelAndView("/login");
    }

    @PostMapping("/login")
    @ResponseBody
    public Tip login(@RequestParam("userName")String userName, String password, boolean rememberMe, Model model){
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName,password,rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try{
            subject.login(usernamePasswordToken);
            User user=(User) subject.getPrincipal();
            //更新用户登录时间，也可以在ShiroRealm里面做
            getSession().setAttribute("user", user);
            model.addAttribute("user",user);
            model.addAttribute("count",shiroSessionListener.getSessionCount());

            return new SuccessTip();
        }catch (Exception e){
            e.printStackTrace();
            return  new ErrorTip(500,e.getMessage());
        }
    }

    @GetMapping("/index")
    public ModelAndView index(){
        List<Menu<Permission>> menus = permissionService.listMenuTree(ShiroUtil.getCurrentUser().getUserId());
        return new ModelAndView("/index_v1").addObject("menus",menus);
    }
}
