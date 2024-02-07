import { motion } from "framer-motion";
import { cn } from "../../utils";
import { useRecoilValue } from "recoil";
import { playingMusic } from "../../state";
import { memo } from "react";

interface CircularProgressProps {
  duration: number;
  currentProgress: number;
  size?: number;
  className?: string;
}

function CircularProgress({
  duration,
  currentProgress,
  className,
  size = 300,
}: CircularProgressProps) {
  const nowPlayingMusic = useRecoilValue(playingMusic);

  const circumference = Math.PI * (size - 12);
  const strokeDasharray = circumference * (1 - currentProgress / duration);

  return (
    <div className={cn("flex", className)}>
      <div className="relative">
        <svg
          width={size}
          height={size}
          viewBox="0 0 200 200"
          className="w-full h-full overflow-visible"
        >
          <motion.circle
            cx="100"
            cy="100"
            r="100"
            stroke="#00CD3C"
            fill="#FFFFFF"
            strokeWidth={12}
            strokeDasharray={circumference}
            strokeDashoffset={strokeDasharray}
          />
        </svg>
        <motion.img
          src={nowPlayingMusic.img_url}
          animate={{
            rotate: 360,
          }}
          transition={{
            repeat: Infinity,
            duration: 15,
            ease: "linear",
          }}
          className="absolute top-0 z-10 object-contain w-full h-full -translate-x-1/2 rounded-full"
        />
        <div className="absolute z-50 transform -translate-x-1/2 -translate-y-1/2 bg-black border rounded-full w-14 h-14 left-1/2 top-1/2 border-primary" />
      </div>
    </div>
  );
}
const MemoizedCircularProgress = memo(CircularProgress);

export default MemoizedCircularProgress;
