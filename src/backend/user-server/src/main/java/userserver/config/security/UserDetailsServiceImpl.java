package userserver.config.security;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import userserver.domain.User;
import userserver.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user =
                userRepository
                        .findById(Long.valueOf(id))
                        .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다"));
        return new UserDetailsImpl(user);
    }
}
