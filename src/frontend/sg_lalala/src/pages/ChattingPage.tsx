import { useEffect, useState } from "react";
import SockJS from "sockjs-client";
import * as StompJs from "@stomp/stompjs";
import PlaylistComponent from "../components/Chat/PlaylistComponent";
import ChattingComponent from "../components/Chat/ChattingComponent";
import ChattingHeader from "../components/Chat/ChattingHeader";
import { useLocation, useNavigate } from "react-router-dom";
import { useParamsHook } from "../hooks/useParamsHook";
import chatApi from "../api/chatApi";
import { useRecoilValue } from "recoil";
import { userInfo } from "../state";

export interface Message {
  content: string;
  id: string;
  messageType: string;
  senderId: number;
  nickName: string;
  senderProfileImage: string;
}

function ChattingPage() {
  const navigate = useNavigate();
  const { roomId } = useParamsHook<{ roomId: string }>({
    onInValidParams: () => navigate("/chat/list"),
    validateParams: (params) =>
      typeof params?.roomId !== "undefined" &&
      /^([0-9]|[A-Za-z]|[A-Za-z0-9])*$/.test(params.roomId),
  });
  const [stompClient, setStompClient] = useState<StompJs.Client>();
  const [messages, setMessages] = useState<Message[]>();
  const user = useRecoilValue(userInfo);
  const { state } = useLocation();

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
      setStompClient(client);
      fetchChatHistory(client);
      client.subscribe(`/chat/topic/room/${roomId}`, handleReceiveMessge);
      if (state.join === "join") {
        client.publish({
          destination: "/chat/pub/join",
          body: JSON.stringify({
            roomId: roomId,
            senderId: user.id,
            nickName: user.nickName,
            senderProfileImage: user.profile,
          }),
        });
      }
    };

    client.activate();
  };

  const handleReceiveMessge = (data: any) => {
    const parsedMessage = JSON.parse(data.body);
    setMessages((prevMessages = []) => {
      return [parsedMessage, ...prevMessages];
    });
  };

  useEffect(() => {
    const leaveRoom = async () => {
      try {
        const response = await chatApi.putLeaveRoom({
          roomId: roomId,
          uid: user.id,
        });
        console.log(response);
      } catch (error) {
        console.error("채팅방나가기 에러", error);
      }
    };
    leaveRoom();
  }, [location.pathname]);

  const fetchChatHistory = async (client: StompJs.Client) => {
    try {
      const response = await fetch(
        `http://localhost:18000/v1/api/chat/history/${roomId}`
      );
      if (!response.ok) {
        throw new Error("Failed to fetch chat history");
      }
      const data = await response.json();
      setMessages(data.data);
      // console.log(data);
    } catch (error) {
      console.error("Error fetching chat history:", error);
    } finally {
      // client.deactivate();
    }
  };

  useEffect(() => {
    connect();
  }, []);

  return (
    <div className="flex flex-col w-full h-screen bg-black">
      <ChattingHeader stompClient={stompClient!} />
      <div className="flex flex-row h-full">
        <PlaylistComponent />
        <ChattingComponent
          messages={messages}
          stompClient={stompClient!}
          roomId={roomId}
        />
      </div>
    </div>
  );
}

export default ChattingPage;
