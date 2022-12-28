//package ca.sait.backup.oauth2;
//
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import javax.servlet.http.HttpSession;
//
//@RequiredArgsConstructor
//@Controller
//public class IndexController{
//
//    private final HttpSession httpSession;
//
//    @GetMapping("/")
//    public void  index(Model model) {
//        SessionUser user = (SessionUser)httpSession.getAttribute("user");
//        if(user != null) {
//            model.addAttribute("userName", user.getName());
//        }
//    }
//}