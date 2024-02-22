package com.learner.smartContactManager.controller;

import com.learner.smartContactManager.dao.UserRepository;
import com.learner.smartContactManager.entities.Contact;
import com.learner.smartContactManager.entities.User;
import com.learner.smartContactManager.helper.Message;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class HomeController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
    public String registerUser(@Valid @ModelAttribute("user") User user ,BindingResult result1, @RequestParam(value = "agreement",defaultValue = "false") boolean agreement, Model model, HttpSession session){
        try{
            if(!agreement){
                System.out.println("you have not agreed terms and conditions");
                session.removeAttribute("message");
                throw new Exception("you have not agreed terms and conditions");

            }
            if(result1.hasErrors()){
                System.out.println("error: "+result1.toString());
                model.addAttribute("user",user);
                return "signup";
            }
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setImageUrl("user.png");
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            User result = userRepository.save(user);
            model.addAttribute("user",new User());
            session.setAttribute("message",new Message("Successfully Registered ","alert-success"));
            session.removeAttribute("message");
            return "signup";
        }catch (Exception e){
          e.printStackTrace();
          model.addAttribute("user",user);
          session.setAttribute("message",new Message("Something went wrong" + e.getMessage(),"alert-danger"));
          return "signup";
        }
    }
    @RequestMapping("signin")
    public String login(Model model){
        model.addAttribute("title","Login Page");
        return "login";
    }


}
