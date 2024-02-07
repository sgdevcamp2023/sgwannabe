function Playlist() {
  const imageUrl =
    "https://cdnimg.melon.co.kr/cm2/album/images/113/23/044/11323044_20230918120653_500.jpg?96485815b31c83574ba34db820882316/melon/resize/282/quality/80/optimize";
  return (
    <div style={{ backgroundImage: `url(${imageUrl})` }}>
      <div className="w-full h-full p-5 backdrop-blur-lg">
        <div>
          <div className="text-white text-xxl font-500">
            축 처진 출근길, 느낌있는 Groove!
          </div>
          <div className="text-sm text-textGray">RNBSOUL 마스터</div>
        </div>
        <div className="flex flex-row items-center justify-between mt-9">
          <div className="flex flex-row items-center">
            <img className="w-10 mr-2" src={imageUrl} alt="Album Cover" />
            <div className="text-sm font-400 text-gray">Lil Boo Thang</div>
          </div>
          <div className="text-sm text-textGray">외 29곡</div>
        </div>
      </div>
    </div>
  );
}

export default Playlist;
