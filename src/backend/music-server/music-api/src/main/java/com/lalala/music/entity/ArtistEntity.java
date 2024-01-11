package com.lalala.music.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "artists")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArtistEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany(mappedBy = "artist")
    Set<MusicComposerEntity> composedMusics = new HashSet<>();

    @OneToMany(mappedBy = "artist")
    Set<MusicComposerEntity> writtenMusics = new HashSet<>();

    @OneToMany(mappedBy = "artist")
    Set<MusicComposerEntity> arrangedMusics = new HashSet<>();
}
