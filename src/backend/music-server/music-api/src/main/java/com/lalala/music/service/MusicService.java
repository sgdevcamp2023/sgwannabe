package com.lalala.music.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lalala.music.dto.CreateMusicRequestDTO;
import com.lalala.music.dto.MusicDTO;
import com.lalala.music.dto.MusicDetailDTO;
import com.lalala.music.dto.UpdateMusicRequestDTO;
import com.lalala.music.entity.AlbumEntity;
import com.lalala.music.entity.ArtistEntity;
import com.lalala.music.entity.MusicEntity;
import com.lalala.music.entity.MusicFile;
import com.lalala.music.repository.AlbumRepository;
import com.lalala.music.repository.ArtistRepository;
import com.lalala.music.repository.MusicRepository;
import com.lalala.music.util.AlbumUtils;
import com.lalala.music.util.ArtistUtils;
import com.lalala.music.util.MusicUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MusicService {
    private final MusicRepository repository;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    @Transactional
    public MusicDetailDTO createMusic(CreateMusicRequestDTO request) {
        AlbumEntity album = AlbumUtils.findById(request.getAlbumId(), albumRepository);
        ArtistEntity artist =
                ArtistUtils.findById(request.getParticipants().getArtistId(), artistRepository);

        final MusicEntity music =
                new MusicEntity(request.getTitle(), request.getPlayTime(), request.getLyrics());

        music.updateFile(
                new MusicFile(request.getFile().getFileUrl(), request.getFile().getFormatType()));

        music.updateAlbum(request.getAlbumId());
        music.updateArtist(request.getParticipants().getArtistId());

        repository.save(music);

        return MusicDetailDTO.from(music, artist, album);
    }

    public List<MusicDTO> getMusics(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Direction.DESC, "id"));
        List<MusicEntity> musics = repository.findAll(pageable).getContent();

        List<Long> artistIds = musics.stream().map(MusicEntity::getArtistId).toList();
        Map<Long, ArtistEntity> artistMap =
                artistRepository.findAllById(artistIds).stream()
                        .collect(Collectors.toMap(ArtistEntity::getId, Function.identity()));

        List<Long> albumIds = musics.stream().map(MusicEntity::getAlbumId).toList();
        Map<Long, AlbumEntity> albumMap =
                albumRepository.findAllById(albumIds).stream()
                        .collect(Collectors.toMap(AlbumEntity::getId, Function.identity()));

        return musics.stream()
                .map(
                        music ->
                                MusicDTO.from(
                                        music, albumMap.get(music.getAlbumId()), artistMap.get(music.getArtistId())))
                .toList();
    }

    public MusicDetailDTO getMusic(long id) {
        MusicEntity music = MusicUtils.findById(id, repository);
        AlbumEntity album = AlbumUtils.findById(music.getAlbumId(), albumRepository);
        ArtistEntity artist = ArtistUtils.findById(music.getArtistId(), artistRepository);

        return MusicDetailDTO.from(music, artist, album);
    }

    @Transactional
    public MusicDetailDTO updateMusic(Long id, UpdateMusicRequestDTO request) {
        AlbumEntity album = AlbumUtils.findById(request.getAlbumId(), albumRepository);
        ArtistEntity artist =
                ArtistUtils.findById(request.getParticipants().getArtistId(), artistRepository);
        MusicEntity music = MusicUtils.findById(id, repository);

        music.update(request.getTitle(), request.getPlayTime(), request.getLyrics());

        music.updateFile(
                new MusicFile(request.getFile().getFileUrl(), request.getFile().getFormatType()));

        music.updateAlbum(request.getAlbumId());
        music.updateArtist(request.getParticipants().getArtistId());

        repository.save(music);

        return MusicDetailDTO.from(music, artist, album);
    }

    @Transactional
    public MusicDetailDTO deleteMusic(Long id) {
        MusicEntity music = MusicUtils.findById(id, repository);
        AlbumEntity album = AlbumUtils.findById(music.getAlbumId(), albumRepository);
        ArtistEntity artist = ArtistUtils.findById(music.getArtistId(), artistRepository);

        repository.delete(music);

        return MusicDetailDTO.from(music, artist, album);
    }

    public List<MusicDTO> getMusicfromIds(List<Long> ids) {
        List<MusicEntity> musics = repository.findAllById(ids);

        List<Long> artistIds = musics.stream().map(MusicEntity::getArtistId).toList();
        Map<Long, ArtistEntity> artistMap =
                artistRepository.findAllById(artistIds).stream()
                        .collect(Collectors.toMap(ArtistEntity::getId, Function.identity()));

        List<Long> albumIds = musics.stream().map(MusicEntity::getAlbumId).toList();
        Map<Long, AlbumEntity> albumMap =
                albumRepository.findAllById(albumIds).stream()
                        .collect(Collectors.toMap(AlbumEntity::getId, Function.identity()));

        return musics.stream()
                .map(
                        music ->
                                MusicDTO.from(
                                        music, albumMap.get(music.getAlbumId()), artistMap.get(music.getArtistId())))
                .toList();
    }
}
