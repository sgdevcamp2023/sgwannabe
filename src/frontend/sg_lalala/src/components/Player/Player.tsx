import { useState } from "react";
import songs from "../../data/songs.json";
import AudioPlayer from "./AudioPlayer";
import MemoizedCircularProgress from "./CircularProgress";
import { useRecoilValue } from "recoil";
import { playingMusic } from "../../state";

function Player() {
  const [duration, setDuration] = useState<number>(0);
  const [currentSongIndex, setCurrentSongIndex] = useState<number>(0);
  const [currrentProgress, setCurrrentProgress] = useState<number>(0);

  const currentSong = songs[currentSongIndex];
  const nowPlayingMusic = useRecoilValue(playingMusic);

  return (
    <div
      style={{ backgroundImage: `url(${nowPlayingMusic.img_url})` }}
      className="w-full h-screen bg-cover"
    >
      <div className="flex flex-col items-center w-full h-screen backdrop-blur-2xl">
        <MemoizedCircularProgress
          duration={duration}
          currentProgress={currrentProgress}
          className="p-20"
        />
        <AudioPlayer
          key={currentSongIndex}
          currentSong={currentSong}
          songCount={songs.length}
          songIndex={currentSongIndex}
          duration={duration}
          setDuration={setDuration}
          currentProgress={currrentProgress}
          setCurrentProgress={setCurrrentProgress}
          onNext={() =>
            setCurrentSongIndex((i) => Math.min(i + 1, songs.length))
          }
          onPrev={() => setCurrentSongIndex((i) => Math.max(i - 1, 0))}
        />
      </div>
    </div>
  );
}

export default Player;
