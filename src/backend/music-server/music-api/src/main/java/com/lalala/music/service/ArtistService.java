package com.lalala.music.service;

import com.lalala.music.dto.ArtistDTO;
import com.lalala.music.dto.ArtistDetailDTO;
import com.lalala.music.dto.CreateArtistRequestDTO;
import com.lalala.music.dto.UpdateArtistRequestDTO;
import com.lalala.music.entity.AlbumEntity;
import com.lalala.music.entity.ArtistEntity;
import com.lalala.music.entity.MusicArrangerEntity;
import com.lalala.music.entity.MusicComposerEntity;
import com.lalala.music.entity.MusicEntity;
import com.lalala.music.entity.MusicLyricistEntity;
import com.lalala.music.repository.AlbumRepository;
import com.lalala.music.repository.ArtistRepository;
import com.lalala.music.repository.MusicArrangerRepository;
import com.lalala.music.repository.MusicComposerRepository;
import com.lalala.music.repository.MusicLyricistRepository;
import com.lalala.music.repository.MusicRepository;
import com.lalala.music.util.ArtistUtils;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArtistService {
    private final ArtistRepository repository;
    private final MusicRepository musicRepository;
    private final MusicComposerRepository musicComposerRepository;
    private final MusicLyricistRepository musicLyricistRepository;
    private final MusicArrangerRepository musicArrangerRepository;
    private final AlbumRepository albumRepository;

    @Transactional
    public ArtistDTO createArtist(CreateArtistRequestDTO request) {
        ArtistEntity artist = new ArtistEntity(
                request.getName(),
                request.getGender(),
                request.getType(),
                request.getAgency()
        );
        artist = repository.save(artist);
        return ArtistDTO.from(artist);
    }

    public List<ArtistDTO> getArtists(int page, int pageSize) {
        Pageable pageable = PageRequest.of(
                page,
                pageSize,
                Sort.by(Direction.DESC, "id")
        );
        Page<ArtistDTO> artists = repository
                .findAll(pageable)
                .map(ArtistDTO::from);
        return artists.getContent();
    }

    public ArtistDetailDTO getArtist(Long id) {
        ArtistEntity artist = ArtistUtils.findById(id, repository);

        List<Long> composerIds = musicComposerRepository.findAllByArtistId(id).stream()
                .map(MusicComposerEntity::getMusicId)
                .toList();

        List<Long> lyricistIds = musicLyricistRepository.findAllByArtistId(id).stream()
                .map(MusicLyricistEntity::getMusicId)
                .toList();

        List<Long> arrangerIds = musicArrangerRepository.findAllByArtistId(id).stream()
                .map(MusicArrangerEntity::getMusicId)
                .toList();

        Set<Long> musicIdSet = new HashSet<>(
                Stream.of(composerIds, lyricistIds, arrangerIds)
                        .flatMap(Collection::stream)
                        .toList()
        );
        List<Long> musicIds = musicIdSet.stream().toList();

        Map<Long, MusicEntity> musicMap = musicRepository.findAllById(musicIds)
                .stream()
                .collect(
                        Collectors.toMap(
                            MusicEntity::getId,
                            Function.identity()
                        )
                );

        Set<MusicEntity> composedMusics = composerIds.stream()
                .map(musicMap::get).collect(Collectors.toSet());
        Set<MusicEntity> writtenMusics = lyricistIds.stream()
                .map(musicMap::get).collect(Collectors.toSet());
        Set<MusicEntity> arrangedMusics = arrangerIds.stream()
                .map(musicMap::get).collect(Collectors.toSet());

        List<Long> albumIds = musicRepository.findAllById(musicIds).stream()
                .map(MusicEntity::getAlbumId)
                .toList();
        Map<Long, AlbumEntity> albumMap = albumRepository.findAllById(albumIds).stream()
                .collect(
                        Collectors.toMap(
                                AlbumEntity::getId,
                                Function.identity()
                        )
                );

        return ArtistDetailDTO.from(
                artist,
                composedMusics,
                writtenMusics,
                arrangedMusics,
                albumMap
        );
    }

    @Transactional
    public ArtistDTO updateArtist(Long id, UpdateArtistRequestDTO request) {
        ArtistEntity artist = ArtistUtils.findById(id, repository);
        artist.update(
                request.getName(),
                request.getGender(),
                request.getType(),
                request.getAgency()
        );
        return ArtistDTO.from(artist);
    }

    @Transactional
    public ArtistDTO deleteArtist(Long id) {
        ArtistEntity artist = ArtistUtils.findById(id, repository);
        repository.delete(artist);
        return ArtistDTO.from(artist);
    }
}
