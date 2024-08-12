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
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;
    private final PlaylistEventPublisher eventPublisher;

    @Operation(summary = "새로운 플레이리스트 생성")
    @PostMapping
    public ResponseEntity<PlaylistResponseDto> createPlaylist(@Valid @RequestBody PlaylistRequestDto playlistRequestDto) {
        PlaylistResponseDto createdPlaylist = playlistService.createPlaylist(playlistRequestDto);
        eventPublisher.publishPlaylistCreated(createdPlaylist.toPlaylist());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlaylist);
    }

    @Operation(summary = "ID로 플레이리스트 조회")
    @GetMapping("/{id}")
    public ResponseEntity<PlaylistResponseDto> getPlaylist(@PathVariable String id) {
        return ResponseEntity.ok(playlistService.getPlaylistById(id));
    }

    @Operation(summary = "사용자 ID로 플레이리스트 목록 조회")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PlaylistResponseDto>> getPlaylistsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(playlistService.getPlaylistsByUserId(userId));
    }

    @Operation(summary = "ID로 플레이리스트 업데이트")
    @PutMapping("/{id}")
    public ResponseEntity<PlaylistResponseDto> updatePlaylist(
            @PathVariable String id,
            @Valid @RequestBody PlaylistRequestDto playlistRequestDto
    ) {
        return ResponseEntity.ok(playlistService.updatePlayListById(id, playlistRequestDto));
    }

    @Operation(summary = "ID로 플레이리스트 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable String id) {
        playlistService.deletePlaylistById(id);
        eventPublisher.publishPlaylistDeleted(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "ID로 플레이리스트에 음악 추가")
    @PostMapping("/{id}/music")
    public ResponseEntity<PlaylistResponseDto> addMusicToPlaylist(
            @PathVariable String id,
            @Valid @RequestBody MusicRequestDto musicRequestDto
    ) {
        return ResponseEntity.ok(playlistService.addMusic(id, musicRequestDto));
    }

    @Operation(summary = "ID로 플레이리스트에서 음악 삭제")
    @DeleteMapping("/{id}/music/{musicId}")
    public ResponseEntity<PlaylistResponseDto> removeMusicFromPlaylist(
            @PathVariable String id,
            @PathVariable Long musicId
    ) {
        return ResponseEntity.ok(playlistService.removeMusic(id, musicId));
    }

    @Operation(summary = "ID로 플레이리스트 음악 순서 변경")
    @PutMapping("/{id}/music/order")
    public ResponseEntity<PlaylistResponseDto> changeMusicOrderInPlaylist(
            @PathVariable String id,
            @Valid @RequestBody MusicOrderChangeRequestDto musicOrderChangeRequestDto
    ) {
        return ResponseEntity.ok(playlistService.changeMusicOrder(id, musicOrderChangeRequestDto));
    }
}