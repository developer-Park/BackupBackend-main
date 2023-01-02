package ca.sait.backup.security;


//import com.shopping.parkshopping.model.Role;

import ca.sait.backup.mapper.UserRepository;
import ca.sait.backup.model.business.JWTSessionContainer;
import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.entity.UserRole;
import ca.sait.backup.service.SessionService;
import ca.sait.backup.utils.JWTUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {



    private final  UserRepository userRepository;



    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        String email = token.getPrincipal().getAttributes().get("email").toString();
        if (userRepository.findUserByEmail(email).isPresent()) {
            User presenter = userRepository.findUserByEmail(email).orElseThrow(IllegalArgumentException::new);
            JWTSessionContainer sessionContainer = new JWTSessionContainer();
            // Populate with user info
            sessionContainer.setUserId(presenter.getId());
            sessionContainer.setEmail(presenter.getEmail());
            sessionContainer.setName(presenter.getName());
            sessionContainer.setUserRole(presenter.getRole());
            // Stringify (JSON)
            GsonBuilder gsonBuilder = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation();

            Gson gson = gsonBuilder.create();
            String jsonData = gson.toJson(sessionContainer);

            // Tokenize json
            String JWToken = JWTUtils.geneJsonGoogleWebToken(presenter.getEmail(), jsonData);
            System.out.println(JWToken);

            Cookie cookie = new Cookie("session", JWToken);
            cookie.setSecure(false);
            cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
            cookie.setPath("/");
            httpServletResponse.addCookie(cookie);

        }else  {
            User user = new User();
            user.setName(token.getPrincipal().getAttributes().get("name").toString());
            user.setEmail(email);
            user.setRole(UserRole.USER);
            userRepository.save(user);
        }
        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }
}
