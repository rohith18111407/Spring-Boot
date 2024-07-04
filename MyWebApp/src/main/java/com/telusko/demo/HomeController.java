package com.telusko.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController 
{
    @GetMapping("home")
    @ResponseBody
    public String home() 
    {
        System.out.println("hi");
        return "home";
    }
}
