package com.lalala.music.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Embeddable
public class MusicParticipants {
    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    ArtistEntity artist;

    @OneToMany(mappedBy = "music")
    Set<MusicComposerEntity> composers = new HashSet<>();

    @OneToMany(mappedBy = "music")
    Set<MusicComposerEntity> lyricists = new HashSet<>();

    @OneToMany(mappedBy = "music")
    Set<MusicComposerEntity> arrangers = new HashSet<>();
}
