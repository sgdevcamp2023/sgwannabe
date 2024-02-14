import person_icon from "../../assets/person.png";

function ChattingHeader() {
  return (
    <div className="w-full">
      <div className="flex flex-row p-2">
        <div className="flex flex-row justify-between w-1/2">
          <div className="flex flex-row items-center">
            <img
              className="mr-5 w-14"
              src={
                "https://cdnimg.melon.co.kr/cm2/album/images/113/23/044/11323044_20230918120653_500.jpg?96485815b31c83574ba34db820882316/melon/resize/282/quality/80/optimize"
              }
              alt="Album Cover"
            />
            <div className="text-lg text-primary font-600">
              디모님의 둠칫둠칫 플레이리스트
            </div>
          </div>
          <div className="flex flex-row items-center">
            <button className="px-4 py-1 rounded-md bg-primary font-700 text-textBlack">
              팔로잉
            </button>
            <div className="mx-4 text-white">디모</div>
            <img
              src={person_icon}
              className="object-contain w-10 mr-3 bg-white border-2 rounded-full border-primary"
            />
          </div>
        </div>
        <div className="flex flex-row items-center justify-between w-1/2 px-2">
          <div className="text-white font-500">1000명 방문</div>
          <button className="px-4 py-1 rounded-md bg-primary text-textBlack font-700">
            나가기
          </button>
        </div>
      </div>
    </div>
  );
}

export default ChattingHeader;
