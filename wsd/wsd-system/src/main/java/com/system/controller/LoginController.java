package com.system.controller;

import com.common.controller.BaseController;
import com.common.dao.model.Menu;
import com.system.dao.model.Permission;
import com.system.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping
public class LoginController extends BaseController{

    @Autowired
    PermissionService permissionService;

    @GetMapping("/index")
    public ModelAndView index(){
        List<Menu<Permission>> menus = permissionService.listMenuTree(4);
        return new ModelAndView("/index_v1").addObject("menus",menus);
    }
}
