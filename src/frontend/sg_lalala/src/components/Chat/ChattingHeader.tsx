import { useLocation, useNavigate } from "react-router-dom";
import * as StompJs from "@stomp/stompjs";
import person_icon from "../../assets/person.png";
import chatApi from "../../api/chatApi";

interface ChattingProps {
  stompClient: StompJs.Client;
}

function ChattingHeader({ stompClient }: ChattingProps) {
  const navigate = useNavigate();
  const { state } = useLocation();
  console.log(state);

  const exitChatRoom = async () => {
    try {
      const response = await chatApi.postExitRoom({
        roomId: state.roomId,
        uid: 2,
      });
      console.log(response);
    } catch (error) {
      console.error("채팅방나가기 에러", error);
    }
  };

  return (
    <div className="w-full">
      <div className="flex flex-row p-2 px-5">
        <div className="flex flex-row justify-between w-1/2">
          <div className="flex flex-row items-center">
            <img
              className="mr-5 w-14"
              src={state.playlist.firstMusic.thumbnail}
              alt="Album Cover"
            />
            <div className="text-lg text-primary font-600">
              {state.playlist.name}
            </div>
          </div>
          <div className="flex flex-row items-center">
            {/* <button className="px-4 py-1 rounded-md bg-primary font-700 text-textBlack">
              팔로잉
            </button> */}
            <div className="mx-4 text-white">
              {state.playlistOwner.nickName}
            </div>
            <img
              src={
                state.playlistOwner.profileImage === ""
                  ? person_icon
                  : state.playlistOwner.profileImage
              }
              className="object-contain w-10 mr-3 bg-white border-2 rounded-full border-primary"
            />
          </div>
        </div>
        <div className="flex flex-row items-center justify-between w-1/2">
          <div className="text-white font-500">{state.userCount}명 방문</div>
          <button
            className="px-4 py-1 rounded-md bg-primary text-textBlack font-700"
            onClick={() => {
              stompClient.deactivate();
              exitChatRoom();
              navigate("/chat/list");
            }}
          >
            나가기
          </button>
        </div>
      </div>
    </div>
  );
}

export default ChattingHeader;
