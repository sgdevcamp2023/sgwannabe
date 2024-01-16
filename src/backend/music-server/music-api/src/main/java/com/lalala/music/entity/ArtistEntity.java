package com.lalala.music.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(nullable = false, length = 50, columnDefinition = "CHAR(50) default ''")
    String name;

    @Column(nullable = false, length = 6, columnDefinition = "CHAR(6) default 'MALE'")
    @Enumerated(EnumType.STRING)
    GenderType gender;

    @Column(nullable = false, length = 5, columnDefinition = "CHAR(5) default 'SOLO'")
    @Enumerated(EnumType.STRING)
    ArtistType type;

    @Column(nullable = false, length = 50, columnDefinition = "CHAR(50) default ''")
    String agency;

    public ArtistEntity(
            String name,
            GenderType gender,
            ArtistType type,
            String agency
    ) {
        this.name = name;
        this.gender = gender;
        this.type = type;
        this.agency = agency;
    }

    public void update(
            String name,
            GenderType gender,
            ArtistType type,
            String agency
    ) {
        this.name = name;
        this.gender = gender;
        this.type = type;
        this.agency = agency;
    }
}
