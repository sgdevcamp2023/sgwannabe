import PlaylistTrack from "./PlaylistTrack";
import { useRef, useState } from "react";
import { useRecoilState, useRecoilValue } from "recoil";
import { nowPlayingList, playingMusic } from "../../state";

function Playlist() {
  const dragItem = useRef<number | null>(null);
  const dragOverItem = useRef<number | null>(null);
  const [isDragging, setIsDragging] = useState<boolean>(false);

  const [list, setList] = useRecoilState(nowPlayingList);
  const nowMusic = useRecoilValue(playingMusic);

  const dragStart = (e: React.DragEvent<HTMLDivElement>, position: number) => {
    dragItem.current = position;
    setIsDragging(true);
  };

  const dragEnter = (e: React.DragEvent<HTMLDivElement>, position: number) => {
    dragOverItem.current = position;

    const newList = [...list];
    const dragItemValue = newList[dragItem.current as number]; // Type assertion here
    newList.splice(dragItem.current as number, 1);
    newList.splice(dragOverItem.current as number, 0, dragItemValue);
    dragItem.current = dragOverItem.current;
    dragOverItem.current = null;
    setList(newList);
  };

  const drop = (e: React.DragEvent<HTMLDivElement>) => {
    dragItem.current = null;
    setIsDragging(false);
  };

  return (
    <div
      style={{
        backgroundImage: `url(${nowMusic.img_url})`,
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
        <div className="overflow-y-scroll max-h-[80vh]">
          {list.map((item, idx) => (
            <div
              key={item.id}
              draggable
              onDragStart={(e) => dragStart(e, idx)}
              onDragEnter={(e) => dragEnter(e, idx)}
              onDragEnd={drop}
              onDragOver={(e) => e.preventDefault()}
              className={`transition duration-200 ${
                dragItem.current === idx && isDragging
                  ? "bg-primary/20 opacity-0"
                  : ""
              }`}
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
