package ca.sait.backup.security;

import ca.sait.backup.oauth2.GoogleOAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@EnableWebSecurity
//@RequiredArgsConstructor
////WebSecurityConfigurerAdapter 사용 하지 않고 구현
////다른 곳 보다 SecurityConfig 쪽만 수정 해주면 된다.
////기존은 Override방식으로 시큐리티를 configure하였지만
////현재는 Bean방식으로 스프링에 DI를 해주는 방식으로 사용한다.
//public class SecurityConfig {
//
//    private final CustomUserDetailService customUserDetailService;
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    private final GoogleOAuth2SuccessHandler googleOAuth2SuccessHandler;
//
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/mediator/**").hasAnyRole("MEDIATOR", "ADMIN")
//                .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/general/login")
//                .permitAll()
//                .usernameParameter("email")
//                .passwordParameter("password")
//                .and()
//                .oauth2Login()
//                .successHandler(googleOAuth2SuccessHandler)
//                .and()
//                .exceptionHandling()
//                .and()
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .csrf().disable();
//        http.headers().frameOptions().disable();
//
////                .and()
////                .logout()
////                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
////                .logoutSuccessUrl("/login")
////                .deleteCookies()
////                .invalidateHttpSession(true)
////                .deleteCookies("JSESSIONID")
//
//        return http.build();
//    }
//
//    //Pass the Authentication
//    public InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> inMemoryAuthentication() throws Exception {
//        return (InMemoryUserDetailsManagerConfigurer)this.apply(new InMemoryUserDetailsManagerConfigurer());
//    }
//
//    //this is for Usercontroller AuthenticationManager
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
//            throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    //this is for password hash encode
//    @Bean
//    public PasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public WebSecurityCustomizer ignoringCustomizer() {
//        return (web) -> web.ignoring().mvcMatchers(
//                "/resources/**", "/static/**", "/static/landing-assets/**", "/static/images/**"
//                , "/static/custom-scripts/**", "/static/custom-assets/**", "/static/assets/**", "/static/app-assets/**"
//                , "/general/**", "/api/v1/pri/**", "/api/v1/**", "/oauth2/**", "/**", "/js", "/css"
//        );
//    }
//
//}


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailService customUserDetailService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final GoogleOAuth2SuccessHandler googleOAuth2SuccessHandler;
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","/general/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/mediator/**").hasAnyRole("MEDIATOR","ADMIN")
                .antMatchers("/user/**").hasAnyRole("ADMIN","USER")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/general/login")
                .permitAll()
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .oauth2Login()
                .successHandler(googleOAuth2SuccessHandler)
//                .and()
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/login")
//                .deleteCookies()
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.headers().frameOptions().disable();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/static/**", "/landing-assets/**", "/images/**"
                , "/custom-scripts/**", "/custom-assets/**","/assets/**","/app-assets/**"
                ,"/templates/**", "/general/**","/api/v1/pri/**");
    }


}