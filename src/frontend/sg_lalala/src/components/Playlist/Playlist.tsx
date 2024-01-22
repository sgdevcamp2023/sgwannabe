import PlaylistTrack from "./PlaylistTrack";
import data from "../../data/playlist_data.json";
import { useRef, useState } from "react";
import { PlaylistArrayType } from "../../types/playlist";

function Playlist() {
  const dragItem = useRef<number | null>(null);
  const dragOverItem = useRef<number | null>(null);
  const [list, setList] = useState<PlaylistArrayType>([
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
        "https://cdnimg.melon.co.kr/cm2/album/images/113/52/904/11352904_20231027101104_500.jpg/melon/resize/120/quality/80/optimize",
      title: "Perfect Night",
      artist: "SG 워너비",
      albumName: "Perfect Night",
      time: "04:03",
    },
    {
      id: 3,
      img_url:
        "https://cdnimg.melon.co.kr/cm2/album/images/113/52/904/11352904_20231027101104_500.jpg/melon/resize/120/quality/80/optimize",
      title: "Perfect Night",
      artist: "라라라",
      albumName: "Perfect Night",
      time: "04:03",
    },
  ]);

  const dragStart = (e: React.DragEvent<HTMLDivElement>, position: number) => {
    dragItem.current = position;
    console.log(e.target);
  };

  const dragEnter = (e: React.DragEvent<HTMLDivElement>, position: number) => {
    dragOverItem.current = position;
    console.log(e.target);
  };

  const drop = (e: React.DragEvent<HTMLDivElement>) => {
    if (dragItem.current === null || dragOverItem.current === null) return;

    const newList = [...list];
    const dragItemValue = newList[dragItem.current as number]; // Type assertion here
    newList.splice(dragItem.current as number, 1);
    newList.splice(dragOverItem.current as number, 0, dragItemValue);
    dragItem.current = null;
    dragOverItem.current = null;
    setList(newList);
  };

  return (
    <div
      style={{
        backgroundImage: `url(${"https://cdnimg.melon.co.kr/cm2/album/images/113/52/904/11352904_20231027101104_500.jpg/melon/resize/120/quality/80/optimize"})`,
      }}
      className="flex flex-col w-full h-screen bg-cover"
    >
      <div className="w-full h-screen px-3 text-white backdrop-blur-3xl">
        <div className="py-5 text-xxl font-500">재생목록</div>
        <tr className="grid grid-flow-col py-3 text-lg text-left border-b-2 grid-cols-16 border-whiteDark">
          <th className="col-span-1"></th>
          <th className="col-span-7 pl-5">곡명</th>
          <th className="col-span-3">아티스트명</th>
          <th className="col-span-3">앨범명</th>
          <th className="col-span-2">재생시간</th>
        </tr>
        <div>
          {list.map((item, idx) => (
            <div
              key={idx}
              draggable
              onDragStart={(e) => dragStart(e, idx)}
              onDragEnter={(e) => dragEnter(e, idx)}
              onDragEnd={drop}
              onDragOver={(e) => e.preventDefault()}
            >
              <PlaylistTrack item={item} />
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default Playlist;
