import remove_icon from "../../assets/remove.png";

function AddSongModal() {
  return (
    <div className="absolute flex items-center justify-center w-full h-full bg-gray/50">
      <div className="relative flex flex-col items-center p-10 bg-white w-96 h-96 rounded-3xl">
        <img
          src={remove_icon}
          className="absolute bg-white rounded-full cursor-pointer w-7 h-7 top-5 right-5"
        />
        <div className="text-lg font-600">플레이리스트 추가</div>
        <div>
          <img
            src={
              "https://cdnimg.melon.co.kr/cm2/album/images/113/52/904/11352904_20231027101104_500.jpg/melon/resize/120/quality/80/optimize"
            }
            className="w-[65px]"
          />
        </div>
      </div>
    </div>
  );
}

export default AddSongModal;
