package com.sgwannabe.playlistserver.playlist.controller;

import com.sgwannabe.playlistserver.music.dto.MusicOrderChangeRequestDto;
import com.sgwannabe.playlistserver.music.dto.MusicRequestDto;
import com.sgwannabe.playlistserver.playlist.dto.PlaylistRequestDto;
import com.sgwannabe.playlistserver.playlist.dto.PlaylistResponseDto;
import com.sgwannabe.playlistserver.playlist.service.PlaylistService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Operation(summary = "새로운 플레이리스트 생성")
    @PostMapping
    public ResponseEntity<PlaylistResponseDto> createPlaylist(@Valid @RequestBody PlaylistRequestDto playlistRequestDto) {
        PlaylistResponseDto createdPlaylist = playlistService.createPlaylist(playlistRequestDto);
        return new ResponseEntity<>(createdPlaylist, HttpStatus.CREATED);
    }

    @Operation(summary = "ID로 플레이리스트 조회")
    @GetMapping("/{id}")
    public ResponseEntity<PlaylistResponseDto> getPlaylist(@PathVariable String id) {
        PlaylistResponseDto playlistResponseDto = playlistService.getPlaylistById(id);
        return new ResponseEntity<>(playlistResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "사용자 ID로 플레이리스트 목록 조회")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PlaylistResponseDto>> getPlaylistsByUserId(@PathVariable Long userId) {
        List<PlaylistResponseDto> playlistResponseDtos = playlistService.getPlaylistsByUserId(userId);
        return new ResponseEntity<>(playlistResponseDtos, HttpStatus.OK);
    }

    @Operation(summary = "ID로 플레이리스트 업데이트")
    @PutMapping("/{id}")
    public ResponseEntity<PlaylistResponseDto> updatePlaylist(
            @PathVariable String id,
            @Valid @RequestBody PlaylistRequestDto playlistRequestDto
    ) {
        PlaylistResponseDto updatedPlaylist = playlistService.updatePlayListById(id, playlistRequestDto);
        return new ResponseEntity<>(updatedPlaylist, HttpStatus.OK);
    }

    @Operation(summary = "ID로 플레이리스트 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable String id) {
        playlistService.deletePlaylistById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "ID로 플레이리스트에 음악 추가")
    @PostMapping("/{id}/add-music")
    public ResponseEntity<PlaylistResponseDto> addMusicToPlaylist(
            @PathVariable String id,
            @Valid @RequestBody MusicRequestDto musicRequestDto
    ) {
        PlaylistResponseDto updatedPlaylist = playlistService.addMusic(id, musicRequestDto);
        return new ResponseEntity<>(updatedPlaylist, HttpStatus.OK);
    }

    @Operation(summary = "ID로 플레이리스트에서 음악 삭제")
    @DeleteMapping("/{id}/remove-music/{musicOrder}")
    public ResponseEntity<PlaylistResponseDto> removeMusicFromPlaylist(
            @PathVariable String id,
            @PathVariable Long musicOrder
    ) {
        PlaylistResponseDto updatedPlaylist = playlistService.removeMusic(id, musicOrder);
        return new ResponseEntity<>(updatedPlaylist, HttpStatus.OK);
    }

    @Operation(summary = "ID로 플레이리스트 음악 순서 변경")
    @PostMapping("/{id}/change-music-order")
    public ResponseEntity<PlaylistResponseDto> changeMusicOrderInPlaylist(
            @PathVariable String id,
            @Valid @RequestBody MusicOrderChangeRequestDto musicOrderChangeRequestDto
    ) {
        PlaylistResponseDto updatedPlaylist = playlistService.changeMusicOrder(id, musicOrderChangeRequestDto);
        return new ResponseEntity<>(updatedPlaylist, HttpStatus.OK);
    }
}
