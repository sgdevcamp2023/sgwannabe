package spring.auth.config.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import spring.auth.domain.User;

import java.util.Collection;
import java.util.List;

//@Getter
public class UserDetailsImpl implements UserDetails {
//    @Serial
//    private static final long serialVersionUID = 1L;

    private static User user;

    public User getUser() {
        return user;
    }

    public UserDetailsImpl(User user) {
        UserDetailsImpl.user = user;
    }

    /**
     * 유저의 권한 목록
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().toString())); // 하나의 권한만을 반환
    }

    @Override
    public String getPassword() {
        return this.getUser().getPassword();
    }

    @Override
    public String getUsername() {
        return this.getUser().getUsername();
    }

    public String getEmail() {
        return this.getUser().getEmail();
    }
    /**
     * 계정 만료 여부
     * true : 만료 안됨
     * false : 만료
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정 잠김 여부
     * true : 잠기지 않음
     * false : 잠김
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    /**
     * 비밀번호 만료 여부
     * true : 만료 안됨
     * false : 만료
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 사용자 활성화 여부
     * ture : 활성화
     * false : 비활성화
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
