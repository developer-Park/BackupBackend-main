package ca.sait.backup.security;

import ca.sait.backup.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
//UserDetails는 사용자의 세부사항을 스프링 시큐리티에 담는 곳 입니다.
//시큐리티에 사용자 정보를 저장하고 불러오기 위한 곳
//중요한 부분은 collection과 username부분 collection을 사용해 사용자의 권한은 파악하고, username은 계정의 고유한값을 넘겨 줄 수 있습니다.
public class CustomUserDetail implements UserDetails{

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(()-> "ROLE_"+user.getRole());
        return collectors;
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
