package ca.sait.backup.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import ca.sait.backup.mapper.UserRepository;
import ca.sait.backup.model.entity.Email;
import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.request.EmailDTO;
import ca.sait.backup.service.EmailService;
import ca.sait.backup.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl{
    // 메일 내용을 생성하고 임시 비밀번호로 회원 비밀번호를 변경
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

    private final SpringTemplateEngine springTemplateEngine;

    public EmailDTO createMailAndChangePassword(EmailDTO emailDTO) throws IllegalAccessException {
        System.out.println(emailDTO.getEmail());
        String str = getTempPassword();
        emailDTO.setEmail(emailDTO.getEmail());
        emailDTO.setTitle("Cocolo 임시비밀번호 안내 이메일 입니다.");
        emailDTO.setMessage("안녕하세요. Cocolo 임시비밀번호 안내 관련 이메일 입니다." + " 회원님의 임시 비밀번호는 "
                + str + " 입니다." + "로그인 후에 비밀번호를 변경을 해주세요");
        updatePassword(str, emailDTO.getEmail());
        return emailDTO;
    }

    //임시 비밀번호로 업데이트

    public void updatePassword(String str, String userEmail) throws IllegalAccessException {

        User user = userRepository.findByEmail(userEmail);
        if(user == null) {
            throw new IllegalAccessException("이메일 없음.");
        }
        user.updatePassword(str);
    }

    //랜덤함수로 임시비밀번호 구문 만들기

    public String getTempPassword() {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String str = "";

        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }

    // 메일보내기

    public void mailSend(EmailDTO mailDTO) {
        System.out.println("전송 완료!");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDTO.getEmail());
        message.setSubject(mailDTO.getTitle());
        message.setText(mailDTO.getMessage());
        message.setFrom("보낸이@naver.com");
        message.setReplyTo("보낸이@naver.com");
        System.out.println("message" + message);
        javaMailSender.send(message);
    }


    public String setContext(String code, String type) {
        Context context = new Context();
        context.setVariable("code", code);
        return springTemplateEngine.process(type, context);
    }

}
