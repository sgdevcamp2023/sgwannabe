import { useEffect, useRef, useState } from "react";
import SockJS from "sockjs-client";
import * as StompJs from "@stomp/stompjs";
import PlaylistComponent from "../components/Chat/PlaylistComponent";
import ChattingComponent from "../components/Chat/ChattingComponent";
import ChattingHeader from "../components/Chat/ChattingHeader";

// const datas = [
//   {
//     content: "asdasd",
//     id: "asdasd",
//     messageType: "asdasd",
//     senderId: 1,
//     nickName: "asdasd",
//   },
// ];

export interface Message {
  content: string;
  id: string;
  messageType: string;
  senderId: number;
  nickName: string;
  senderProfileImage: string;
}

function ChattingPage() {
  const [stompClient, setStompClient] = useState<StompJs.Client>();
  const [messages, setMessages] = useState<Message[]>();

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
      client.subscribe(
        "/chat/topic/room/65cc5c76663b8a66715c3b29",
        handleReceiveMessge
      );
    };

    client.activate();
  };

  const handleReceiveMessge = (data: any) => {
    const parsedMessage = JSON.parse(data.body);
    console.log(parsedMessage);
    setMessages((prevMessages = []) => {
      return [parsedMessage, ...prevMessages];
    });
  };

  const fetchChatHistory = async (client: StompJs.Client) => {
    try {
      const response = await fetch(
        "http://localhost:18000/chat/api/v1/history/65cc5c76663b8a66715c3b29"
      );
      if (!response.ok) {
        throw new Error("Failed to fetch chat history");
      }
      const data = await response.json();
      setMessages(data.data);
      console.log(data);
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
      <ChattingHeader />
      <div className="flex flex-row h-full">
        <PlaylistComponent />
        <ChattingComponent messages={messages} stompClient={stompClient!} />
      </div>
    </div>
  );
}

export default ChattingPage;
