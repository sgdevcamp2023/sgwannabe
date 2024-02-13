package com.sgwannabe.playlistserver.playlist.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.sgwannabe.playlistserver.music.domain.Music;
import com.sgwannabe.playlistserver.playlist.exception.DuplicateMusicException;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "playlist")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Playlist implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String name;
    private List<Music> musics;
    private String thumbnail;
    private String userName;
    private Long uid;
    private int totalMusicCount;

    @CreatedDate
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updatedAt;

    public void updateName(String name) {
        this.name = name;
    }

    public void updateTotalMusicCount() {
        this.totalMusicCount = musics.size();
    }

    public boolean isExistingMusic(Music music) {
        return musics.stream().anyMatch(existingMusic -> existingMusic.getId().equals(music.getId()));
    }

    public int getTotalMusicCount() {
        return musics.size();
    }
    public void addMusic(Music music) {
        if (isExistingMusic(music)) {
            musics.add(music);
            if (musics.size() == 1) {
                this.thumbnail = music.getThumbnail();
            }
        } else {
            throw new DuplicateMusicException("이미 추가된 음원입니다. musicId: " + music.getId());
        }
    }

    public void removeMusic(Long musicId) {
        musics.removeIf(music -> music.getId().equals(musicId));
        if (!musics.isEmpty()) {
            this.thumbnail = musics.get(0).getThumbnail();
        } else {
            // TODO 플레이리스트에 곡이 없는 경우 썸네일을 디폴트 이미지로 변경 or 논의 필요
            this.thumbnail = null;
            // this.thumbnail = "기본 썸네일 경로";
        }
    }

    public void changeMusicOrder(int fromIndex, int toIndex) {
        if (fromIndex >= 0 && fromIndex < musics.size() && toIndex >= 0 && toIndex < musics.size()) {
            Music musicToMove = musics.remove(fromIndex);
            musics.add(toIndex, musicToMove);
        } else {
            throw new IllegalArgumentException("잘못된 인덱스 범위 요청입니다.");
        }
    }

}