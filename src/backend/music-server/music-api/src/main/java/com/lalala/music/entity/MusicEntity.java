package com.lalala.music.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
    Short playTime;

    @Column(nullable = false, columnDefinition = "TEXT")
    String lyrics;

    @Column(name = "album_id", nullable = false)
    Long albumId;

    @Column(name = "artist_id", nullable = false)
    Long artistId;

    @Column(name = "like_count", nullable = false, columnDefinition = "UNSIGNED INT default 0")
    Integer likeCount;

    @Embedded MusicFile file = new MusicFile();

    public MusicEntity(String title, Short playTime, String lyrics) {
        this.title = title;
        this.playTime = playTime;
        this.lyrics = lyrics;
    }

    public void update(String title, Short playTime, String lyrics) {
        this.title = title;
        this.playTime = playTime;
        this.lyrics = lyrics;
    }

    public void updateFile(MusicFile file) {
        this.file = file;
    }

    public void updateAlbum(Long albumId) {
        this.albumId = albumId;
    }

    public void updateArtist(Long artistId) {
        this.artistId = artistId;
    }

    public void increaseLikeCount() {
        this.likeCount += 1;
    }

    public void decreaseLikeCount() {
        this.likeCount -= 1;
    }
}
