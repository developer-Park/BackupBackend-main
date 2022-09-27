package ca.sait.backup.service.impl;

import ca.sait.backup.model.business.JWTSessionContainer;
import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.entity.UserNotification;
import ca.sait.backup.service.NotificationService;
import ca.sait.backup.service.SessionService;
import ca.sait.backup.utils.JWTUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private NotificationService notificationService;

    public void exposeEssentialVariables(HttpServletRequest request, Model model) {

        JWTSessionContainer jwtSessionContainer = this.extractSession(
            request
        );

        List<UserNotification> notifications = this.notificationService.backend_getUnreadNotificationsForUser(
          new User(jwtSessionContainer.getUserId())
        );

        // Note: Please do not change these parameters, they are used throughout the rendering engine.
        model.addAttribute("sessionContainer", jwtSessionContainer);
        model.addAttribute("userNotifications", notifications);

    }

    public JWTSessionContainer extractSession(HttpServletRequest request) {

        String jwtToken = "";

        // Grab cookie jar from request
        Cookie[] cookieJar = request.getCookies();
        if (cookieJar != null && cookieJar.length > 0) {

            // Loop through cookies and find session cookie
            Cookie sessionCookie = null;
            for (Cookie cookie: cookieJar) {
                if (cookie.getName().equals("session")) {
                    sessionCookie = cookie;
                }
            }

            // Defensive coding, interceptor should never let this happen.
            if (sessionCookie == null) {
                // TODO: I don't want to throw exception in every single route where this is used. See above
                //throw new Exception("No cookie");
            }

            // If all good, extract the JWT token
            jwtToken = sessionCookie.getValue();

        }

        // First need to validate token
        Claims claims = JWTUtils.checkJWT(jwtToken);

        // Grab session data (JSON) from the processed token
        String sessionData = (String)claims.get("sessionData");

        // Pass off session data to parser
        JWTSessionContainer sessionContainer = JWTSessionContainer.Process(
            sessionData
        );

        return sessionContainer;
    }

    public String createToken(User user) {

        // Create new session container object
        JWTSessionContainer sessionContainer = new JWTSessionContainer();

        // Populate with user info
        sessionContainer.setUserId(user.getId());
        sessionContainer.setEmail(user.getEmail());
        sessionContainer.setName(user.getName());
        sessionContainer.setUserRole(user.getRole());
        sessionContainer.setPhoneNumber(user.getPhone());

        // Stringify (JSON)
        GsonBuilder gsonBuilder = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation();

        Gson gson = gsonBuilder.create();
        String jsonData = gson.toJson(sessionContainer);

        // Tokenize json
        String JWToken = JWTUtils.geneJsonWebToken(
            jsonData
        );

        return JWToken;
    }

}
