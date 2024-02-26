import { useNavigate } from "react-router-dom";
import { ChatRoom } from "../../pages/ChattingListPage";
import { useRecoilValue } from "recoil";
import { userInfo } from "../../state";

function ChattingListComponent({
  id,
  playlist,
  roomName,
  thumbnailImage,
  userCount,
  playlistOwner,
  chatSocket,
}: ChatRoom) {
  const navigate = useNavigate();
  const user = useRecoilValue(userInfo);

  return (
    <div
      className="w-full p-5 mb-4 rounded-md cursor-pointer bg-primaryDark/20"
      onClick={() => {
        navigate(`/chat/${id}`, {
          state: {
            roomId: id,
            playlist: playlist,
            userCount: userCount,
            playlistOwner: playlistOwner,
            join: "join",
          },
        });
      }}
    >
      <div className="flex flex-row">
        <img className="mr-5 w-14" src={thumbnailImage} alt="Album Cover" />
        <div className="flex flex-col">
          <div className="text-lg text-textBlack font-600">{roomName}</div>
          <div className="text-textGray text-md font-400">
            {userCount}명 방문
          </div>
        </div>
      </div>
    </div>
  );
}

export default ChattingListComponent;
