package com.lalala.music.controller;

import com.lalala.music.dto.MusicRetrieveRequestDTO;
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
import org.springframework.web.multipart.MultipartFile;

import com.lalala.music.dto.CreateMusicRequestDTO;
import com.lalala.music.dto.MusicDTO;
import com.lalala.music.dto.MusicDetailDTO;
import com.lalala.music.dto.UpdateMusicRequestDTO;
import com.lalala.music.service.MusicService;
import com.lalala.music.service.MusicUploaderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/musics")
public class MusicController {
    private final MusicService service;
    private final MusicUploaderService uploaderService;

    @PostMapping
    public ResponseEntity<BaseResponse<MusicDetailDTO>> createMusic(
            @RequestBody CreateMusicRequestDTO request) {
        MusicDetailDTO music = service.createMusic(request);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "음원을 생성했습니다.", music));
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<BaseResponse<MusicDetailDTO>> uploadMusic(
            @RequestParam(value = "upload") MultipartFile file) {
        MusicDetailDTO music = uploaderService.upload(file);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "음원을 업로드했습니다.", music));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<MusicDTO>>> readMusics(
            @RequestParam(required = false, defaultValue = "0", name = "page") int page,
            @RequestParam(required = false, defaultValue = "50", name = "size") int pageSize) {
        List<MusicDTO> musics = service.getMusics(page, pageSize);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "음원을 조회했습니다.", musics));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<MusicDetailDTO>> readMusic(@PathVariable("id") Long id) {
        MusicDetailDTO music = service.getMusic(id);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "음원을 조회했습니다.", music));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<MusicDetailDTO>> updateMusic(
            @PathVariable("id") Long id, @RequestBody UpdateMusicRequestDTO request) {
        MusicDetailDTO music = service.updateMusic(id, request);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "음원을 업로드했습니다.", music));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<MusicDetailDTO>> deleteMusic(@PathVariable("id") Long id) {
        MusicDetailDTO music = service.deleteMusic(id);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "음원을 업로드했습니다.", music));
    }

    @PostMapping("/retrieve")
    public BaseResponse<List<MusicDTO>> getMusicFromIds(@RequestBody MusicRetrieveRequestDTO request) {
        List<MusicDTO> musics = service.getMusicfromIds(request.getIds());
        return BaseResponse.from(HttpStatus.OK.value(), "음원을 조회했습니다.", musics);
    }
}
