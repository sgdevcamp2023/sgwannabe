import { motion } from "framer-motion";
import { cn } from "../../utils";
import { useRecoilValue } from "recoil";
import { playingMusic } from "../../state";
import { useEffect, useState } from "react";

interface CircularProgressProps {
  value: number;
  minValue?: number;
  maxValue?: number;
  size?: number;
  className?: string;
}

function CircularProgress({
  value,
  className,
  minValue = 0,
  maxValue = 100,
  size = 300,
}: CircularProgressProps) {
  const nowPlayingMusic = useRecoilValue(playingMusic);

  const draw = {
    hidden: { pathLength: 0, opacity: 0 },
    visible: (i: number) => {
      const delay = 1 + i * 0.5;
      return {
        pathLength: 1,
        opacity: 1,
        transition: {
          pathLength: { delay, type: "spring", duration: 1.5, bounce: 0 },
          opacity: { delay, duration: 0.01 },
        },
      };
    },
  };

  return (
    <div className={cn("flex", className)}>
      <div className="relative">
        <motion.svg
          width={size}
          height={size}
          viewBox="0 0 200 200"
          initial="hidden"
          animate="visible"
          className="w-full h-full overflow-visible"
        >
          <motion.circle
            cx="100"
            cy="100"
            r="100"
            stroke="#00CD3C"
            strokeWidth={12}
            variants={draw}
            custom={1}
            fill={"#FFFFFF"}
          />
        </motion.svg>
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

export default CircularProgress;
