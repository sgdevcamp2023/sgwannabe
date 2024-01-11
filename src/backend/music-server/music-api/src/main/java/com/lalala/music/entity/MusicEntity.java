package com.lalala.music.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "musics")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 50, columnDefinition = "VARCHAR(50) default ''")
    String title;

    @Column(name = "play_time", nullable = false, columnDefinition = "SMALLINT UNSIGNED default 0")
    short playTime;

    @Column(nullable = false, columnDefinition = "TEXT")
    String lyrics;

    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    AlbumEntity album;

    @Embedded
    MusicFile file;

    @Embedded
    MusicParticipants participants;
}
