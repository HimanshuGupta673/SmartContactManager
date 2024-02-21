package com.learner.smartContactManager.helper;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class RemoveMessageFromSession {
    public void removeSession(){
        try{
            System.out.println("Trying to remove session...");
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpSession session = request.getSession(false); // Passing false to avoid creating a new session
            if(session != null) {
                session.removeAttribute("message");
                System.out.println("Session attribute 'message' removed successfully!");
            } else {
                System.out.println("No session available to remove 'message' attribute.");
            }
        } catch (Exception e){
            // Print the stack trace to understand any potential issues
            e.printStackTrace();
        }
    }
}

