package com.sgwannabe.playlistserver.playlist.controller;

import com.sgwannabe.playlistserver.music.dto.MusicOrderChangeRequestDto;
import com.sgwannabe.playlistserver.music.dto.MusicRequestDto;
import com.sgwannabe.playlistserver.playlist.dto.PlaylistRequestDto;
import com.sgwannabe.playlistserver.playlist.dto.PlaylistResponseDto;
import com.sgwannabe.playlistserver.playlist.exception.NotFoundException;
import com.sgwannabe.playlistserver.playlist.service.PlaylistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/api/playlists")
@RequiredArgsConstructor
@Slf4j
public class PlaylistController {

    private final PlaylistService playlistService;

    @PostMapping
    public ResponseEntity<PlaylistResponseDto> createPlaylist(@Valid @RequestBody PlaylistRequestDto playlistRequestDto) {
        log.info("post mapping C playlist request dto={}", playlistRequestDto.toString());
        PlaylistResponseDto createdPlaylist = playlistService.createPlaylist(playlistRequestDto);
        return new ResponseEntity<>(createdPlaylist, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistResponseDto> getPlaylist(@PathVariable String id) {
        PlaylistResponseDto playlistResponseDto = playlistService.getPlaylistById(id);
        return new ResponseEntity<>(playlistResponseDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistResponseDto> updatePlaylist(@Valid @PathVariable String id, @RequestBody PlaylistRequestDto playlistRequestDto) {
        PlaylistResponseDto updatedPlaylist = playlistService.updatePlayListById(id, playlistRequestDto);
        return new ResponseEntity<>(updatedPlaylist, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable String id) {
        playlistService.deletePlaylistById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/add-music")
    public ResponseEntity<PlaylistResponseDto> addMusicToPlaylist(@Valid @PathVariable String id, @RequestBody MusicRequestDto musicRequestDto) {

        PlaylistResponseDto updatedPlaylist = playlistService.addMusic(id, musicRequestDto);
        return new ResponseEntity<>(updatedPlaylist, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/remove-music/{musicOrder}")
    public ResponseEntity<PlaylistResponseDto> removeMusicFromPlaylist(@PathVariable String id, @PathVariable Long musicOrder) {

        PlaylistResponseDto updatedPlaylist = playlistService.removeMusic(id, musicOrder);
        return new ResponseEntity<>(updatedPlaylist, HttpStatus.OK);
    }

    @PostMapping("/{id}/change-music-order")
    public ResponseEntity<PlaylistResponseDto> changeMusicOrderInPlaylist(
            @PathVariable String id,
            @Valid @RequestBody MusicOrderChangeRequestDto musicOrderChangeRequestDto
    ) {
        PlaylistResponseDto updatedPlaylist = playlistService.changeMusicOrder(id, musicOrderChangeRequestDto);
        return new ResponseEntity<>(updatedPlaylist, HttpStatus.OK);

    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}