package ca.sait.backup.controller.html.general.settings;

import ca.sait.backup.model.business.JWTSessionContainer;
import ca.sait.backup.model.entity.User;
import ca.sait.backup.service.SessionService;
import ca.sait.backup.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/general/settings")
@RequiredArgsConstructor
public class SettingsController {

    private final SessionService sessionService;
    private final UserService userService;
    @GetMapping("/home")
    public String GetSettingsHome(Model model, HttpServletRequest request) {
        // Expose session variables
        sessionService.exposeEssentialVariables(request, model);
        JWTSessionContainer sessionContainer = sessionService.extractSession(request);
        User user = userService.dev_GetUserById(sessionContainer.getUserId());

        String userFullName = user.getName();
        String firstName = "", lastName = "";

        if (userFullName.indexOf(" ") > 0) {
            firstName = userFullName.split(" ")[0];
            lastName = userFullName.split(" ")[1];
        }

        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("userObj", user);

        return "/general/settings_profile.html";
    }

    @GetMapping("/security")
    public String GetSettingsSecurity(Model model, HttpServletRequest request) {
        // Expose session variables
        this.sessionService.exposeEssentialVariables(request, model);

        return "/general/settings_security.html";
    }

    @GetMapping("/delete")
    public String GetSettingsDelete(Model model, HttpServletRequest request) {
        // Expose session variables
        this.sessionService.exposeEssentialVariables(request, model);
        return "/general/settings_delete.html";
    }


}
