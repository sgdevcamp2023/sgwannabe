package com.lalala.music.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import com.lalala.music.dto.AlbumDetailDTO;
import com.lalala.music.dto.ArtistDTO;
import com.lalala.music.dto.CreateAlbumRequestDTO;
import com.lalala.music.dto.CreateArtistRequestDTO;
import com.lalala.music.dto.CreateMusicRequestDTO;
import com.lalala.music.dto.ExtractedMusicDTO;
import com.lalala.music.dto.FileDTO;
import com.lalala.music.dto.MusicDetailDTO;
import com.lalala.music.dto.ParticipantsRequestDTO;
import com.lalala.music.dto.UploadResponseDTO;
import com.lalala.music.entity.AlbumType;
import com.lalala.music.entity.ArtistType;
import com.lalala.music.entity.FormatType;
import com.lalala.music.entity.GenderType;
import com.lalala.music.entity.MusicFile;
import com.lalala.music.exception.UploadFailedException;

@Service
@RequiredArgsConstructor
@Slf4j
public class MusicUploaderService {
    @Qualifier("uploaderClient")
    private final RestClient restClient;

    private final MusicExtractorService extractorService;
    private final MusicService musicService;
    private final ArtistService artistService;
    private final AlbumService albumService;

    @Transactional
    public MusicDetailDTO upload(MultipartFile file) {
        File downloadFile = extractorService.downloadToTemp(file);

        ExtractedMusicDTO extractedMusic = extractorService.extract(downloadFile);

        UploadResponseDTO imageResponse =
                this.uploadImage(extractedMusic.getCoverData(), extractedMusic.getCoverExtension());
        UploadResponseDTO musicResponse = this.uploadMusic(file);

        ArtistDTO artist =
                artistService.createArtist(
                        new CreateArtistRequestDTO(
                                extractedMusic.getArtist(), GenderType.NONE, ArtistType.NONE, "NCS"));

        AlbumDetailDTO album =
                albumService.createAlbum(
                        new CreateAlbumRequestDTO(
                                extractedMusic.getAlbum(),
                                imageResponse.getUrl(),
                                AlbumType.SINGLE,
                                LocalDateTime.now(),
                                artist.getId()));

        return musicService.createMusic(
                new CreateMusicRequestDTO(
                        extractedMusic.getTitle(),
                        extractedMusic.getPlayTime().shortValue(),
                        extractedMusic.getLyrics(),
                        album.getId(),
                        FileDTO.from(new MusicFile(musicResponse.getUrl(), FormatType.MP3)),
                        new ParticipantsRequestDTO(artist.getId())));
    }

    public UploadResponseDTO uploadImage(byte[] imageData, String extension) {
        if (imageData == null || extension == null) {
            return new UploadResponseDTO(null);
        }

        ByteArrayResource imageResource =
                new ByteArrayResource(imageData) {
                    @Override
                    public String getFilename() {
                        return UUID.randomUUID() + "." + extension;
                    }
                };

        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("upload", imageResource);

        return restClient
                .post()
                .uri("/upload")
                .headers(httpHeaders -> httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA))
                .body(parts)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        (req, res) -> {
                            log.error("앨범커버 업로드 실패(4xx) - " + imageResource.getFilename());
                            throw new UploadFailedException();
                        })
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        (req, res) -> {
                            log.error("앨범커버 업로드 실패(5xx) - " + imageResource.getFilename());
                            throw new UploadFailedException();
                        })
                .body(UploadResponseDTO.class);
    }

    public UploadResponseDTO uploadMusic(MultipartFile file) {
        Objects.requireNonNull(file);

        try {
            ByteArrayResource musicResource =
                    new ByteArrayResource(file.getBytes()) {
                        @Override
                        public String getFilename() {
                            return file.getOriginalFilename();
                        }
                    };

            MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
            parts.add("upload", musicResource);

            return restClient
                    .post()
                    .uri("/upload")
                    .headers(httpHeaders -> httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA))
                    .body(parts)
                    .retrieve()
                    .onStatus(
                            HttpStatusCode::is4xxClientError,
                            (req, res) -> {
                                log.error("음원 업로드 실패(4xx) - " + musicResource.getFilename());
                                throw new UploadFailedException();
                            })
                    .onStatus(
                            HttpStatusCode::is5xxServerError,
                            (req, res) -> {
                                log.error("음원 업로드 실패(5xx) - " + musicResource.getFilename());
                                throw new UploadFailedException();
                            })
                    .body(UploadResponseDTO.class);
        } catch (IOException exception) {
            log.error("음원 업로드 실패", exception);
            throw new UploadFailedException();
        }
    }
}
