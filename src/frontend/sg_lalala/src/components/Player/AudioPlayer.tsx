import {
  MdPlayArrow,
  MdPause,
  MdSkipNext,
  MdSkipPrevious,
  MdVolumeUp,
  MdVolumeOff,
} from "react-icons/md";
import { CgSpinner } from "react-icons/cg";
import IconButton from "./IconButton";
import AudioProgressBar from "./AudioProgressBar";
import { useEffect, useRef, useState } from "react";
import { setState } from "../../@types/react";

function timeFormat(duration: number) {
  const min = Math.floor(duration / 60);
  const sec = Math.floor(duration - min * 60);

  const formatted = [min, sec].map((n) => (n < 10 ? "0" + n : n)).join(":");

  return formatted;
}

interface AudioPlayerProps {
  currentSong?: { title: string; src: string };
  songIndex: number;
  songCount: number;
  duration: number;
  setDuration: setState<number>;
  currentProgress: number;
  setCurrentProgress: setState<number>;
  onNext: () => void;
  onPrev: () => void;
}

export default function AudioPlayer({
  currentSong,
  songCount,
  songIndex,
  duration,
  setDuration,
  currentProgress,
  setCurrentProgress,
  onNext,
  onPrev,
}: AudioPlayerProps) {
  const MAX_VOLUME = 1;
  const INITIAL_AUDIO_BUFFER = 0;

  const audioRef = useRef<HTMLAudioElement | null>(null);

  const [isReady, setIsReady] = useState<boolean>(false);
  const [buffered, setBuffered] = useState<number>(INITIAL_AUDIO_BUFFER);
  const [volume, setVolume] = useState<number>(MAX_VOLUME);
  const [isPlaying, setIsPlaying] = useState<boolean>(false);

  const durationDisplay = timeFormat(duration);
  const elapsedDisplay = timeFormat(currentProgress);

  useEffect(() => {
    audioRef.current?.pause();

    if (songIndex !== 0) {
      const timeout = setTimeout(() => {
        audioRef.current?.play();
      }, 500);

      return () => {
        clearTimeout(timeout);
      };
    }
  }, [songIndex]);

  const togglePlayPause = () => {
    if (isPlaying) {
      audioRef.current?.pause();
      setIsPlaying(false);
    } else {
      audioRef.current?.play();
      setIsPlaying(true);
    }
  };

  const handleBufferProgress: React.ReactEventHandler<HTMLAudioElement> = (
    e
  ) => {
    const audio = e.currentTarget;
    const dur = audio.duration;
    if (dur > 0) {
      for (let i = 0; i < audio.buffered.length; i++) {
        if (
          audio.buffered.start(audio.buffered.length - 1 - i) <
          audio.currentTime
        ) {
          const bufferedLength = audio.buffered.end(
            audio.buffered.length - 1 - i
          );
          setBuffered(bufferedLength);
          break;
        }
      }
    }
  };

  const handleMuteUnmute = () => {
    if (!audioRef.current) return;

    if (audioRef.current.volume !== 0) {
      audioRef.current.volume = 0;
    } else {
      audioRef.current.volume = 1;
    }
  };

  return (
    <div className="relative p-3 bg-slate-900 text-slate-400">
      {currentSong && (
        <audio
          ref={audioRef}
          preload="metadata"
          onDurationChange={(e) => setDuration(e.currentTarget.duration)}
          onPlaying={() => setIsPlaying(true)}
          onPause={() => setIsPlaying(false)}
          onEnded={() => onNext()}
          onCanPlay={(e) => {
            e.currentTarget.volume = volume;
            setIsReady(true);
          }}
          onTimeUpdate={(e) => {
            setCurrentProgress(e.currentTarget.currentTime);
            handleBufferProgress(e);
          }}
          onProgress={handleBufferProgress}
          onVolumeChange={(e) => setVolume(e.currentTarget.volume)}
        >
          <source type="audio/mpeg" src={currentSong.src} />
        </audio>
      )}
      <AudioProgressBar
        duration={duration}
        currentProgress={currentProgress}
        buffered={buffered}
        onChange={(e) => {
          if (!audioRef.current) return;

          audioRef.current.currentTime = e.currentTarget.valueAsNumber;

          setCurrentProgress(e.currentTarget.valueAsNumber);
        }}
      />

      <div className="flex flex-col items-center justify-center">
        <div className="mb-1 text-center">
          <p className="font-bold text-slate-300">
            {currentSong?.title ?? "Select a song"}
          </p>
          <p className="text-xs">Singer Name</p>
        </div>
      </div>
      <div className="grid items-center grid-cols-3 mt-4">
        <span className="text-xs">
          {elapsedDisplay} / {durationDisplay}
        </span>
        <div className="flex items-center gap-4 justify-self-center">
          <IconButton
            onClick={() => onPrev()}
            disabled={songIndex === 0}
            aria-label="go to previous"
            intent="secondary"
          >
            <MdSkipPrevious size={24} />
          </IconButton>
          <IconButton
            disabled={!isReady}
            onClick={togglePlayPause}
            aria-label={isPlaying ? "Pause" : "Play"}
            size="lg"
          >
            {!isReady && currentSong ? (
              <CgSpinner size={24} className="animate-spin" />
            ) : isPlaying ? (
              <MdPause size={30} />
            ) : (
              <MdPlayArrow size={30} />
            )}
          </IconButton>
          <IconButton
            onClick={() => onNext()}
            disabled={songIndex === songCount - 1}
            aria-label="go to next"
            intent="secondary"
          >
            <MdSkipNext size={24} />
          </IconButton>
        </div>

        <div className="flex items-center gap-3 justify-self-end">
          <IconButton
            intent="secondary"
            size="sm"
            onClick={handleMuteUnmute}
            aria-label={volume === 0 ? "unmute" : "mute"}
          >
            {volume === 0 ? (
              <MdVolumeOff size={20} />
            ) : (
              <MdVolumeUp size={20} />
            )}
          </IconButton>
        </div>
      </div>
    </div>
  );
}
