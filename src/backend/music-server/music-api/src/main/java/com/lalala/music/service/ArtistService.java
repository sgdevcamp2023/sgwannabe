package com.lalala.music.service;

import com.lalala.music.domain.*;

import com.lalala.music.domain.Artist;
import com.lalala.music.domain.ArtistDetail;
import com.lalala.music.mapper.ArtistDetailMapper;
import com.lalala.music.mapper.ArtistMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lalala.music.dto.CreateArtistRequestDTO;
import com.lalala.music.dto.UpdateArtistRequestDTO;
import com.lalala.music.entity.ArtistEntity;
import com.lalala.music.repository.AlbumRepository;
import com.lalala.music.repository.ArtistRepository;
import com.lalala.music.repository.MusicRepository;
import com.lalala.music.util.ArtistUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArtistService {
    private final ArtistRepository repository;
    private final MusicRepository musicRepository;
    private final AlbumRepository albumRepository;

    @Transactional
    public Artist createArtist(CreateArtistRequestDTO request) {
        Optional<ArtistEntity> artist = repository.findByNameAndAgency(request.getName(), request.getAgency());

        if (artist.isPresent()) {
            return ArtistMapper.from(artist.get());
        }

        ArtistEntity newArtist = new ArtistEntity(
                request.getName(),
                request.getGender(),
                request.getType(),
                request.getAgency()
        );
        newArtist = repository.save(newArtist);
        return ArtistMapper.from(newArtist);
    }

    public List<Artist> getArtists(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Direction.DESC, "id"));
        Page<Artist> artists = repository.findAll(pageable).map(ArtistMapper::from);
        return artists.getContent();
    }

    public ArtistDetail getArtist(Long id) {
        ArtistEntity artist = ArtistUtils.findById(id, repository);

        return ArtistDetailMapper.from(artist);
    }

    @Transactional
    public Artist updateArtist(Long id, UpdateArtistRequestDTO request) {
        ArtistEntity artist = ArtistUtils.findById(id, repository);
        artist.update(request.getName(), request.getGender(), request.getType(), request.getAgency());
        return ArtistMapper.from(artist);
    }

    @Transactional
    public Artist deleteArtist(Long id) {
        ArtistEntity artist = ArtistUtils.findById(id, repository);
        repository.delete(artist);
        return ArtistMapper.from(artist);
    }
}
