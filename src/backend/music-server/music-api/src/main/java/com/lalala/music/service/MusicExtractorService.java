package com.lalala.music.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lalala.music.dto.ExtractedMusicDTO;
import com.lalala.music.exception.BusinessException;
import com.lalala.music.exception.ErrorCode;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

@Service
@RequiredArgsConstructor
@Slf4j
public class MusicExtractorService {
    private final ArtistService artistService;
    private final MusicService musicService;
    private final AlbumService albumService;

    public File downloadToTemp(MultipartFile multipart) {
        Objects.requireNonNull(multipart.getOriginalFilename());

        log.info(
                "음원 임시 파일을 다운로드 - "
                        + multipart.getOriginalFilename()
                        + " ( "
                        + multipart.getContentType()
                        + " )");
        try {
            Path destination =
                    Paths.get("temp").resolve(multipart.getOriginalFilename()).normalize().toAbsolutePath();
            InputStream bufferedInputStream = new BufferedInputStream(multipart.getInputStream());
            Files.copy(bufferedInputStream, destination, StandardCopyOption.REPLACE_EXISTING);
            return new File(destination.toUri());
        } catch (IOException exception) {
            log.error("음원 임시 파일 다운로드 예외 발생", exception);
            throw new BusinessException(ErrorCode.TEMP_DOWNLOAD_FAILED);
        }
    }

    public ExtractedMusicDTO extract(File file) {
        log.info("MP3 Extracting - " + file.getName());

        try {
            Mp3File mp3file = new Mp3File(file);

            if (mp3file.hasId3v1Tag()) {
                ID3v1 id3v1Tag = mp3file.getId3v1Tag();
                return new ExtractedMusicDTO(
                        id3v1Tag.getTitle(),
                        id3v1Tag.getArtist(),
                        id3v1Tag.getAlbum(),
                        "",
                        mp3file.getLengthInSeconds(),
                        null,
                        null);
            } else if (mp3file.hasId3v2Tag()) {
                ID3v2 id3v2Tag = mp3file.getId3v2Tag();

                String mimeType = id3v2Tag.getAlbumImageMimeType();
                String extension = mimeType.split("/")[1];

                return new ExtractedMusicDTO(
                        id3v2Tag.getTitle(),
                        id3v2Tag.getArtist(),
                        id3v2Tag.getAlbum(),
                        id3v2Tag.getLyrics() == null ? "" : id3v2Tag.getLyrics(),
                        mp3file.getLengthInSeconds(),
                        id3v2Tag.getAlbumImage(),
                        extension);
            } else {
                throw new BusinessException(ErrorCode.INVALID_MP3_TAG);
            }
        } catch (IOException | UnsupportedTagException | InvalidDataException exception) {
            log.error("MP3 Load Error", exception);
            throw new BusinessException(ErrorCode.INVALID_MP3_TAG);
        }
    }
}
