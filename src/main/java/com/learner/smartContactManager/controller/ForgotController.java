package com.learner.smartContactManager.controller;

import com.learner.smartContactManager.dao.UserRepository;
import com.learner.smartContactManager.entities.User;
import com.learner.smartContactManager.service.EmailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

@Controller
public class ForgotController {
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    Random random = new Random(1000);
    @RequestMapping("/forgot")
    public String openEmailForm(){
        return "forgotEmailForm";
    }
    @PostMapping("/sendOtp")
    public String sendOtp(@RequestParam("email") String email, HttpSession session){
        int otp = random.nextInt(999999);

        String subject = "OTP FROM SCM TO RESET YOUR PASSWORD";
        String message = ""+"<div style = 'border:1px solid #e2e2e2';padding:20px>"+"<h1>"+"OTP is"+"<b>"+otp+"</n"+"</h1>"+"</div>";
        String to  = email;

        boolean flag = emailService.sendEmail(subject,message,to);
        if (flag){
            session.setAttribute("myotp",otp);
            session.setAttribute("email",email);
            return "verifyOtp";
        }else{
            session.setAttribute("message","Check your email id");
            return "forgotEmailForm";
        }
    }

    @PostMapping("/verifyOtp")
    public String verifyOtp(@RequestParam("otp") int otp, HttpSession session){
       int myotp = (int)session.getAttribute("myotp");
       String email = (String)session.getAttribute("email");

       if(myotp==otp){
          User user =  userRepository.getUserByUserName(email);
           if(user==null){
               session.setAttribute("message","User does not exist with this email !");
               return "forgotEmailForm";
           }
           return "changePasswordForm";
       }else{
           session.setAttribute("message","you have entered wrong otp");
           return "verifyOtp";
       }
    }
    @PostMapping("/changePassword")
    public String changePassword(@RequestParam("newPassword") String newPassword, HttpSession session){
       String email = (String)session.getAttribute("email");
       User user = userRepository.getUserByUserName(email);
       user.setPassword(bCryptPasswordEncoder.encode(newPassword));
       userRepository.save(user);
       return "redirect:/signin?change=password changed successfully!!";
    }
}
