package com.sgwannabe.playlistserver.playlist.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;  // 추가
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "playlistdb")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Playlist implements Serializable {  // Serializable 인터페이스 추가

    @Serial
    private static final long serialVersionUID = 1L;  // 추가

    @Id
    private String id;
    private String name;
    private String songs;
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
}
