package ca.sait.backup.controller;



import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.request.LoginRequest;
import ca.sait.backup.model.request.RegisterRequest;
import ca.sait.backup.model.response.LoginResponse;
import ca.sait.backup.model.response.RegisterResponse;
import ca.sait.backup.service.EmailService;

import ca.sait.backup.service.SessionService;
import ca.sait.backup.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


//Writer : Park, Ibrahim
@RestController
@RequestMapping("api/v1/pri/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final SessionService sessionService;

    //Writer : Park, John
    @PostMapping("/login")
    private LoginResponse processLogin(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {

        boolean valid = userService.loginUser(loginRequest);
        LoginResponse lResponse = new LoginResponse(valid);

        // Assign user a JWT session
        if (valid) {
            // Create session container, then JWT token
            User user = userService.dev_GetUserByEmail(loginRequest.getEmail());
            String jwtToken = sessionService.createToken(user);
            System.out.println("Creating JWT Token: " + jwtToken);
            // Create cookie using JWT token
            Cookie cookie = new Cookie("session", jwtToken);
            cookie.setSecure(false);
            cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return lResponse;
    }
    @ResponseBody
    @PostMapping("/register")
    private RegisterResponse processRegister(@RequestBody RegisterRequest registerRequest) {
        boolean created = userService.processRegister(registerRequest);
        return new RegisterResponse(created);
    }



}
