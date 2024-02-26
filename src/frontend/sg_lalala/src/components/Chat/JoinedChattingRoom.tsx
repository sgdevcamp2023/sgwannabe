import { useNavigate } from "react-router-dom";
import { ChatRoom } from "../../pages/ChattingListPage";
import chatApi from "../../api/chatApi";
import { useState } from "react";
import { useRecoilState } from "recoil";
import { playingMusicId } from "../../state";

function JoinedChattingRoom({
  id,
  playlist,
  roomName,
  thumbnailImage,
  userCount,
  lastMessage,
  playlistOwner,
}: ChatRoom) {
  const navigate = useNavigate();
  const [musicId, setMusicId] = useRecoilState(playingMusicId);

  const reJoin = async () => {
    try {
      const rejoinData = await chatApi.getRejoin({
        roomId: id,
        readMsgId: lastMessage?.messageId,
      });
      console.log(rejoinData);
      setMusicId(rejoinData.data.currentMusicId);
    } catch (error) {
      console.error("채팅 목록 에러 발생:", error);
    }
  };

  return (
    <div
      className="bg-cover cursor-pointer h-60 rounded-3xl"
      style={{ backgroundImage: `url(${thumbnailImage})` }}
      onClick={() => {
        reJoin();
        navigate(`/chat/${id}`, {
          state: {
            playlist: playlist,
            userCount: userCount,
            playlistOwner: playlistOwner,
            roomId: id,
            join: "rejoin",
          },
        });
      }}
    >
      <div className="h-full p-5 text-black bg-gradient-to-b from-gray/100 to-white/0 rounded-3xl">
        <div className="text-lg font-700">{roomName}</div>
        <div>{userCount}명 방문</div>
      </div>
    </div>
  );
}

export default JoinedChattingRoom;
