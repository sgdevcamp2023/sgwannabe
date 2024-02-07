package com.lalala.music.service;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lalala.music.dto.ArtistDTO;
import com.lalala.music.dto.ArtistDetailDTO;
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
    public ArtistDTO createArtist(CreateArtistRequestDTO request) {
        ArtistEntity artist =
                new ArtistEntity(
                        request.getName(), request.getGender(), request.getType(), request.getAgency());
        artist = repository.save(artist);
        return ArtistDTO.from(artist);
    }

    public List<ArtistDTO> getArtists(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Direction.DESC, "id"));
        Page<ArtistDTO> artists = repository.findAll(pageable).map(ArtistDTO::from);
        return artists.getContent();
    }

    public ArtistDetailDTO getArtist(Long id) {
        ArtistEntity artist = ArtistUtils.findById(id, repository);

        return ArtistDetailDTO.from(artist);
    }

    @Transactional
    public ArtistDTO updateArtist(Long id, UpdateArtistRequestDTO request) {
        ArtistEntity artist = ArtistUtils.findById(id, repository);
        artist.update(request.getName(), request.getGender(), request.getType(), request.getAgency());
        return ArtistDTO.from(artist);
    }

    @Transactional
    public ArtistDTO deleteArtist(Long id) {
        ArtistEntity artist = ArtistUtils.findById(id, repository);
        repository.delete(artist);
        return ArtistDTO.from(artist);
    }
}
