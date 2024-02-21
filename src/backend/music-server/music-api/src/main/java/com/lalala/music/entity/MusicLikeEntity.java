package com.lalala.music.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "music_likes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicLikeEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "user_id", nullable = false)
    Long userId;

    @Column(name = "music_id", nullable = false)
    Long musicId;

    public MusicLikeEntity(Long userId, Long musicId) {
        this.userId = userId;
        this.musicId = musicId;
    }

    public void updateUser(Long userId) {
        this.userId = userId;
    }

    public void updateMusic(Long musicId) {
        this.musicId = musicId;
    }
}