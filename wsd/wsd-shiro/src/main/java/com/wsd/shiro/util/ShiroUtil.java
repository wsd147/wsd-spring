package com.wsd.shiro.util;

import com.wsd.admin.pojo.User;
import org.apache.shiro.SecurityUtils;

public class ShiroUtil {

    public static User  getCurrentUser(){
        return (User) SecurityUtils.getSubject().getPrincipal();
    }
}
