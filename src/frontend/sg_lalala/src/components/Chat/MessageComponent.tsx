import person_icon from "../../assets/person.png";

interface ChattingProps {
  content: string;
  id: string;
  messageType: string;
  senderId: number;
  nickName: string;
  senderProfileImage: string;
}

function MessageComponent({
  content,
  nickName,
  senderProfileImage,
}: ChattingProps) {
  return (
    <div className="flex flex-row items-center w-full my-2 text-md font-500">
      <img
        src={senderProfileImage === "" ? person_icon : senderProfileImage}
        className="object-contain w-8 mr-3 bg-white border-2 rounded-full border-primary"
      />
      <div className="mr-3 text-primary">{nickName}</div>
      <div className="text-white">{content}</div>
    </div>
  );
}

export default MessageComponent;
