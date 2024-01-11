package com.lalala.music.entity;

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
@Table(name = "music_composers")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicComposerEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "music_id")
    MusicEntity music;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    ArtistEntity artist;
}