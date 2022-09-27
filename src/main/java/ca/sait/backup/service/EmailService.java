package ca.sait.backup.service;

import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;

public interface EmailService {

    void sendHtmlMessage(String to, String subject,String text) throws MessagingException;
}
