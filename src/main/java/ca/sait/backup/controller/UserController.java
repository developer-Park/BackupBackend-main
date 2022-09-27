package ca.sait.backup.controller;

import ca.sait.backup.exception.CustomExceptionHandler;

import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.request.LoginRequest;
import ca.sait.backup.model.request.RegisterRequest;
import ca.sait.backup.model.response.LoginResponse;
import ca.sait.backup.model.response.RegisterResponse;
import ca.sait.backup.service.EmailService;

import ca.sait.backup.service.SessionService;
import ca.sait.backup.service.UserService;
import ca.sait.backup.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("api/v1/pri/user")
public class UserController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @PostMapping("/login")
    private LoginResponse processLogin(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        LoginResponse lResponse = new LoginResponse();

        System.out.println("Email: " + loginRequest.getEmail());
        System.out.println("Password: " + loginRequest.getPassword());

        boolean valid = this.userService.validateUser(
            loginRequest.getEmail(),
            loginRequest.getPassword()
        );

        lResponse.setAuthenticated(valid);

        // Assign user a JWT session
        if (valid) {

            // Create session container, then JWT token
            User user = this.userService.dev_GetUserByEmail(
                loginRequest.getEmail()
            );
            String jwtToken = this.sessionService.createToken(
                user
            );

            System.out.println("Creating JWT Token: " + jwtToken);

            // Create cookie using JWT token
            Cookie cookie = new Cookie("session", jwtToken);

            cookie.setSecure(false);
            cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
            cookie.setPath("/");

            response.addCookie(
                cookie
            );

        }

        return lResponse;
    }

    @PostMapping("/register")
    private RegisterResponse processRegister(@RequestBody RegisterRequest registerRequest) {
        RegisterResponse rResponse = new RegisterResponse();

        boolean created = this.userService.processRegister(
            registerRequest.getEmail(),
            registerRequest.getPassword(),
            registerRequest.getName(),
            registerRequest.getPhone(),
            registerRequest.getCompany(),
            registerRequest.getAddress(),
            registerRequest.getCountry()
        );

        rResponse.setCreationStatus(created);

        // TODO: Send authenticated JWT token.

        return rResponse;
    }


}
