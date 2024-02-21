import { useNavigate } from "react-router-dom";
import { ChatRoom } from "../../pages/ChattingListPage";

function JoinedChattingRoom({
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
      className="bg-cover cursor-pointer h-60 rounded-3xl"
      style={{ backgroundImage: `url(${thumbnailImage})` }}
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
      <div className="h-full p-5 text-black bg-gradient-to-b from-gray/100 to-white/0 rounded-3xl">
        <div className="text-lg font-700">{roomName}</div>
        <div>{userCount}명 방문</div>
      </div>
    </div>
  );
}

export default JoinedChattingRoom;
