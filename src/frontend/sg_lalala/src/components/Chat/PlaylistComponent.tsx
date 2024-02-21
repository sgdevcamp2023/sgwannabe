import { useLocation } from "react-router-dom";
import * as StompJs from "@stomp/stompjs";
import PlaylistSongComponent from "./PlaylistSongComponent";
import { useEffect, useState } from "react";

export interface Song {
  title: string;
  artist: string;
  thumbnail: string;
}

function PlaylistComponent() {
  const { state } = useLocation();
  const [socket, setSocket] = useState<WebSocket | null>(null);
  const [stream, setStream] = useState(false);
  const [audioContext, setAudioContext] = useState<AudioContext | null>(null);
  const [sourceNode, setSourceNode] = useState<AudioBufferSourceNode | null>(
    null
  );
  const [time, setTime] = useState(0);

  useEffect(() => {
    const ws = new WebSocket("ws://localhost:22000/v1/ws/streaming");
    ws.binaryType = "arraybuffer";
    setSocket(ws);

    return () => {
      if (ws) {
        ws.close();
      }
    };
  }, []);

  useEffect(() => {
    if (!socket) return;

    socket.onopen = () => {
      socket.send("1/1");
    };

    socket.onmessage = async (event) => {
      if (!stream) {
        socket.send("2/1/0");
        setStream(true);
      } else {
        if (!audioContext) {
          const audioCtx = new AudioContext();
          setAudioContext(audioCtx);
          audioCtx.resume().then(() => {
            console.log("AudioContext resumed");
          });
        }

        if (audioContext) {
          const buffer = await audioContext.decodeAudioData(event.data);
          const node = audioContext.createBufferSource();
          node.buffer = buffer;
          node.connect(audioContext.destination);
          node.start(time);

          setTime((prevTime) => prevTime + buffer.duration);
          console.log(time);

          setSourceNode(node);
        }
      }
    };

    socket.onerror = (error) => {
      console.log(error);
    };
  }, [audioContext, socket, stream, time]);

  const handleButtonClick = () => {
    if (socket) {
      socket.send("1/4");
    }
  };

  return (
    <div className="flex flex-col items-center w-1/2 px-10 py-5">
      <button onClick={handleButtonClick}>play</button>
      {state &&
        state.playlist.musics.map((music: Song, index: number) => (
          <PlaylistSongComponent
            key={index}
            title={music.title}
            artist={music.artist}
            thumbnail={music.thumbnail}
            // socket={socket}
          />
        ))}
    </div>
  );
}

export default PlaylistComponent;
