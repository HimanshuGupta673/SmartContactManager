package com.learner.smartContactManager.service;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {

    public static boolean sendEmail(String subject, String message, String to) {
        boolean isSent = false;

        String from = "himanshuu673@gmail.com";
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", "465"); // Gmail SMTP port
        properties.setProperty("mail.smtp.ssl.enable", "true"); // Enable SSL
        properties.setProperty("mail.smtp.auth", "true"); // Enable authentication

        // Get the default Session object.
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("himanshuu673@gmail.com", "czegrvgvwyugpekx"); //
            }
        });

        try {
            MimeMessage mimeMessage = new MimeMessage(session);

            mimeMessage.setFrom(new InternetAddress(from));

            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            mimeMessage.setSubject(subject);

//            mimeMessage.setText(message);
            mimeMessage.setContent(message,"text/html");

            Transport.send(mimeMessage);
            isSent = true;
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        return isSent;
    }
}
