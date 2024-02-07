package com.lalala.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lalala.music.entity.AlbumEntity;

public interface AlbumRepository extends JpaRepository<AlbumEntity, Long> {}
