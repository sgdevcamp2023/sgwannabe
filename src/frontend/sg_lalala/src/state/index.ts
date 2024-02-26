import { atom } from "recoil";
import { playStateType } from "../types/backgroundMusic";
import { PlaylistArrayType, PlaylistMusicType, UserInfoType } from "../types";

export const userInfo = atom<UserInfoType>({
  key: "userInfo",
  default: {
    id: 0,
    nickName: "",
    profile: "",
  },
});

export const playingMusicId = atom<number>({
  key: "playingMusicId",
  default: 1,
});

export const playState = atom<playStateType>({
  key: "playState",
  default: {
    backgroundPlaying: true,
    isPlaying: true,
  },
});

export const playingMusic = atom<PlaylistMusicType>({
  key: "playingMusic",
  default: {
    id: 1,
    img_url: "https://image.bugsm.co.kr/album/images/500/40924/4092452.jpg",
    title: "Perfect Night",
    artist: "LE SSERAFIM (르세라핌)",
    albumName: "Perfect Night",
    time: "04:03",
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
      title: "smile gate",
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
    {
      id: 4,
      img_url:
        "https://cdnimg.melon.co.kr/cm2/album/images/113/52/904/11352904_20231027101104_500.jpg/melon/resize/120/quality/80/optimize",
      title: "Perfect Night",
      artist: "LE SSERAFIM (르세라핌)",
      albumName: "Perfect Night",
      time: "04:03",
    },
    {
      id: 5,
      img_url:
        "https://cdnimg.melon.co.kr/cm2/album/images/112/15/072/11215072_20230331123824_500.jpg/melon/resize/120/quality/80/optimize",
      title: "smile gate",
      artist: "SG 워너비",
      albumName: "Perfect Night",
      time: "04:03",
    },
    {
      id: 6,
      img_url:
        "https://cdnimg.melon.co.kr/cm2/album/images/112/36/264/11236264_20230508184331_500.jpg/melon/resize/120/quality/80/optimize",
      title: "Spicy",
      artist: "라라라",
      albumName: "Perfect Night",
      time: "04:03",
    },
    {
      id: 7,
      img_url:
        "https://cdnimg.melon.co.kr/cm2/album/images/113/52/904/11352904_20231027101104_500.jpg/melon/resize/120/quality/80/optimize",
      title: "Perfect Night",
      artist: "LE SSERAFIM (르세라핌)",
      albumName: "Perfect Night",
      time: "04:03",
    },
    {
      id: 8,
      img_url:
        "https://cdnimg.melon.co.kr/cm2/album/images/112/15/072/11215072_20230331123824_500.jpg/melon/resize/120/quality/80/optimize",
      title: "smile gate",
      artist: "SG 워너비",
      albumName: "Perfect Night",
      time: "04:03",
    },
    {
      id: 9,
      img_url:
        "https://cdnimg.melon.co.kr/cm2/album/images/112/36/264/11236264_20230508184331_500.jpg/melon/resize/120/quality/80/optimize",
      title: "Spicy",
      artist: "라라라",
      albumName: "Perfect Night",
      time: "04:03",
    },
    {
      id: 10,
      img_url:
        "https://cdnimg.melon.co.kr/cm2/album/images/113/52/904/11352904_20231027101104_500.jpg/melon/resize/120/quality/80/optimize",
      title: "Perfect Night",
      artist: "LE SSERAFIM (르세라핌)",
      albumName: "Perfect Night",
      time: "04:03",
    },
    {
      id: 11,
      img_url:
        "https://cdnimg.melon.co.kr/cm2/album/images/112/15/072/11215072_20230331123824_500.jpg/melon/resize/120/quality/80/optimize",
      title: "smile gate",
      artist: "SG 워너비",
      albumName: "Perfect Night",
      time: "04:03",
    },
    {
      id: 12,
      img_url:
        "https://cdnimg.melon.co.kr/cm2/album/images/112/36/264/11236264_20230508184331_500.jpg/melon/resize/120/quality/80/optimize",
      title: "Spicy",
      artist: "라라라",
      albumName: "Perfect Night",
      time: "04:03",
    },
  ],
});
