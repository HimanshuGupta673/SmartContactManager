package com.learner.smartContactManager.controller;

import com.learner.smartContactManager.dao.ContactRepository;
import com.learner.smartContactManager.dao.UserRepository;
import com.learner.smartContactManager.entities.Contact;
import com.learner.smartContactManager.entities.User;
import com.learner.smartContactManager.helper.Message;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContactRepository contactRepository;
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
            contact.setImage("user.png");
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

    @RequestMapping("/showContacts/{page}")
    public String showContacts(@PathVariable("page") Integer page, Model model, Principal principal){
        model.addAttribute("title","Show Contacts");
        String userName = principal.getName();
        User user = userRepository.getUserByUserName(userName);

        Pageable pageable = PageRequest.of(page,5);
        Page<Contact> contacts = contactRepository.findContactByUser(user.getId(),pageable);
        model.addAttribute("contacts",contacts);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPages",contacts.getTotalPages());
        return "normal/showContacts";
    }

    @RequestMapping("/{cId}/contact")
    public String showContactDetail(@PathVariable("cId") Integer cId,Model model,Principal principal){
        Optional<Contact> contactOptional = contactRepository.findById(cId);
        Contact contact = contactOptional.get();

        String name = principal.getName();
        User user = userRepository.getUserByUserName(name);

        if(user.getId()==contact.getUser().getId()){
            model.addAttribute("title",contact.getName());
         model.addAttribute("contact",contact);
        }


         return "normal/showContactDetail";
    }

    @RequestMapping("/delete/{cId}")
    public String deleteContact(@PathVariable("cId") Integer cId,HttpSession session,Model model,Principal principal){
        Contact contact = contactRepository.findById(cId).get();
        String name = principal.getName();
        User user = userRepository.getUserByUserName(name);
        if(user.getId()==contact.getUser().getId()){
            contact.setUser(null);
            contactRepository.delete(contact);
            session.setAttribute("message",new Message("User has been deleted successfully!","success"));
        }
        return "redirect:/user/showContacts/0";
    }

}
