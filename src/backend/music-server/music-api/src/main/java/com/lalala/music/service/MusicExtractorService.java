package com.lalala.music.service;

import com.lalala.music.domain.*;

import com.lalala.exception.BusinessException;
import com.lalala.exception.ErrorCode;
import com.lalala.music.domain.ExtractedMusic;
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

    public ExtractedMusic extract(File file) {
        log.info("MP3 Extracting - " + file.getName());

        try {
            Mp3File mp3file = new Mp3File(file);

            if (mp3file.hasId3v1Tag()) {
                ID3v1 id3v1Tag = mp3file.getId3v1Tag();
                return new ExtractedMusic(
                        id3v1Tag.getTitle(),
                        id3v1Tag.getArtist(),
                        id3v1Tag.getAlbum(),
                        "",
                        mp3file.getLengthInSeconds(),
                        null,
                        null);
            } else if (mp3file.hasId3v2Tag()) {
                ID3v2 id3v2Tag = mp3file.getId3v2Tag();

                String fileName = getNameWithoutExtension(file.getName());
                String title = id3v2Tag.getTitle();
                if (title == null || title.contains(" - ")) {
                    title = getTitleFromFileName(fileName);
                }

                String artist = id3v2Tag.getArtist();
                if (artist == null) {
                    artist = getArtistFromFileName(fileName);
                }

                String album = id3v2Tag.getAlbum();
                if (album == null) {
                    album = "Various Album";
                }

                String lyrics = id3v2Tag.getLyrics();
                if (lyrics == null) {
                    lyrics = "";
                }

                String extension = null;
                byte[] albumImage = null;

                String mimeType = id3v2Tag.getAlbumImageMimeType();
                if (mimeType != null) {
                    extension = mimeType.split("/")[1];
                    albumImage = id3v2Tag.getAlbumImage();
                }

                return new ExtractedMusic(
                        title,
                        artist,
                        album,
                        lyrics,
                        mp3file.getLengthInSeconds(),
                        albumImage,
                        extension);
            } else {
                throw new BusinessException(ErrorCode.INVALID_MP3_TAG);
            }
        } catch (IOException | UnsupportedTagException | InvalidDataException exception) {
            log.error("MP3 Load Error", exception);
            throw new BusinessException(ErrorCode.INVALID_MP3_TAG);
        }
    }

    private String getNameWithoutExtension(String fileNameWithExtension) {
        int dotIndex = fileNameWithExtension.lastIndexOf('.');
        return (dotIndex == -1) ? fileNameWithExtension : fileNameWithExtension.substring(0, dotIndex);
    }

    private String getTitleFromFileName(String fileName) {
        if (fileName.lastIndexOf('-') == -1) {
            return fileName;
        }

        int hyphenIndex = fileName.lastIndexOf(" - ") + 1;
        String subStringToHyphen = fileName.substring(hyphenIndex + 1, fileName.length()).trim();

        int featuringIndex = subStringToHyphen.lastIndexOf(".");
        if (featuringIndex != -1) {
            int lastSpaceIndex = subStringToHyphen.substring(0, featuringIndex).lastIndexOf(" ");
            return subStringToHyphen.substring(0, lastSpaceIndex).trim();
        }

        return subStringToHyphen;
    }

    private String getArtistFromFileName(String fileName) {
        if (fileName.lastIndexOf('-') == -1) {
            return "Various Artist";
        }

        int hyphenIndex = fileName.lastIndexOf(" - ") + 1;
        String subStringToHyphen = fileName.substring(0, hyphenIndex).trim();

        int featuringIndex = subStringToHyphen.lastIndexOf(".");
        if (featuringIndex != -1) {
            int lastSpaceIndex = subStringToHyphen.substring(0, featuringIndex).lastIndexOf(" ");
            return subStringToHyphen.substring(0, lastSpaceIndex).trim();
        }

        return subStringToHyphen;
    }
}
