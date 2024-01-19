package com.lalala.music.controller;

import com.lalala.music.dto.ArtistDTO;
import com.lalala.music.dto.ArtistDetailDTO;
import com.lalala.music.dto.CreateArtistRequestDTO;
import com.lalala.music.dto.UpdateArtistRequestDTO;
import com.lalala.music.service.ArtistService;
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
@RequestMapping("/api/v1/artists")
public class ArtistController {
    private final ArtistService service;

    @PostMapping
    public ResponseEntity<ArtistDTO> createArtist(@RequestBody CreateArtistRequestDTO request) {
        ArtistDTO artist = service.createArtist(request);
        return ResponseEntity.ok(artist);
    }

    @GetMapping
    public ResponseEntity<List<ArtistDTO>> readArtists(
            @RequestParam(required = false, defaultValue = "0", name = "page") int page,
            @RequestParam(required = false, defaultValue = "50", name = "size") int pageSize
    ) {
        List<ArtistDTO> artists = service.getArtists(page, pageSize);
        return ResponseEntity.ok(artists);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistDetailDTO> readArtist(@PathVariable("id") Long id) {
        ArtistDetailDTO artist = service.getArtist(id);
        return ResponseEntity.ok(artist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistDTO> updateArtist(
            @PathVariable("id") Long id,
            @RequestBody UpdateArtistRequestDTO request
    ) {
        ArtistDTO artist = service.updateArtist(id, request);
        return ResponseEntity.ok(artist);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ArtistDTO> deleteArtist(@PathVariable("id") Long id) {
        ArtistDTO artist = service.deleteArtist(id);
        return ResponseEntity.ok(artist);
    }
}
