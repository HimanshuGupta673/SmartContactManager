package com.learner.smartContactManager.helper;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

@Component
public class RemoveMessageFromSession {
    public void removeSession(){
        try{
            System.out.println("session removed successfully!");
            HttpSession session = ((HttpServletRequest) RequestContextHolder.getRequestAttributes()).getSession();
            session.removeAttribute("message");
        }catch (Exception e){

        }
    }
}
