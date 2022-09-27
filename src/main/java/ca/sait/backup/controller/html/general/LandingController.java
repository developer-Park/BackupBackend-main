package ca.sait.backup.controller.html.general;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/general")
public class LandingController {

    @GetMapping("/login")
    public String showLoginForm() {
        System.out.println("Login triggered");
        return "general/login";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        System.out.println("Register triggered");
        return "general/register";
    }

}
