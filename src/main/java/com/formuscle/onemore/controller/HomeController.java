package com.formuscle.onemore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String main(){
        return "home";
    }

    @RequestMapping("/home")
    public String home(){
        return "home";
    }

}
