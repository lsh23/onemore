package com.formuscle.onemore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String main(HttpSession session){
        Object login = session.getAttribute("login");
        if(login != null) return "home";
        return "index";
    }

    @RequestMapping("/home")
    public String home(){
        return "home";
    }

    @RequestMapping("/signUp")
    public String signUp(){
        return "signUp";
    }
}
