package com.lalala.music.repository;

import com.lalala.music.entity.ArtistEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<ArtistEntity, Long> {
}

