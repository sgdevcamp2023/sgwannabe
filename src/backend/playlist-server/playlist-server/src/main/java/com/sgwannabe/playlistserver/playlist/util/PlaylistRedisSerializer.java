package com.sgwannabe.playlistserver.playlist.util;

import com.sgwannabe.playlistserver.playlist.domain.Playlist;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class PlaylistRedisSerializer implements RedisSerializer<Playlist> {

    @Override
    public byte[] serialize(Playlist playlist) throws SerializationException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(playlist);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new SerializationException("Error serializing object", e);
        }
    }

    @Override
    public Playlist deserialize(byte[] bytes) throws SerializationException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInput in = new ObjectInputStream(bis)) {
            return (Playlist) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new SerializationException("Error deserializing object", e);
        }
    }
}
