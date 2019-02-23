package com.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HelloWordController {

    @RequestMapping("/")
    public String hello(){
        return "/hello";
    }
}
