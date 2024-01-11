package com.lalala.music.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @Column(name = "play_time", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
    int playTime;

    @Column(nullable = false, columnDefinition = "TEXT default ''")
    String lyrics;

    @ManyToOne
    @JoinColumn(name = "album_id")
    AlbumEntity album;

    @Embedded
    MusicFile file;

    @Embedded
    MusicParticipants participants;
}
