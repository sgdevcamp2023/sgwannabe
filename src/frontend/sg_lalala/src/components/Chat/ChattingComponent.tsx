import { useState } from "react";
import * as StompJs from "@stomp/stompjs";
import MessageComponent from "./MessageComponent";
import { Message } from "../../pages/ChattingPage";

interface ChattingProps {
  messages?: Message[];
  stompClient: StompJs.Client;
}

function ChattingComponent({ messages = [], stompClient }: ChattingProps) {
  const [sendingMessage, setSendingMessage] = useState<string>("");

  const handleMessageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSendingMessage(e.target.value);
  };

  const handleKeyPress = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Enter" && e.nativeEvent.isComposing === false) {
      if (sendingMessage !== "") {
        stompClient.publish({
          destination: "/chat/pub/send",
          body: JSON.stringify({
            messageType: "MSG",
            roomId: "65cc5c76663b8a66715c3b29",
            senderId: 1,
            nickName: "유저1",
            content: sendingMessage,
            senderProfileImage: "",
          }),
        });
        setSendingMessage("");
      } else {
        alert("메시지를 입력해주세요");
      }
    }
  };

  return (
    <div className="relative w-1/2 h-full max-h-screen overflow-x-hidden">
      <div className="px-2">
        <div
          className="flex flex-col-reverse overflow-y-auto"
          style={{ height: "calc(100vh - 8rem)" }}
        >
          {messages &&
            messages.map((message, index) => (
              <MessageComponent
                key={index}
                content={message.content}
                id={message.id}
                messageType={message.messageType}
                senderId={message.senderId}
                nickName={message.nickName}
                senderProfileImage={message.senderProfileImage}
              />
            ))}
        </div>
        <div className="sticky bottom-0 flex w-full py-2 text-white">
          <input
            className="flex-1 p-2 pl-4 rounded-xl focus:outline-none bg-[#3C3C3C]"
            placeholder="Type your message here"
            value={sendingMessage}
            onChange={handleMessageChange}
            onKeyDown={handleKeyPress}
          />
        </div>
      </div>
    </div>
  );
}

export default ChattingComponent;
