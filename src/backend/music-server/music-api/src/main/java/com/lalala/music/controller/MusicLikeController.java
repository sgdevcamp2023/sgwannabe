package com.lalala.music.controller;

import com.lalala.aop.AuthenticationContext;
import com.lalala.aop.PassportAuthentication;
import com.lalala.music.dto.CreateMusicRequestDTO;
import com.lalala.music.dto.MusicDTO;
import com.lalala.music.dto.MusicDetailDTO;
import com.lalala.music.dto.MusicRetrieveRequestDTO;
import com.lalala.music.dto.UpdateMusicRequestDTO;
import com.lalala.music.service.MusicLikeService;
import com.lalala.music.service.MusicService;
import com.lalala.music.service.MusicUploaderService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/musics")
public class MusicLikeController {
    private final MusicLikeService service;

    @PostMapping("/{musicId}/likes")
    @PassportAuthentication
    public ResponseEntity<BaseResponse<Boolean>> createLike(@PathVariable("musicId") Long musicId) {
        Long userId = AuthenticationContext.getUserInfo().id();
        boolean isLike = service.create(userId, musicId);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "좋아요를 눌렀습니다.", isLike));
    }
}
