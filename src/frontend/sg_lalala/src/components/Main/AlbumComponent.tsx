import { CiPlay1 } from "react-icons/ci";
import { BsPlusLg } from "react-icons/bs";
import { colors } from "../../styles";
import { HiOutlineHeart, HiHeart } from "react-icons/hi";
import { RxTriangleUp, RxTriangleDown } from "react-icons/rx";

function AlbumComponent() {
  return (
    <div className="flex flex-row w-full text-sm text-textGray items-center justify-between py-3 border-b-2 border-b-whiteDark">
      <input type="checkbox" />
      <div className="text-black text-xl">1</div>
      <div className="flex flex-row items-center">
        <RxTriangleUp size={25} color={colors.up} />
        <div className="text-up text-lg">1</div>
      </div>
      <img
        src={
          "https://cdnimg.melon.co.kr/cm2/album/images/113/52/904/11352904_20231027101104_500.jpg/melon/resize/120/quality/80/optimize"
        }
        className="w-[65px]"
      />
      <div className="flex flex-col">
        <div className="text-black">Perfect Night</div>
        <div>LE SSERAFIM (르세라핌)</div>
      </div>
      <div>Perfect Night</div>
      <div className="flex flex-row items-center">
        <HiOutlineHeart />
        <div className="text-xs">77,398</div>
      </div>
      <CiPlay1 size={25} color={colors.primary} />
      <BsPlusLg size={25} />
    </div>
  );
}

export default AlbumComponent;
