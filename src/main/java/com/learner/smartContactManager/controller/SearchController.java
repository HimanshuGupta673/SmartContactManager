package com.learner.smartContactManager.controller;

import com.learner.smartContactManager.dao.ContactRepository;
import com.learner.smartContactManager.dao.UserRepository;
import com.learner.smartContactManager.entities.Contact;
import com.learner.smartContactManager.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class SearchController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContactRepository contactRepository;
    @GetMapping("/search/{query}")
//    ResponseEntity: This is a class provided by Spring Framework that represents an HTTP response. It allows you to control the HTTP status code, headers, and body of the response.
//
//<?>: This is a wildcard generic type parameter. The <?> indicates that the type of the body of the response is not specified. It can be any type, and it is typically used when you want to handle different types of responses generically.
    public ResponseEntity<?> search(@PathVariable("query") String query, Principal principal){
       User user = userRepository.getUserByUserName(principal.getName());
       List<Contact> contactList = contactRepository.findByNameContainingAndUser(query,user);
        return ResponseEntity.ok(contactList);
    }
}
