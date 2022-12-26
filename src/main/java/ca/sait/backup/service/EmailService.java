package ca.sait.backup.service;

import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.validation.constraints.Email;

public interface EmailService {

    public void SendEmail(Email email);
}
