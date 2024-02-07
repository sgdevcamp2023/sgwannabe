package com.lalala.music.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
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

import com.lalala.music.dto.ArtistDTO;
import com.lalala.music.dto.ArtistDetailDTO;
import com.lalala.music.dto.CreateArtistRequestDTO;
import com.lalala.music.dto.SuccessResponse;
import com.lalala.music.dto.UpdateArtistRequestDTO;
import com.lalala.music.service.ArtistService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/artists")
public class ArtistController {
    private final ArtistService service;

    @PostMapping
    public ResponseEntity<SuccessResponse<ArtistDTO>> createArtist(
            @RequestBody CreateArtistRequestDTO request) {
        ArtistDTO artist = service.createArtist(request);
        return ResponseEntity.ok(SuccessResponse.from(HttpStatus.OK, "아티스트를 생성했습니다.", artist));
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<List<ArtistDTO>>> readArtists(
            @RequestParam(required = false, defaultValue = "0", name = "page") int page,
            @RequestParam(required = false, defaultValue = "50", name = "size") int pageSize) {
        List<ArtistDTO> artists = service.getArtists(page, pageSize);
        return ResponseEntity.ok(SuccessResponse.from(HttpStatus.OK, "아티스트 목록을 조회했습니다.", artists));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<ArtistDetailDTO>> readArtist(@PathVariable("id") Long id) {
        ArtistDetailDTO artist = service.getArtist(id);
        return ResponseEntity.ok(SuccessResponse.from(HttpStatus.OK, "아티스트를 조회했습니다.", artist));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<ArtistDTO>> updateArtist(
            @PathVariable("id") Long id, @RequestBody UpdateArtistRequestDTO request) {
        ArtistDTO artist = service.updateArtist(id, request);
        return ResponseEntity.ok(SuccessResponse.from(HttpStatus.OK, "아티스트를 수정했습니다.", artist));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<ArtistDTO>> deleteArtist(@PathVariable("id") Long id) {
        ArtistDTO artist = service.deleteArtist(id);
        return ResponseEntity.ok(SuccessResponse.from(HttpStatus.OK, "아티스트를 삭제했습니다.", artist));
    }
}
