package com.sgwannabe.playlistserver.playlist.controller;

import com.sgwannabe.playlistserver.playlist.dto.PlaylistRequestDto;
import com.sgwannabe.playlistserver.playlist.dto.PlaylistResponseDto;
import com.sgwannabe.playlistserver.playlist.exception.NotFoundException;
import com.sgwannabe.playlistserver.playlist.service.PlaylistService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @PostMapping
    public ResponseEntity<PlaylistResponseDto> createPlaylist(@RequestBody PlaylistRequestDto playlistRequestDto) {
        PlaylistResponseDto createdPlaylist = playlistService.createPlaylist(playlistRequestDto);
        return new ResponseEntity<>(createdPlaylist, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistResponseDto> getPlaylist(@PathVariable String id) {
            PlaylistResponseDto playlistResponseDto = playlistService.getPlaylistById(id);
            return new ResponseEntity<>(playlistResponseDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistResponseDto> updatePlaylist(@PathVariable String id, @RequestBody PlaylistRequestDto playlistRequestDto) {
            PlaylistResponseDto updatedPlaylist = playlistService.updatePlayListById(id, playlistRequestDto);
            return new ResponseEntity<>(updatedPlaylist, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable String id) {
            playlistService.deletePlaylistById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}

