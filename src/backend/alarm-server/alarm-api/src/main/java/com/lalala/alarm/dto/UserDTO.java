package com.lalala.alarm.dto;

import com.lalala.alarm.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDTO {
    private final Long id;
    private final String nickname;
    private final String profile;

    public static UserDTO from(
            User user
    ) {
        return new UserDTO(
                user.getId(),
                user.getNickname(),
                user.getProfile()
        );
    }
}
