package com.sgwannabe.playlistserver.playlist.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.sgwannabe.playlistserver.music.domain.Music;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;  // 추가
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "playlistdb")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    public void addMusic(Music music) {
        this.musics.add(music);
    }
    public void removeMusic(Long musicId) {
        this.musics.removeIf(music -> music.getId().equals(musicId));
    }
    public void changeMusicOrder(int fromIndex, int toIndex) {
        if (fromIndex >= 0 && fromIndex < musics.size() && toIndex >= 0 && toIndex < musics.size()) {
            Music musicToMove = musics.remove(fromIndex);
            musics.add(toIndex, musicToMove);
        }
    }

}
