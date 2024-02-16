import { useNavigate } from "react-router-dom";
import { ChatRoom } from "../../pages/ChattingListPage";

function ChattingListComponent({
  id,
  playlist,
  roomName,
  thumbnailImage,
  userCount,
  playlistOwner,
}: ChatRoom) {
  const navigate = useNavigate();
  return (
    <div
      className="w-full p-5 mb-4 rounded-md cursor-pointer bg-primaryDark/20"
      onClick={() =>
        navigate(`/chat/${id}`, {
          state: {
            playlist: playlist,
            userCount: userCount,
            playlistOwner: playlistOwner,
          },
        })
      }
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
