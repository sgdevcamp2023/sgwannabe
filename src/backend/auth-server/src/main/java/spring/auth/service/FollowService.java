package spring.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.auth.domain.FollowUser;
import spring.auth.repository.FollowRepository;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    public FollowUser findOneByEmail(String email) {
        return followRepository.findOneByEmail(email).block();
    }
}
