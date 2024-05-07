package com.lalala.music.controller;

import com.lalala.music.domain.*;
import com.lalala.music.dto.*;
import com.lalala.music.service.*;

import com.lalala.response.BaseResponse;

import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/musics")
public class MusicController {
    private final MusicService service;
    private final MusicUploaderService uploaderService;

    @PostMapping
    public ResponseEntity<BaseResponse<MusicDetail>> createMusic(
            @RequestBody CreateMusicRequestDTO request) {
        MusicDetail music = service.createMusic(request);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "음원을 생성했습니다.", music));
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<BaseResponse<MusicDetail>> uploadMusic(
            @RequestParam(value = "upload") MultipartFile file) {
        MusicDetail music = uploaderService.upload(file);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "음원을 업로드했습니다.", music));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<Music>>> readMusics(
            @RequestParam(required = false, defaultValue = "0", name = "page") int page,
            @RequestParam(required = false, defaultValue = "50", name = "size") int pageSize) {
        List<Music> musics = service.getMusics(page, pageSize);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "음원을 조회했습니다.", musics));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<MusicDetail>> readMusic(@PathVariable("id") Long id) {
        MusicDetail music = service.getMusic(id);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "음원을 업로드했습니다.", music));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<MusicDetail>> updateMusic(
            @PathVariable("id") Long id, @RequestBody UpdateMusicRequestDTO request) {
        MusicDetail music = service.updateMusic(id, request);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "음원을 업로드했습니다.", music));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<MusicDetail>> deleteMusic(@PathVariable("id") Long id) {
        MusicDetail music = service.deleteMusic(id);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "음원을 업로드했습니다.", music));
    }

    @PostMapping("/retrieve")
    public BaseResponse<List<Music>> getMusicFromIds(@RequestBody MusicRetrieveRequestDTO request) {
        List<Music> musics = service.getMusicfromIds(request.getIds());
        return BaseResponse.from(HttpStatus.OK.value(), "음원을 조회했습니다.", musics);
    }
}
