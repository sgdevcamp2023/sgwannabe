import { useEffect, useState } from "react";
import ChattingListComponent from "../components/Chat/ChattingListComponent";
import Header from "../components/shared/Header";
import chatApi from "../api/chatApi";
import JoinedChattingRoom from "../components/Chat/JoinedChattingRoom";

export interface PlaylistInfo {
  id: string;
  name: string;
}

export interface PlaylistOwner {
  nickName: string;
  profileImage: string;
  uid: number;
}
export interface ChatRoom {
  id: string;
  playlist: PlaylistInfo;
  roomName: string;
  thumbnailImage: string;
  userCount: number;
  playlistOwner: PlaylistOwner;
}

function ChattingListPage() {
  const [chatList, setChatList] = useState<ChatRoom[]>([]);
  const [joinedChatList, setJoinedChatList] = useState<ChatRoom[]>([]);

  useEffect(() => {
    const getChatList = async () => {
      try {
        const chatListData = await chatApi.getChatList({ uid: 3 });
        setChatList(chatListData.data);
        // console.log(chatList);
      } catch (error) {
        console.error("채팅 목록 에러 발생:", error);
      }
    };

    const getJoinedChatList = async () => {
      try {
        const chatListData = await chatApi.getJoinedChatList({ uid: 3 });
        setJoinedChatList(chatListData.data);
        console.log("joined", joinedChatList);
      } catch (error) {
        console.error("채팅 목록 에러 발생:", error);
      }
    };
    getChatList();
    getJoinedChatList();
  }, []);

  return (
    <div className="w-full">
      <Header />
      <div className="grid grid-cols-4 gap-3 mb-5">
        {joinedChatList &&
          joinedChatList.map((room, index) => (
            <JoinedChattingRoom
              key={index}
              id={room.id}
              playlist={room.playlist}
              roomName={room.roomName}
              thumbnailImage={room.thumbnailImage}
              userCount={room.userCount}
              playlistOwner={room.playlistOwner}
            />
          ))}
      </div>
      {chatList &&
        chatList.map((room, index) => (
          <ChattingListComponent
            key={index}
            id={room.id}
            playlist={room.playlist}
            roomName={room.roomName}
            thumbnailImage={room.thumbnailImage}
            userCount={room.userCount}
            playlistOwner={room.playlistOwner}
          />
        ))}
    </div>
  );
}

export default ChattingListPage;
