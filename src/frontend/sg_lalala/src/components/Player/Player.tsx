import { useEffect, useState } from "react";
import "./player.css";
import axios from "axios";

const DEFAULT_RAGNE_VALUE = 75;

function Player() {
  const data = [
    {
      id: 1,
      url: "https://firebasestorage.googleapis.com/v0/b/sgwannabe-bd76b.appspot.com/o/audio_sample.mp4?alt=media&token=b6b57033-e8fb-4a8c-8ebe-155c84b924e3",
    },
  ];
  const [audioContext, setAudioContext] = useState(
    new (window.AudioContext || (window as any).webkitAudioContext)()
  );
  const [audios, setAudios] = useState<AudioBuffer[]>([]);

  useEffect(() => {
    const fetchAudio = async () => {
      try {
        const buffers = [];
        for (let i = 0; i < data.length; i++) {
          const res = await axios({
            method: "get",
            url: data[i].url,
            responseType: "arraybuffer",
          });
          const buffer = await audioContext.decodeAudioData(res.data);
          buffers.push(buffer);
        }
        setAudios(buffers);
      } catch (error) {
        console.error("Error fetching audio:", error);
      }
    };
    fetchAudio();
  }, [data, audioContext]);

  const play = () => {
    audios.forEach((audio) => {
      const source = audioContext.createBufferSource();
      source.buffer = audio;
      source.connect(audioContext.destination);
      source.start(0);
    });
  };

  return (
    <div className="flex justify-center w-full">
      <input
        type="range"
        className="c-rng"
        min={0}
        max={360}
        step={1}
        data-range="circular"
      />
      <button onClick={play}>재생</button>
    </div>
  );
}

export default Player;
