package com.lalala.music.controller;

import com.lalala.response.BaseResponse;
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

import com.lalala.music.dto.AlbumDTO;
import com.lalala.music.dto.AlbumDetailDTO;
import com.lalala.music.dto.CreateAlbumRequestDTO;
import com.lalala.music.dto.UpdateAlbumRequestDTO;
import com.lalala.music.service.AlbumService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/albums")
public class AlbumController {
    private final AlbumService service;

    @PostMapping
    public ResponseEntity<BaseResponse<AlbumDetailDTO>> createAlbum(
            @RequestBody CreateAlbumRequestDTO request) {
        AlbumDetailDTO album = service.createAlbum(request);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "앨범을 생성했습니다.", album));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<AlbumDTO>>> readAlbums(
            @RequestParam(required = false, defaultValue = "0", name = "page") int page,
            @RequestParam(required = false, defaultValue = "50", name = "size") int pageSize) {
        List<AlbumDTO> albums = service.getAlbums(page, pageSize);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "앨범 목록을 조회했습니다.", albums));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<AlbumDetailDTO>> readAlbum(@PathVariable("id") Long id) {
        AlbumDetailDTO album = service.getAlbum(id);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "앨범을 조회했습니다.", album));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<AlbumDetailDTO>> updateAlbum(
            @PathVariable("id") Long id, @RequestBody UpdateAlbumRequestDTO request) {
        AlbumDetailDTO album = service.updateAlbum(id, request);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "앨범을 수정했습니다.", album));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<AlbumDetailDTO>> deleteAlbum(@PathVariable("id") Long id) {
        AlbumDetailDTO album = service.deleteAlbum(id);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "앨범을 삭제했습니다.", album));
    }
}
