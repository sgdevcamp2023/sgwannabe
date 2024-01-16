package com.lalala.music.repository;

import com.lalala.music.entity.AlbumEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<AlbumEntity, Long> {
}
