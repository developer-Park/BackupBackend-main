package ca.sait.backup.service.impl;

import ca.sait.backup.mapper.UserRepository;
import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.request.EmailDTO;
import ca.sait.backup.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//Write : Park
@Service
@RequiredArgsConstructor
public class EmailServiceImpl {
    // Create mail content and change member password with temporary password
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

    @Transactional
    public EmailDTO createMailAndChangePassword(EmailDTO emailDTO) throws IllegalAccessException {
        System.out.println(emailDTO.getEmail());
        System.out.println(emailDTO.getUsername());
        String str = getTempPassword();
        emailDTO.setEmail(emailDTO.getEmail());
        emailDTO.setTitle("BackUpBuddy This is the temporary password email.");
        emailDTO.setMessage("Hello. BackUpBuddy This is the temporary password email." + " Your temporary password : "
                + str + " ." + "Please after sign in, change the temporary password to your password");
        updatePassword(str, emailDTO);
        return emailDTO;
    }

    //임시 비밀번호로 업데이트
    @Transactional
    public void updatePassword(String str, EmailDTO emailDTO) throws IllegalAccessException {
        String newPassword = CommonUtils.SHA256(str);
        User user = userRepository.findByNameAndEmail(emailDTO.getUsername(), emailDTO.getEmail());
        if (user == null) {
            throw new IllegalAccessException("Invalid email, username.");
        } else {
            System.out.println(str);
            user.updatePassword(newPassword);
            System.out.println(user);
        }
    }

    // Creating temporary password phrases with a random function
    public String getTempPassword() {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        StringBuilder str = new StringBuilder();
        // Create a syntax by randomly picking 10 values ​​of the length of the character array.
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str.append(charSet[idx]);
        }
        return str.toString();
    }

    //send a mail
    public void mailSend(EmailDTO emailDTO) {
        System.out.println("Success send email to customer!");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailDTO.getEmail());
        message.setSubject(emailDTO.getTitle());
        message.setText(emailDTO.getMessage());
        message.setFrom("saitpark2022@gmail.com");
        message.setReplyTo("saitpark2022@gmail.com");
        System.out.println("message" + message);
        javaMailSender.send(message);
    }

}
