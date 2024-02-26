import { useRecoilState, useRecoilValue } from "recoil";
import { playState, playingMusic } from "../../state";
import play_icon from "../../assets/background_play.png";
import pause_icon from "../../assets/background_pause.png";
import play_back from "../../assets/play_back.png";
import play_front from "../../assets/play_front.png";
import close_icon from "../../assets/remove.png";
import { useRef, useState } from "react";

function MusicBar() {
  const nowPlayingMusic = useRecoilValue(playingMusic);
  const [isPlaying, setIsPlaying] = useRecoilState(playState);
  const audioRef = useRef<HTMLAudioElement>(null);
  const [currentTime, setCurrentTime] = useState<number>(0);

  const togglePlay = () => {
    if (audioRef.current) {
      if (isPlaying.isPlaying) {
        audioRef.current.pause();
      } else {
        audioRef.current.play();
      }
      setIsPlaying({
        backgroundPlaying: isPlaying.backgroundPlaying,
        isPlaying: !isPlaying.isPlaying,
      });
    }
    console.log(isPlaying);
  };

  const handleTimeUpdate = () => {
    if (audioRef.current) {
      setCurrentTime(audioRef.current.currentTime);
    }
  };

  const handleSeek = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newTime = parseFloat(e.target.value);
    setCurrentTime(newTime);
    if (audioRef.current) {
      audioRef.current.currentTime = newTime;
    }
  };

  return (
    <div
      className={`absolute bottom-0 w-full bg-black/80 text-white items-center justify-between p-2 rounded-xl flex flex-row transition-all duration-500 ease-in-out ${
        !isPlaying.isPlaying ? "transform translate-y-0" : ""
      }`}
    >
      <img src={nowPlayingMusic.img_url} className="w-[65px] rounded-sm" />
      <div className="flex flex-col">
        <div className="">{nowPlayingMusic.title}</div>
        <div className="">{nowPlayingMusic.artist}</div>
      </div>
      <div className="flex flex-row">
        <img src={play_back} className="w-10 h-10 cursor-pointer" />
        {isPlaying.isPlaying ? (
          <img
            src={play_icon}
            className="w-10 h-10 cursor-pointer"
            onClick={togglePlay}
          />
        ) : (
          <img
            src={pause_icon}
            className="w-10 h-10 cursor-pointer"
            onClick={togglePlay}
          />
        )}
        <img src={play_front} className="w-10 h-10 cursor-pointer" />
      </div>
      <audio ref={audioRef} onTimeUpdate={handleTimeUpdate} />
      <input
        type="range"
        value={currentTime}
        max={audioRef.current?.duration || 0}
        onChange={handleSeek}
        className="w-48 h-1 rounded-sm bg-whiteDark input-range"
      />
      <div className="">{nowPlayingMusic.time}</div>
      <img
        src={close_icon}
        className="w-8 h-8 cursor-pointer"
        onClick={() => {
          setIsPlaying({ backgroundPlaying: false, isPlaying: false });
        }}
      />
    </div>
  );
}

export default MusicBar;
