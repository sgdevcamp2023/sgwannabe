import person_icon from "../../assets/person.png";
import { Message } from "../../pages/ChattingPage";

function MessageComponent({
  content,
  nickName,
  senderProfileImage,
  messageType,
}: Message) {
  return (
    <div className="flex flex-row items-center w-full my-2 text-md font-500">
      {messageType === "MSG" ? (
        <>
          <img
            src={senderProfileImage === "" ? person_icon : senderProfileImage}
            className="object-cover w-8 h-8 mr-3 bg-white border-2 rounded-full border-primary"
          />
          <div className="mr-3 text-primary">{nickName}</div>
          <div className="text-white">{content}</div>
        </>
      ) : (
        <div className="flex justify-center w-full">
          <div className="flex text-sm text-white">
            {nickName}님이 들어왔습니다.
          </div>
        </div>
      )}
    </div>
  );
}

export default MessageComponent;
