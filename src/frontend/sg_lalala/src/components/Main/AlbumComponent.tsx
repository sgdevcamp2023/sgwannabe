import { CiPlay1 } from "react-icons/ci";
import { BsPlusLg } from "react-icons/bs";
import { colors } from "../../styles";
import { HiOutlineHeart } from "react-icons/hi";
import { RxTriangleUp } from "react-icons/rx";
import { useNavigate } from "react-router-dom";

function AlbumComponent({ number }: { number: number }) {
  const navigate = useNavigate();
  return (
    <div className="grid items-center grid-flow-col py-3 text-sm border-b-2 text-textGray border-b-whiteDark">
      <div className="col-span-1">
        <input type="checkbox" />
      </div>
      <div className="col-span-1 text-xl text-black">{number}</div>
      <div className="flex flex-row items-center col-span-1">
        <RxTriangleUp size={25} color={colors.up} />
        <div className="text-lg text-up">1</div>
      </div>
      <div className="flex items-center col-span-1">
        <img
          src={
            "https://cdnimg.melon.co.kr/cm2/album/images/113/52/904/11352904_20231027101104_500.jpg/melon/resize/120/quality/80/optimize"
          }
          className="w-[65px] mr-3"
        />
        <div className="flex flex-col col-span-1">
          <div className="text-black">Perfect Night</div>
          <div>LE SSERAFIM (르세라핌)</div>
        </div>
      </div>
      <div className="col-span-2">Perfect Night</div>
      <div className="flex flex-row items-center">
        <HiOutlineHeart />
        <div className="text-xs">77,398</div>
      </div>
      <div className="col-span-1" onClick={() => navigate("/player")}>
        <CiPlay1 size={25} color={colors.primary} />
      </div>
      <div className="col-span-1" onClick={() => navigate("/playlist/now")}>
        <BsPlusLg size={25} />
      </div>
    </div>
  );
}

export default AlbumComponent;
