import { useEffect, useState } from "react";
import Feed from "./Feed";
import chatApi from "../../api/chatApi";
import { ChatRoom } from "../../pages/ChattingListPage";

function FeedSlide() {
  const [recommendRooms, setRecommendRooms] = useState<ChatRoom[]>();

  const getAllRooms = async () => {
    try {
      const response = await chatApi.getAllChatList();
      const data = response.data.slice(0, 2);
      console.log(response);
      setRecommendRooms(data);
    } catch (error) {
      console.error("모든 채팅방 부르기 에러", error);
    }
  };

  useEffect(() => {
    getAllRooms();
  }, []);

  return (
    <div className="flex flex-row justify-evenly">
      {recommendRooms &&
        recommendRooms?.map((room, index) => (
          <Feed
            key={index}
            musicCount={room.musicCount}
            roomName={room.playlist.name}
            thumbnail={
              room.playlist.firstMusic.thumbnail !== ""
                ? room.playlist.firstMusic.thumbnail
                : "https://image.bugsm.co.kr/album/images/500/40924/4092452.jpg"
            }
            artist={room.playlist.firstMusic.artist}
            playlistOwner={room.playlist.playlistOwnerNickName}
          />
        ))}
    </div>
  );
}

export default FeedSlide;
