package com.lalala.music.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "albums")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlbumEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 7, columnDefinition = "CHAR(7) default 'SINGLE'")
    @Enumerated(EnumType.STRING)
    AlbumType type;

    @Column(nullable = false, length = 50, columnDefinition = "VARCHAR(50) default ''")
    String title;

    @JoinColumn(name = "artist_id", nullable = false)
    @ManyToOne
    ArtistEntity artist;

    @Column(name = "released_at", nullable = false, columnDefinition = "DATETIME default NOW()")
    LocalDateTime releasedAt;

    @OneToMany(mappedBy = "album")
    List<MusicEntity> tracks = new ArrayList<>();
}
