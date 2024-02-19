package com.sgwannabe.playlistserver.playlist.service;

import com.lalala.exception.BusinessException;
import com.lalala.exception.ErrorCode;
import com.lalala.response.BaseResponse;
import com.sgwannabe.playlistserver.external.feign.FeignMusicClient;
import com.sgwannabe.playlistserver.external.feign.dto.MusicDTO;
import com.sgwannabe.playlistserver.external.feign.dto.MusicRetrieveRequestDTO;
import com.sgwannabe.playlistserver.music.domain.Music;
import com.sgwannabe.playlistserver.music.dto.MusicOrderChangeRequestDto;
import com.sgwannabe.playlistserver.music.dto.MusicRequestDto;
import com.sgwannabe.playlistserver.playlist.domain.Playlist;
import com.sgwannabe.playlistserver.playlist.dto.PlaylistRequestDto;
import com.sgwannabe.playlistserver.playlist.dto.PlaylistResponseDto;
import com.sgwannabe.playlistserver.playlist.util.PlaylistToDtoConverter;
import com.sgwannabe.playlistserver.playlist.repository.PlaylistRepository;
import com.sgwannabe.playlistserver.playlist.util.KeyGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final RedisTemplate<String, Playlist> redisTemplate;
    private final PlaylistToDtoConverter converter;
    private final TransactionalService transactionalService;

    private final FeignMusicClient musicClient;


    public PlaylistResponseDto createPlaylist(PlaylistRequestDto playlistRequestDto) {

        BaseResponse<List<MusicDTO>> response = musicClient.getMusicFromIds(
                new MusicRetrieveRequestDTO(playlistRequestDto.getMusics())
        );

        List<Music> musics = response.getData().stream().map(musicDTO ->
                Music.builder()
                    .id(musicDTO.getId())
                    .title(musicDTO.getTitle())
                    .artistId(musicDTO.getArtist().getId())
                    .artist(musicDTO.getArtist().getName())
                    .albumId(musicDTO.getAlbum().getId())
                    .album(musicDTO.getAlbum().getTitle())
                    .thumbnail(musicDTO.getAlbum().getCoverUrl())
                    .playtime(getPlayTime(musicDTO.getPlayTime()))
                    .build()
        ).toList();

        Playlist playlist = Playlist.builder()
                .uid(playlistRequestDto.getUid())
                .userName(playlistRequestDto.getUserName())
                .name(playlistRequestDto.getName())
                .musics(musics)
                .thumbnail(playlistRequestDto.getThumbnail())
                .build();

        playlist.updateTotalMusicCount();

        log.info("playlist={}", playlist);
        Playlist saved = playlistRepository.save(playlist);

        log.info("savedPlaylist={}", saved);
        transactionalService.updateCacheAsync(saved);
        return converter.convert(saved);
    }

    private String getPlayTime(Short playTime) {
        int min = playTime / 60;
        int sec = playTime % 60;
        return String.format("%02d:%02d", min, sec);
    }

    public PlaylistResponseDto getPlaylistById(String id) {
        String key = KeyGenerator.playlistKeyGenerate(id);
        Playlist cachedPlaylist = redisTemplate.opsForValue().get(key);
        if (cachedPlaylist != null) {
            return converter.convert(cachedPlaylist);
        }

        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYLIST_NOT_FOUND));
        transactionalService.updateCacheAsync(playlist);
        return converter.convert(playlist);
    }

    public List<PlaylistResponseDto> getPlaylistsByUserId(Long userId) {
        List<Playlist> playlists = playlistRepository.findByUid(userId);
        return playlists.stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    @Transactional
    public PlaylistResponseDto updatePlayListById(String id, PlaylistRequestDto playlistRequestDto) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYLIST_NOT_FOUND));

        playlist.updateName(playlistRequestDto.getName());

        Playlist saved = playlistRepository.save(playlist);
        transactionalService.updateCacheAsync(saved);

        return converter.convert(playlist);
    }

    @Transactional
    public void deletePlaylistById(String id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYLIST_NOT_FOUND));

        playlistRepository.deleteById(id);
        deleteCache(playlist);
    }

    private void updateCache(Playlist playlist) {
        String key = KeyGenerator.playlistKeyGenerate(playlist.getId());
        redisTemplate.opsForValue().set(key, playlist, Duration.ofMinutes(10));
    }

    private void deleteCache(Playlist playlist) {
        String key = KeyGenerator.playlistKeyGenerate(playlist.getId());
        redisTemplate.delete(key);
    }

    @Transactional
    public PlaylistResponseDto addMusic(String playlistId, MusicRequestDto musicRequestDto) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYLIST_NOT_FOUND));

        Music music = Music.builder()
                .title(musicRequestDto.getTitle())
                .artistId(musicRequestDto.getArtistId())
                .artist(musicRequestDto.getArtist())
                .albumId(musicRequestDto.getAlbumId())
                .album(musicRequestDto.getAlbum())
                .thumbnail(musicRequestDto.getThumbnail())
                .playtime(musicRequestDto.getPlaytime())
                .build();

        playlist.addMusic(music);
        playlist.updateTotalMusicCount();

        playlistRepository.save(playlist);
        updateCache(playlist);

        return converter.convert(playlist);
    }

    @Transactional
    public PlaylistResponseDto removeMusic(String playlistId, Long musicId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYLIST_NOT_FOUND));

        playlist.removeMusic(musicId);
        playlist.updateTotalMusicCount();

        playlistRepository.save(playlist);
        updateCache(playlist);

        return converter.convert(playlist);
    }

    @Transactional
    public PlaylistResponseDto changeMusicOrder(String playlistId, MusicOrderChangeRequestDto musicOrderChangeRequestDto) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYLIST_NOT_FOUND));

        playlist.changeMusicOrder(musicOrderChangeRequestDto.getFromIndex(), musicOrderChangeRequestDto.getToIndex());

        playlistRepository.save(playlist);
        updateCache(playlist);

        return converter.convert(playlist);
    }

}
