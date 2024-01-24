package com.lalala.alarm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String profile;

    public User(
            Long id,
            String nickname,
            String profile
    ) {
        this.id = id;
        this.nickname = nickname;
        this.profile = profile;
    }

    public void update(
            String nickname,
            String profile
    ) {
        this.nickname = nickname;
        this.profile = profile;
    }
}
