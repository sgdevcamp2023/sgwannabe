package com.lalala.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lalala.music.entity.ArtistEntity;

public interface ArtistRepository extends JpaRepository<ArtistEntity, Long> {}
