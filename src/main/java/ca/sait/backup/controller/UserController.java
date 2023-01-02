package ca.sait.backup.controller;



import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.request.EmailDTO;
import ca.sait.backup.model.request.LoginRequest;
import ca.sait.backup.model.request.RegisterRequest;
import ca.sait.backup.model.response.LoginResponse;
import ca.sait.backup.model.response.RegisterResponse;

import ca.sait.backup.service.EmailService;
import ca.sait.backup.service.SessionService;
import ca.sait.backup.service.UserService;
import ca.sait.backup.service.impl.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.Map;


//Writer : Park, Ibrahim
@RestController
@RequestMapping("/api/v1/pri/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final EmailServiceImpl emailService;
    private final SessionService sessionService;
    private final AuthenticationManager authenticationManager;

    //Writer : Park, John
    @PostMapping("/login")
    private LoginResponse processLogin(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {

        boolean valid = userService.loginUser(loginRequest);
        LoginResponse lResponse = new LoginResponse(valid);
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Assign user a JWT session
        if (valid) {
            // Create session container, then JWT token
            User user = userService.dev_GetUserByEmail(loginRequest.getEmail());
            String jwtToken = sessionService.createToken(authentication, user);
            System.out.println("Creating JWT Token: " + jwtToken);
//             Create cookie using JWT token
            Cookie cookie = new Cookie("session", jwtToken);
            cookie.setSecure(false);
            cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return lResponse;
    }

    //Writer : Park,
    //
    @ResponseBody
    @PostMapping(value = "/sendEmail", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RedirectView findPasswordByEmailByJson(@RequestBody EmailDTO emailDTO ) throws IllegalAccessException {
        EmailDTO findPasswordRequest = emailService.createMailAndChangePassword(emailDTO);
        emailService.mailSend(findPasswordRequest);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost/general/login");
        return redirectView;
    }
    //Writer : Park,
    @PostMapping(value ="/sendEmail", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView findPasswordByEmailByFormRequest(EmailDTO emailDTO) throws IllegalAccessException {
        EmailDTO findPasswordRequest = emailService.createMailAndChangePassword(emailDTO);
        emailService.mailSend(findPasswordRequest);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost/general/login");
        return redirectView;
    }
    //Writer : Park,
    @RequestMapping(value = "/logout",method =  {RequestMethod.GET, RequestMethod.POST})
    public RedirectView logOutForm(HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie("session",null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost/general/login");
        return redirectView;
    }



}
