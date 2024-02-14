function ChattingListComponent() {
  return (
    <div className="w-full p-5 rounded-md bg-primaryDark/20">
      <div className="flex flex-row">
        <img
          className="mr-5 w-14"
          src={
            "https://cdnimg.melon.co.kr/cm2/album/images/113/23/044/11323044_20230918120653_500.jpg?96485815b31c83574ba34db820882316/melon/resize/282/quality/80/optimize"
          }
          alt="Album Cover"
        />
        <div className="flex flex-col">
          <div className="text-lg text-textBlack font-600">
            디모님의 둠칫둠칫 플레이리스트
          </div>
          <div className="text-textGray text-md font-400">20,000명 방문</div>
        </div>
      </div>
    </div>
  );
}

export default ChattingListComponent;
