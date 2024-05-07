package com.lalala.music.service;

import com.lalala.music.domain.*;

import com.lalala.music.domain.Album;
import com.lalala.music.domain.AlbumDetail;
import com.lalala.music.mapper.AlbumDetailMapper;
import com.lalala.music.mapper.AlbumMapper;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lalala.music.dto.CreateAlbumRequestDTO;
import com.lalala.music.dto.UpdateAlbumRequestDTO;
import com.lalala.music.entity.AlbumEntity;
import com.lalala.music.entity.ArtistEntity;
import com.lalala.music.entity.MusicEntity;
import com.lalala.music.repository.AlbumRepository;
import com.lalala.music.repository.ArtistRepository;
import com.lalala.music.repository.MusicRepository;
import com.lalala.music.util.AlbumUtils;
import com.lalala.music.util.ArtistUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlbumService {
    private final AlbumRepository repository;
    private final ArtistRepository artistRepository;
    private final MusicRepository musicRepository;

    @Transactional
    public AlbumDetail createAlbum(CreateAlbumRequestDTO request) {
        ArtistEntity artist = ArtistUtils.findById(request.getArtistId(), artistRepository);

        Optional<AlbumEntity> album = repository.findByTitleAndArtistId(request.getTitle(), artist.getId());
        if (album.isPresent()) {
            return AlbumDetailMapper.from(album.get(), artist);
        }

        AlbumEntity newAlbum = new AlbumEntity(
                request.getTitle(),
                request.getCoverUrl(),
                request.getType(),
                request.getReleasedAt()
        );
        newAlbum.updateArtist(request.getArtistId());
        newAlbum = repository.save(newAlbum);

        return AlbumDetailMapper.from(newAlbum, artist);
    }

    public List<Album> getAlbums(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Direction.DESC, "id"));
        List<AlbumEntity> albums = repository.findAll(pageable).getContent();

        List<Long> artistIds = albums.stream().map(AlbumEntity::getArtistId).toList();
        Map<Long, ArtistEntity> artists =
                artistRepository.findAllById(artistIds).stream()
                        .collect(Collectors.toMap(ArtistEntity::getId, Function.identity()));

        return albums.stream()
                .map(album -> AlbumMapper.from(album, artists.get(album.getArtistId())))
                .toList();
    }

    public AlbumDetail getAlbum(Long id) {
        AlbumEntity album = AlbumUtils.findById(id, repository);
        ArtistEntity artist = ArtistUtils.findById(album.getArtistId(), artistRepository);
        List<MusicEntity> musics = musicRepository.findAllByAlbumId(id);

        return AlbumDetailMapper.from(album, artist, musics);
    }

    @Transactional
    public AlbumDetail updateAlbum(Long id, UpdateAlbumRequestDTO request) {
        AlbumEntity album = AlbumUtils.findById(id, repository);
        ArtistEntity artist = ArtistUtils.findById(album.getArtistId(), artistRepository);
        List<MusicEntity> musics = musicRepository.findAllByAlbumId(id);

        album.update(
                request.getTitle(),
                request.getCoverUrl(),
                request.getType(),
                request.getReleasedAt()
        );
        album.updateArtist(request.getArtistId());

        return AlbumDetailMapper.from(album, artist, musics);
    }

    @Transactional
    public AlbumDetail deleteAlbum(Long id) {
        AlbumEntity album = AlbumUtils.findById(id, repository);
        ArtistEntity artist = ArtistUtils.findById(album.getArtistId(), artistRepository);

        repository.delete(album);

        return AlbumDetailMapper.from(album, artist);
    }
}
