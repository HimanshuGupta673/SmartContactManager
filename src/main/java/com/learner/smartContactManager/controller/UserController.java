package com.learner.smartContactManager.controller;

import com.learner.smartContactManager.dao.UserRepository;
import com.learner.smartContactManager.entities.Contact;
import com.learner.smartContactManager.entities.User;
import com.learner.smartContactManager.helper.Message;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    public void addCommonData(Model model, Principal principal){
        String userName = principal.getName();
        User user = userRepository.getUserByUserName(userName);
        model.addAttribute("user",user);
    }
    @RequestMapping("/index")
    public String dashboard(Model model, Principal principal){
        model.addAttribute("title","User Dashboard");
        return "normal/dashBoard";
    }

    @RequestMapping("/addContact")
    public String openAddContactForm(Model model){
        model.addAttribute("title","Add Contact");
        model.addAttribute("contact",new Contact());

        return "normal/addContact";
    }

    @PostMapping("/processContact")
    public String processContact(@ModelAttribute Contact contact, @RequestParam("profileImage")MultipartFile file, Principal principal, HttpSession session){
        try{
        String name = principal.getName();
        User user = userRepository.getUserByUserName(name);

        if(file.isEmpty()){
            System.out.println("file is empty");
        }else{
            contact.setImage(file.getOriginalFilename());
            File saveFile = new ClassPathResource("static/img").getFile();
            Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
            Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Image is uploaded!");
        }

        contact.setUser(user);
        user.getContacts().add(contact);
        userRepository.save(user);

        session.setAttribute("message",new Message("contact added successfully !!","success"));
        }catch (Exception e){
            session.setAttribute("message",new Message("Something went wrong !!","danger"));
            System.out.println("Error : "+e.getMessage());
            e.printStackTrace();
        }
        return "normal/addContact";
    }

    @RequestMapping("/showContacts")
    public String showContacts(){
        return "showContacts";
    }

}
