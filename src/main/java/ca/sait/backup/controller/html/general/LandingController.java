package ca.sait.backup.controller.html.general;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
