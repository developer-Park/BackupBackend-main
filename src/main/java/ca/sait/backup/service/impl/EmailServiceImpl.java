package ca.sait.backup.service.impl;

import ca.sait.backup.exception.XDException;
import ca.sait.backup.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;



    //
//    @Override
//    public JavaMailSender getJavaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(587);
//
//        mailSender.setUsername("zhenrong.shi1@gmail.com");
//        mailSender.setPassword("ugdeszrpmmenbnzv");
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");
//
//        return mailSender;
//    }
    public void sendHtmlMessage(String to, String subject,String text) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            Context context = new Context();
            context.setVariable("message", text);
            helper.setFrom("noreply@backup.com");
            helper.setTo(to);
            helper.setSubject(subject);
            String html = templateEngine.process("RegisterMail", context);

            helper.setText(html, true);

            //log.info("Sending email: {} with html body: {}", email, html);
            emailSender.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }
}
//    @Override
//    public void sendEmail(String to, String subject,String text) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("noreply@backup.com");
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(text);
//
//        emailSender.send(message);
//    }
}
