package com.learner.smartContactManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/index")
    public String dashboard(){
      return "normal/dashBoard";
    }
}
