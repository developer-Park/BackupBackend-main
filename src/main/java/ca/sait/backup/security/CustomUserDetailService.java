package ca.sait.backup.security;

import ca.sait.backup.mapper.UserRepository;
import ca.sait.backup.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
//유저의 정보를 가져오는 UserDetailsService 인터페이스를 구현
//기본 오버라이드 메소드는 loadUserByUsername이고 반환타입은 UserDetails입니다.

public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;





    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
               if (user == null) {
                   throw new IllegalArgumentException("No user exist.");
               }
        return new CustomUserDetail(user);
    }
}




