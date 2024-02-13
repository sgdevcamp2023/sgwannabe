import { atom } from "recoil";
import { isPlayingType } from "../types/backgroundMusic";
import { PlaylistArrayType, PlaylistMusicType } from "../types";

export const isPlaying = atom<isPlayingType>({
  key: "isPlaying",
  default: {
    isPlaying: false,
  },
});

export const playingMusic = atom<PlaylistMusicType>({
  key: "playingMusic",
  default: {
    id: 0,
    img_url:
      "https://cdnimg.melon.co.kr/cm2/album/images/113/52/904/11352904_20231027101104_500.jpg/melon/resize/120/quality/80/optimize",
    title: "",
    artist: "",
    albumName: "",
    time: "",
  },
});

export const nowPlayingList = atom<PlaylistArrayType>({
  key: "nowPlayingList",
  default: [
    {
      id: 1,
      img_url:
        "https://cdnimg.melon.co.kr/cm2/album/images/113/52/904/11352904_20231027101104_500.jpg/melon/resize/120/quality/80/optimize",
      title: "Perfect Night",
      artist: "LE SSERAFIM (르세라핌)",
      albumName: "Perfect Night",
      time: "04:03",
    },
    {
      id: 2,
      img_url:
        "https://cdnimg.melon.co.kr/cm2/album/images/112/15/072/11215072_20230331123824_500.jpg/melon/resize/120/quality/80/optimize",
      title: "Perfect Night",
      artist: "SG 워너비",
      albumName: "Perfect Night",
      time: "04:03",
    },
    {
      id: 3,
      img_url:
        "https://cdnimg.melon.co.kr/cm2/album/images/112/36/264/11236264_20230508184331_500.jpg/melon/resize/120/quality/80/optimize",
      title: "Spicy",
      artist: "라라라",
      albumName: "Perfect Night",
      time: "04:03",
    },
  ],
});
