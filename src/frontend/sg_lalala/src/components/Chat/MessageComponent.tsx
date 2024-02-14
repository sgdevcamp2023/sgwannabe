import person_icon from "../../assets/person.png";
import { Message } from "../../pages/ChattingPage";

function MessageComponent({ content, nickName, senderProfileImage }: Message) {
  return (
    <div className="flex flex-row items-center w-full my-2 text-md font-500">
      <img
        src={senderProfileImage === "" ? person_icon : senderProfileImage}
        className="object-cover w-8 h-8 mr-3 bg-white border-2 rounded-full border-primary"
      />
      <div className="mr-3 text-primary">{nickName}</div>
      <div className="text-white">{content}</div>
    </div>
  );
}

export default MessageComponent;
