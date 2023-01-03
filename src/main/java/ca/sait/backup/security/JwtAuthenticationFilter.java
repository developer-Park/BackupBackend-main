package ca.sait.backup.security;

import ca.sait.backup.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
//컴포넌트로 구성되고, do filter를 OncePerRequestFilter가 구현하고, 구현된 것을 상속받아 클래스로 만든다.
//dofilter
//1. 모든 요청은 FilterChainProxy를 거치며 그 안에는 doFilter를 거치게 된다.
//2. doFilter는 doFilterInternal를 호출한다.
//
//doFilterInternal
//1. GetFilters를 호출해 요청을 처리할 Filter들을 불러온다.
//2. 적절한 Filter를 찾지 못하면 종료된다.
//3. 찾은 필터들을 VirtualFilterChain에게 위임한다.
//내가 이곳에서 dofilterinternal을 사용한 것은 첫번째는 HttpServletRequest를 내가 더 잘 다루기 떄문이다
//doFilter를 사용하여 구현해보았지만, 캐스트되는과정에서 firewall이라는 것에 갇혀 token을 가져오는것이 잘 되지 않았다
//그래서 doFilterInternal을 구현해 만들어보니 잘 만들어졌다.
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final CustomUserDetailService userDetailsService;
    private final JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            //토큰을 쿠키에서 가져와 token변수에 저장하는 과정.
            String token = Arrays.stream(request.getCookies())
                    .filter(c -> c.getName().equals("session"))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
            System.out.println("토큰"+token);

            //토큰의 validation check을 하는 곳.
            if (token != null && jwtUtils.validateJwtToken(token)) {
                //token의 정보를 가져오는 곳
                String username = jwtUtils.getUserNameFromJwtToken(token);
                System.out.println("사용자이름"+username);
                //UserDetails에 정보를 넘겨줘 해당 User를 저장한다.
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                //username과 password를 조합해서 UsernamePasswordAuthenticationToken 인스턴스를 만듭니다.
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                //유저 권한에 들어갈 것들을 만드는 과정.
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //authentication을 SecurityContextHolder.getContext().setAuthentication(...)를 set 해줍니다.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
        //do filter에 request와,response저장
        filterChain.doFilter(request, response);
    }
}