package com.lalala.music.controller;

import com.lalala.music.domain.Artist;
import com.lalala.music.domain.ArtistDetail;
import com.lalala.response.BaseResponse;

import com.lalala.music.domain.*;

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

import com.lalala.music.dto.CreateArtistRequestDTO;
import com.lalala.music.dto.UpdateArtistRequestDTO;
import com.lalala.music.service.ArtistService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/artists")
public class ArtistController {
    private final ArtistService service;

    @PostMapping
    public ResponseEntity<BaseResponse<Artist>> createArtist(
            @RequestBody CreateArtistRequestDTO request) {
        Artist artist = service.createArtist(request);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "아티스트를 생성했습니다.", artist));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<Artist>>> readArtists(
            @RequestParam(required = false, defaultValue = "0", name = "page") int page,
            @RequestParam(required = false, defaultValue = "50", name = "size") int pageSize) {
        List<Artist> artists = service.getArtists(page, pageSize);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "아티스트 목록을 조회했습니다.", artists));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ArtistDetail>> readArtist(@PathVariable("id") Long id) {
        ArtistDetail artist = service.getArtist(id);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "아티스트를 조회했습니다.", artist));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Artist>> updateArtist(
            @PathVariable("id") Long id, @RequestBody UpdateArtistRequestDTO request) {
        Artist artist = service.updateArtist(id, request);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "아티스트를 수정했습니다.", artist));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Artist>> deleteArtist(@PathVariable("id") Long id) {
        Artist artist = service.deleteArtist(id);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "아티스트를 삭제했습니다.", artist));
    }
}
