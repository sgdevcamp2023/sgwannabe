package com.sgwannabe.playlistserver.playlist.service;

@Service
@RequiredArgsConstructor
public class PlaylistEventPublisher {
    private final KafkaTemplate<String, PlaylistEvent> kafkaTemplate;

    public void publishPlaylistCreated(Playlist playlist) {
        PlaylistEvent event = new PlaylistEvent("CREATED", playlist.getId());
        kafkaTemplate.send("playlist-events", event);
    }

    public void publishPlaylistDeleted(String playlistId) {
        PlaylistEvent event = new PlaylistEvent("DELETED", playlistId);
        kafkaTemplate.send("playlist-events", event);
    }
}

public class PlaylistEvent {
    private String eventType;
    private String playlistId;

    // constructor, getters, setters
}
