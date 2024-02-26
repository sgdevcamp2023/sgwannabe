import { useNavigate } from "react-router-dom";

function Feed({
  roomName,
  thumbnail,
  artist,
  playlistOwner,
  musicCount,
}: {
  roomName: string;
  thumbnail: string;
  artist: string;
  playlistOwner: string;
  musicCount: number | undefined;
}) {
  const navigate = useNavigate();

  return (
    <div
      style={{ backgroundImage: `url(${thumbnail})` }}
      className={`w-5/12 flex flex-col rounded-2xl bg-cover bg-center cursor-pointer`}
    >
      <div className="w-full h-full p-5 backdrop-blur-md">
        <div className="w-10/12 ">
          <div className="text-white text-xxl font-500">{roomName}</div>
          <div className="text-sm text-textGray">{playlistOwner}</div>
        </div>
        <div className="flex flex-row items-center justify-between mt-9">
          <div className="flex flex-row items-center">
            <img className="w-10 mr-2" src={thumbnail} alt="Album Cover" />
            <div className="text-sm font-400 text-gray">{artist}</div>
          </div>
          <div className="text-sm text-textGray">{musicCount}곡</div>
        </div>
      </div>
    </div>
  );
}

export default Feed;
