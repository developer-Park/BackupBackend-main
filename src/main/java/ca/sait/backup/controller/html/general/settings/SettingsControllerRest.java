package ca.sait.backup.controller.html.general.settings;


import ca.sait.backup.model.business.JWTSessionContainer;
import ca.sait.backup.model.request.ChangePasswordRequest;
import ca.sait.backup.model.request.UpdateUserInformationRequest;
import ca.sait.backup.service.SessionService;
import ca.sait.backup.service.UserService;
import ca.sait.backup.utils.JsonData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1/pri/general/settings")
@RequiredArgsConstructor
public class SettingsControllerRest {

    @Autowired
    private final UserService  userService;

    @Autowired
    private final SessionService sessionService;

    @PostMapping("/details")
    public JsonData updateProfileDetails(@RequestBody UpdateUserInformationRequest updateRequest, HttpServletRequest request) {

        JWTSessionContainer sessionContainer = this.sessionService.extractSession(request);
        boolean res = this.userService.dev_UpdateUser(sessionContainer,updateRequest);
        return JsonData.buildSuccess("");
    }

    @PostMapping("/password")
    public JsonData changePassword(HttpServletRequest request, @RequestBody ChangePasswordRequest changePassword) {

        JWTSessionContainer sessionContainer = this.sessionService.extractSession(
            request
        );

        boolean res = this.userService.dev_ChangePassword(
            sessionContainer,
            changePassword
        );

        return JsonData.buildSuccess(res ? "true" : "false");
    }

    @PostMapping("/delete")
    public JsonData deleteAccount(HttpServletRequest request) {

        JWTSessionContainer sessionContainer = this.sessionService.extractSession(
            request
        );

        this.userService.dev_ChangeAccountStatus(
            sessionContainer.getUserId(),
            true
        );

        return JsonData.buildSuccess("");
    }

}
