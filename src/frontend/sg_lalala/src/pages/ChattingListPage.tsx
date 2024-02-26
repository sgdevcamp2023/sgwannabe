import { useEffect, useState } from "react";
import * as StompJs from "@stomp/stompjs";
import ChattingListComponent from "../components/Chat/ChattingListComponent";
import Header from "../components/shared/Header";
import chatApi from "../api/chatApi";
import JoinedChattingRoom from "../components/Chat/JoinedChattingRoom";
import { useRecoilState, useRecoilValue } from "recoil";
import { userInfo } from "../state";

export interface PlaylistInfo {
  id: string;
  name: string;
  firstMusic: firstMusic;
  playlistOwnerNickName: string;
}
export interface LastMessage {
  messageId: string;
}
export interface firstMusic {
  artist: string;
  id: number;
  thumbnail: string;
}
export interface PlaylistOwner {
  nickName: string;
  profileImage: string;
  uid: number;
}
export interface ChatRoom {
  id: string;
  roomId?: string;
  musicCount?: number;
  playlist: PlaylistInfo;
  lastMessage?: LastMessage;
  roomName: string;
  thumbnailImage: string;
  userCount: number;
  playlistOwner: PlaylistOwner;
  chatSocket: StompJs.Client;
}

function ChattingListPage() {
  const [chatList, setChatList] = useState<ChatRoom[]>([]);
  const [joinedChatList, setJoinedChatList] = useState<ChatRoom[]>([]);
  const [stomp, setStomp] = useState<StompJs.Client>();
  const user = useRecoilValue(userInfo);

  const connect = async () => {
    const client: StompJs.Client = new StompJs.Client({
      brokerURL: "ws://localhost:18000/ws-chat",
      debug: function (str) {
        console.log(str);
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    client.onStompError = function (frame) {
      console.log("Broker reported error: " + frame.headers["message"]);
      console.log("Additional details: " + frame.body);
    };

    client.onConnect = function (frame) {
      console.log("Connected to WebSocket");
      setStomp(client);
    };

    client.activate();
  };

  useEffect(() => {
    connect();
  }, []);

  useEffect(() => {
    const getChatList = async () => {
      try {
        const chatListData = await chatApi.getChatList({ uid: 1 });
        setChatList(chatListData.data);
        console.log(chatListData.data);
      } catch (error) {
        console.error("채팅 목록 에러 발생:", error);
      }
    };

    const getJoinedChatList = async () => {
      try {
        const chatListData = await chatApi.getJoinedChatList({ uid: 1 });
        setJoinedChatList(chatListData.data);
        console.log("joined", chatListData.data);
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
              lastMessage={room.lastMessage}
              roomName={room.roomName}
              thumbnailImage={
                room.thumbnailImage !== ""
                  ? room.thumbnailImage
                  : "https://image.bugsm.co.kr/album/images/500/40924/4092452.jpg"
              }
              userCount={room.userCount}
              playlistOwner={room.playlistOwner}
              chatSocket={stomp!}
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
            thumbnailImage={
              room.thumbnailImage !== ""
                ? room.thumbnailImage
                : "https://image.bugsm.co.kr/album/images/500/40924/4092452.jpg"
            }
            userCount={room.userCount}
            playlistOwner={room.playlistOwner}
            chatSocket={stomp!}
          />
        ))}
    </div>
  );
}

export default ChattingListPage;
