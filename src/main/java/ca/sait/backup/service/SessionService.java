package ca.sait.backup.service;

import ca.sait.backup.model.business.JWTSessionContainer;
import ca.sait.backup.model.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

public interface SessionService {

    public JWTSessionContainer extractSession(HttpServletRequest request);

    public void exposeEssentialVariables(HttpServletRequest request, Model model);

    public String createToken(Authentication authentication, User user);

}
