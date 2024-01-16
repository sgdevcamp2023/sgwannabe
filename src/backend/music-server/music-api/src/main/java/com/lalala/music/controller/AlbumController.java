package com.lalala.music.controller;

import com.lalala.music.dto.AlbumDTO;
import com.lalala.music.dto.AlbumDetailDTO;
import com.lalala.music.dto.CreateAlbumRequestDTO;
import com.lalala.music.dto.UpdateAlbumRequestDTO;
import com.lalala.music.service.AlbumService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/albums")
public class AlbumController {
    private final AlbumService service;

    @PostMapping
    public ResponseEntity<AlbumDetailDTO> createAlbum(@RequestBody CreateAlbumRequestDTO request) {
        AlbumDetailDTO album = service.createAlbum(request);
        return ResponseEntity.ok(album);
    }

    @GetMapping
    public ResponseEntity<List<AlbumDTO>> readAlbums(
            @RequestParam(required = false, defaultValue = "0", name = "page") int page,
            @RequestParam(required = false, defaultValue = "50", name = "size") int pageSize
    ) {
        List<AlbumDTO> albums = service.getAlbums(page, pageSize);
        return ResponseEntity.ok(albums);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumDetailDTO> readAlbum(@PathVariable("id") Long id) {
        AlbumDetailDTO album = service.getAlbum(id);
        return ResponseEntity.ok(album);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumDetailDTO> updateAlbum(
            @PathVariable("id") Long id,
            @RequestBody UpdateAlbumRequestDTO request
        ) {
        AlbumDetailDTO album = service.updateAlbum(id, request);
        return ResponseEntity.ok(album);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AlbumDetailDTO> deleteAlbum(@PathVariable("id") Long id) {
        AlbumDetailDTO album = service.deleteAlbum(id);
        return ResponseEntity.ok(album);
    }
}
