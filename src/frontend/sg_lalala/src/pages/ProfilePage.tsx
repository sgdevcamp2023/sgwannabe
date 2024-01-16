import Header from "../components/shared/Header";
import { FaUserCircle } from "react-icons/fa";
import { colors } from "../styles";
import Playlist from "../components/Profile/Playlist";

function ProfilePage() {
  return (
    <div className="w-full">
      <Header />
      <div className="flex flex-row items-center p-10 rounded-xl bg-gray/30">
        <div className="flex flex-col items-center">
          <FaUserCircle size="100" color={colors.gray} />
          <label className="w-full py-1 my-2 text-sm text-center cursor-pointer rounded-xl bg-primaryDark/20 text-textBlack">
            이미지 업로드
            <input type="file" accept="image/*" className="hidden" />
          </label>
          <div className="w-full py-1 text-sm text-center cursor-pointer text-textBlack rounded-xl bg-primaryDark/20">
            이미지 제거
          </div>
        </div>
        <div className="ml-5">
          <div className="text-xl text-black font-600">콘sy1te</div>
          <div className="text-textGray">gmail@gmail.com</div>
        </div>
      </div>
      <div className="flex flex-col mt-3">
        <div className="text-xl font-600 text-primaryDark">플레이리스트</div>
        <div className="grid justify-center grid-cols-4 gap-3 mt-3">
          <Playlist />
          <Playlist />
          <Playlist />
          <Playlist />
          <Playlist />
        </div>
      </div>
    </div>
  );
}

export default ProfilePage;
