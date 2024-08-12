package com.sgwannabe.playlistserver.playlist.service;

@Service
@RequiredArgsConstructor
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final CacheService cacheService;
    private final PlaylistToDtoConverter converter;
    private final MusicService musicService;
    private final AsyncDatabaseService asyncDatabaseService;

    public PlaylistResponseDto createPlaylist(PlaylistRequestDto requestDto) {
        List<Music> musics = musicService.getMusicsFromIds(requestDto.getMusics());
        Playlist playlist = Playlist.createFrom(requestDto, musics);
        Playlist saved = playlistRepository.save(playlist);
        cacheService.cachePlaylist(saved);
        asyncDatabaseService.savePlaylistAsync(saved);  // 비동기 저장
        return converter.convert(saved);
    }

    public PlaylistResponseDto getPlaylistById(String id) {
        return cacheService.getCachedPlaylist(id)
                .orElseGet(() -> {
                    Playlist playlist = playlistRepository.findById(id)
                            .orElseThrow(() -> new PlaylistNotFoundException(id));
                    cacheService.cachePlaylist(playlist);
                    return converter.convert(playlist);
                });
    }

    @Transactional
    public PlaylistResponseDto updatePlayListById(String id, PlaylistRequestDto requestDto) {
        Playlist playlist = getPlaylistOrThrow(id);
        playlist.update(requestDto);
        Playlist saved = playlistRepository.save(playlist);
        cacheService.cachePlaylist(saved);
        return converter.convert(saved);
    }

    @Transactional
    public void deletePlaylistById(String id) {
        Playlist playlist = getPlaylistOrThrow(id);
        cacheService.evictPlaylist(id);
        asyncDatabaseService.deletePlaylistAsync(id);  // 비동기 삭제
    }

    @Transactional
    public PlaylistResponseDto addMusic(String playlistId, MusicRequestDto musicRequestDto) {
        Playlist playlist = getPlaylistOrThrow(playlistId);
        Music music = musicService.createMusic(musicRequestDto);
        playlist.addMusic(music);
        Playlist saved = playlistRepository.save(playlist);
        cacheService.cachePlaylist(saved);
        return converter.convert(saved);
    }

    private Playlist getPlaylistOrThrow(String id) {
        return playlistRepository.findById(id)
                .orElseThrow(() -> new PlaylistNotFoundException(id));
    }
}
