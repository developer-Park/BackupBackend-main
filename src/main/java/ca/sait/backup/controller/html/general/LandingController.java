package ca.sait.backup.controller.html.general;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/general")
public class LandingController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "general/login";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "general/register";
    }

    @GetMapping("/findPassword")
    public String showfindPasswordForm() {
        return "general/findPassword";
    }




}

