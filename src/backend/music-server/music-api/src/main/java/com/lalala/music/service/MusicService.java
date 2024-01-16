package com.lalala.music.service;

import com.lalala.music.dto.CreateMusicRequestDTO;
import com.lalala.music.dto.MusicDTO;
import com.lalala.music.dto.MusicDetailDTO;
import com.lalala.music.dto.UpdateMusicRequestDTO;
import com.lalala.music.entity.AlbumEntity;
import com.lalala.music.entity.ArtistEntity;
import com.lalala.music.entity.MusicArrangerEntity;
import com.lalala.music.entity.MusicComposerEntity;
import com.lalala.music.entity.MusicEntity;
import com.lalala.music.entity.MusicFile;
import com.lalala.music.entity.MusicLyricistEntity;
import com.lalala.music.repository.AlbumRepository;
import com.lalala.music.repository.ArtistRepository;
import com.lalala.music.repository.MusicArrangerRepository;
import com.lalala.music.repository.MusicComposerRepository;
import com.lalala.music.repository.MusicLyricistRepository;
import com.lalala.music.repository.MusicRepository;
import com.lalala.music.util.AlbumUtils;
import com.lalala.music.util.ArtistUtils;
import com.lalala.music.util.MusicUtils;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MusicService {
    private final MusicRepository repository;
    private final MusicComposerRepository composerRepository;
    private final MusicLyricistRepository lyricistRepository;
    private final MusicArrangerRepository arrangerRepository;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    @Transactional
    public MusicDetailDTO createMusic(CreateMusicRequestDTO request) {
        AlbumEntity album = AlbumUtils.findById(request.getAlbumId(), albumRepository);
        ArtistEntity artist = ArtistUtils.findById(request.getParticipants().getArtistId(), artistRepository);

        final MusicEntity music = new MusicEntity(
                request.getTitle(),
                request.getPlayTime(),
                request.getLyrics()
        );

        music.updateFile(new MusicFile(
                request.getFile().getFileUrl(),
                request.getFile().getFormatType()
        ));

        music.updateAlbum(request.getAlbumId());
        music.updateArtist(request.getParticipants().getArtistId());

        repository.save(music);

        Set<ArtistEntity> composers = new HashSet<>(
                artistRepository.findAllById(request.getParticipants().getComposerIds()));

        List<MusicComposerEntity> composerEntities = composers.stream().map(composer ->
            new MusicComposerEntity(music.getId(), composer.getId())
        ).toList();
        composerRepository.saveAll(composerEntities);

        Set<ArtistEntity> lyricists = new HashSet<>(
                artistRepository.findAllById(request.getParticipants().getLyricistIds()));

        List<MusicLyricistEntity> lyricistEntities = lyricists.stream().map(lyricist ->
                new MusicLyricistEntity(music.getId(), lyricist.getId())
        ).toList();
        lyricistRepository.saveAll(lyricistEntities);

        Set<ArtistEntity> arrangers = new HashSet<>(
                artistRepository.findAllById(request.getParticipants().getArrangerIds()));

        List<MusicArrangerEntity> arrangerEntities = arrangers.stream().map(arranger ->
                new MusicArrangerEntity(music.getId(), arranger.getId())
        ).toList();
        arrangerRepository.saveAll(arrangerEntities);

        return MusicDetailDTO.from(
                music,
                artist,
                album,
                composers,
                lyricists,
                arrangers
        );
    }

    public List<MusicDTO> getMusics(int page, int pageSize) {
        Pageable pageable = PageRequest.of(
                page,
                pageSize,
                Sort.by(Direction.DESC, "id")
        );
        List<MusicEntity> musics = repository
                .findAll(pageable)
                .getContent();

        List<Long> artistIds = musics.stream()
                .map(MusicEntity::getArtistId)
                .toList();
        Map<Long, ArtistEntity> artistMap = artistRepository.findAllById(artistIds)
                .stream()
                .collect(
                        Collectors.toMap(
                                ArtistEntity::getId,
                                Function.identity()
                        )
                );

        List<Long> albumIds = musics.stream()
                .map(MusicEntity::getAlbumId)
                .toList();
        Map<Long, AlbumEntity> albumMap = albumRepository.findAllById(albumIds)
                .stream()
                .collect(
                        Collectors.toMap(
                                AlbumEntity::getId,
                                Function.identity()
                        )
                );

        return musics.stream()
                .map(music -> MusicDTO.from(
                            music,
                            albumMap.get(music.getAlbumId()),
                            artistMap.get(music.getAlbumId())
                )).toList();
    }

    public MusicDetailDTO getMusic(long id) {
        MusicEntity music = MusicUtils.findById(id, repository);
        AlbumEntity album = AlbumUtils.findById(music.getAlbumId(), albumRepository);
        ArtistEntity artist = ArtistUtils.findById(music.getArtistId(), artistRepository);

        List<Long> composerIds = composerRepository.findAllByMusicId(id).stream()
                .map(MusicComposerEntity::getArtistId)
                .toList();
        Set<ArtistEntity> composers = new HashSet<>(
                artistRepository.findAllById(composerIds)
        );

        List<Long> lyricistIds = lyricistRepository.findAllByMusicId(id).stream()
                .map(MusicLyricistEntity::getArtistId)
                .toList();
        Set<ArtistEntity> lyricists = new HashSet<>(
                artistRepository.findAllById(lyricistIds)
        );

        List<Long> arrangerIds = arrangerRepository.findAllByMusicId(id).stream()
                .map(MusicArrangerEntity::getArtistId)
                .toList();
        Set<ArtistEntity> arrangers = new HashSet<>(
                artistRepository.findAllById(arrangerIds)
        );

        return MusicDetailDTO.from(
                music,
                artist,
                album,
                composers,
                lyricists,
                arrangers
        );
    }

    @Transactional
    public MusicDetailDTO updateMusic(Long id, UpdateMusicRequestDTO request) {
        AlbumEntity album = AlbumUtils.findById(request.getAlbumId(), albumRepository);
        ArtistEntity artist = ArtistUtils.findById(request.getParticipants().getArtistId(), artistRepository);
        MusicEntity music = MusicUtils.findById(id, repository);

        music.update(
                request.getTitle(),
                request.getPlayTime(),
                request.getLyrics()
        );

        music.updateFile(new MusicFile(
                request.getFile().getFileUrl(),
                request.getFile().getFormatType()
        ));

        music.updateAlbum(request.getAlbumId());
        music.updateArtist(request.getParticipants().getArtistId());

        repository.save(music);

        Set<ArtistEntity> composers = new HashSet<>(
                artistRepository.findAllById(request.getParticipants().getComposerIds()));

        List<MusicComposerEntity> composerEntities = composers.stream().map(composer ->
                new MusicComposerEntity(music.getId(), composer.getId())
        ).toList();
        composerRepository.saveAll(composerEntities);

        Set<ArtistEntity> lyricists = new HashSet<>(
                artistRepository.findAllById(request.getParticipants().getLyricistIds()));

        List<MusicLyricistEntity> lyricistEntities = lyricists.stream().map(lyricist ->
                new MusicLyricistEntity(music.getId(), lyricist.getId())
        ).toList();
        lyricistRepository.saveAll(lyricistEntities);

        Set<ArtistEntity> arrangers = new HashSet<>(
                artistRepository.findAllById(request.getParticipants().getArrangerIds()));

        List<MusicArrangerEntity> arrangerEntities = arrangers.stream().map(arranger ->
                new MusicArrangerEntity(music.getId(), arranger.getId())
        ).toList();
        arrangerRepository.saveAll(arrangerEntities);

        return MusicDetailDTO.from(
                music,
                artist,
                album,
                composers,
                lyricists,
                arrangers
        );
    }

    @Transactional
    public MusicDetailDTO deleteMusic(Long id) {
        MusicEntity music = MusicUtils.findById(id, repository);
        AlbumEntity album = AlbumUtils.findById(music.getAlbumId(), albumRepository);
        ArtistEntity artist = ArtistUtils.findById(music.getArtistId(), artistRepository);

        List<Long> composerIds = composerRepository.findAllByMusicId(id).stream()
                .map(MusicComposerEntity::getArtistId)
                .toList();
        Set<ArtistEntity> composers = new HashSet<>(
                artistRepository.findAllById(composerIds)
        );

        List<Long> lyricistIds = lyricistRepository.findAllByMusicId(id).stream()
                .map(MusicLyricistEntity::getArtistId)
                .toList();
        Set<ArtistEntity> lyricists = new HashSet<>(
                artistRepository.findAllById(lyricistIds)
        );

        List<Long> arrangerIds = arrangerRepository.findAllByMusicId(id).stream()
                .map(MusicArrangerEntity::getArtistId)
                .toList();
        Set<ArtistEntity> arrangers = new HashSet<>(
                artistRepository.findAllById(arrangerIds)
        );

        repository.delete(music);

        return MusicDetailDTO.from(
                music,
                artist,
                album,
                composers,
                lyricists,
                arrangers
        );
    }
}
