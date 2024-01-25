package com.sgwannabe.playlistserver.playlist.util;

public class KeyGenerator {
    private static final String PLAYLIST_KEY_PREFIX = "playlist";

    public static String cartKeyGenerate(String id) {
        return PLAYLIST_KEY_PREFIX + ":" + id;
    }
}
