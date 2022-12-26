package ca.sait.backup.controller.html.general;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/logout")
    public String logOutForm(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/general/login"; //주소 요청으로 변경
    }


}

