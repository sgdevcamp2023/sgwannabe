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
@Table(name = "music_arrangers")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicArrangerEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "music_id", nullable = false)
    Long musicId;

    @Column(name = "artist_id", nullable = false)
    Long artistId;

    public MusicArrangerEntity(Long musicId, Long artistId) {
        this.musicId = musicId;
        this.artistId = artistId;
    }
}
