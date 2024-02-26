import { useLocation } from "react-router-dom";
import * as StompJs from "@stomp/stompjs";
import PlaylistSongComponent from "./PlaylistSongComponent";
import { useEffect, useState } from "react";
import { useRecoilState } from "recoil";
import { playingMusicId } from "../../state";

export interface Song {
  id: number;
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
  const [time, setTime] = useState<number>(0);
  const [realTime, setRealTime] = useState<number>(0);
  const [songId, setSongId] = useRecoilState(playingMusicId);

  useEffect(() => {
    const ws = new WebSocket("ws://localhost:22000/v1/ws/streaming");
    ws.binaryType = "arraybuffer";
    setSocket(ws);

    return () => {
      if (ws) {
        ws.close();
        audioContext?.close();
        audioContext?.suspend();
        setSourceNode(null);
        setStream(false);
        setTime(0);
        console.log("ws close");
      }
    };
  }, []);

  useEffect(() => {
    if (!socket) return;

    socket.onopen = () => {
      socket.send(`1/${songId}`);
    };

    socket.onmessage = async (event) => {
      if (!stream) {
        socket.send(`2/${songId}/0`);
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
          console.log(time, audioContext.currentTime);

          setSourceNode(node);
        }
      }
    };

    socket.onerror = (error) => {
      console.log(error);
    };
  }, [audioContext, songId, socket, stream, time, audioContext?.currentTime]);

  const handleButtonClick = () => {
    if (socket) {
      socket.send(`1/${songId}`);
    }
  };

  useEffect(() => {
    if (audioContext) {
      if (audioContext.currentTime !== 0 && audioContext?.currentTime >= time) {
        console.log("노래가 끝났습니다.");
        // setSongId(songId + 1);
      }
    }
  }, [audioContext?.currentTime, time]);

  return (
    <div className="flex flex-col items-center w-1/2 pb-5">
      <div className="w-full px-10 overflow-y-auto max-h-[88vh]">
        <button className="text-black" onClick={handleButtonClick}>
          play
        </button>
        {state &&
          state.playlist.musics.map((music: Song, index: number) => (
            <PlaylistSongComponent
              key={index}
              id={music.id}
              title={music.title}
              artist={music.artist}
              thumbnail={
                music.thumbnail !== ""
                  ? music.thumbnail
                  : "https://image.bugsm.co.kr/album/images/500/40924/4092452.jpg"
              }
            />
          ))}
      </div>
    </div>
  );
}

export default PlaylistComponent;
