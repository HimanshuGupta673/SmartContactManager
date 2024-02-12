package com.learner.smartContactManager.controller;

import com.learner.smartContactManager.dao.UserRepository;
import com.learner.smartContactManager.entities.Contact;
import com.learner.smartContactManager.entities.User;
import com.learner.smartContactManager.helper.Message;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("title","Home - Smart Contact Manager");
        return "home";
    }
    @GetMapping("/about")
    public String about(Model model){
        model.addAttribute("title","About - Smart Contact Manager");
        return "about";
    }
    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("title","Register - Smart Contact Manager");
         model.addAttribute("user",new User());
        return "signup";
    }
    @RequestMapping(value = "/do_register",method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("user") User user, @RequestParam(value = "agreement",defaultValue = "false") boolean agreement, Model model, HttpSession session){
        try{
            if(!agreement){
                System.out.println("you have not agreed terms and conditions");
                throw new Exception("you have not agreed terms and conditions");
            }
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            User result = userRepository.save(user);
            model.addAttribute("user",new User());
            session.setAttribute("message",new Message("Successfully Registered ","alert-success"));
            return "signup";
        }catch (Exception e){
          e.printStackTrace();
          model.addAttribute("user",user);
          session.setAttribute("message",new Message("Something went wrong" + e.getMessage(),"alert-danger"));
          return "signup";
        }
    }
}
