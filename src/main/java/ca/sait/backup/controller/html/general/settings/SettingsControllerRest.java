package ca.sait.backup.controller.html.general.settings;


import ca.sait.backup.model.business.JWTSessionContainer;
import ca.sait.backup.model.request.ChangePasswordRequest;
import ca.sait.backup.model.request.UpdateUserInformationRequest;
import ca.sait.backup.service.SessionService;
import ca.sait.backup.service.UserService;
import ca.sait.backup.utils.JsonData;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


//Writer:Park
@RestController
@RequestMapping("api/v1/pri/general/settings")
@RequiredArgsConstructor
public class SettingsControllerRest {

    private final UserService  userService;

    private final SessionService sessionService;

    //Writer:Park
    @PostMapping("/details")
    public JsonData updateProfileDetails(@RequestBody UpdateUserInformationRequest updateRequest, HttpServletRequest request) {
        JWTSessionContainer sessionContainer = sessionService.extractSession(request);
        userService.dev_UpdateUser(sessionContainer,updateRequest);
        return JsonData.buildSuccess("");
    }
    //Wrtier:Park,Ibrahim
    @PostMapping("/password")
    public JsonData changePassword(HttpServletRequest request, @RequestBody ChangePasswordRequest changePassword) {
        JWTSessionContainer sessionContainer = sessionService.extractSession(request);
        boolean res = userService.dev_ChangePassword(sessionContainer, changePassword);
        return JsonData.buildSuccess(res ? "true" : "false");
    }
    //Writer : Park
    @PostMapping("/delete")
    public JsonData deleteAccount(HttpServletRequest request) {
        JWTSessionContainer sessionContainer = sessionService.extractSession(request);
        userService.deleteUser(sessionContainer.getUserId(), true);
        return JsonData.buildSuccess("");
    }
    //Write : Park
    @PostMapping("/suspend/{userId}")
    public String suspendAccount(@PathVariable Long userId) {
        userService.suspendUser(userId);
        return "success";
    }

}
