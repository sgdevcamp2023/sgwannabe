import Header from "../components/shared/Header";
import Playlist from "../components/Profile/Playlist";
import person_icon from "../assets/person.png";
import remove_icon from "../assets/remove.png";
import { useRef, useState } from "react";
import { useRecoilValue } from "recoil";
import { userInfo } from "../state";

function ProfilePage() {
  const imgRef = useRef<HTMLInputElement>(null);
  const [imgFile, setImgFile] = useState<string>("");
  const user = useRecoilValue(userInfo);

  const saveImgFile = () => {
    const file = imgRef.current?.files?.[0];

    if (file) {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onloadend = () => {
        const result = reader.result as string;
        setImgFile(result);
      };
    }
  };

  return (
    <div className="w-full">
      <Header />
      <div className="flex flex-row items-center p-10 rounded-xl bg-gray/30">
        <div className="relative w-24 h-24 rounded-full group hover:bg-gray/30">
          {imgFile && (
            <img
              src={remove_icon}
              className="absolute top-0 right-0 z-50 w-5 h-5 bg-white rounded-full cursor-pointer"
              onClick={() => setImgFile("")}
            />
          )}
          <img
            src={imgFile ? imgFile : person_icon}
            className="w-full h-full bg-white rounded-full group-hover:opacity-20"
          />
          <label className="absolute inset-0 flex items-center justify-center text-lg text-center transition-opacity opacity-0 cursor-pointer text-textBlack font-600 hover:opacity-100">
            {imgFile ? "변경" : "업로드"}
            <input
              type="file"
              accept="image/*"
              className="hidden"
              onChange={saveImgFile}
              ref={imgRef}
            />
          </label>
        </div>
        <div className="ml-5">
          <div className="text-xl text-black font-600">{user.nickName}</div>
          <div className="text-textGray">{user.nickName}@gmail.com</div>
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
