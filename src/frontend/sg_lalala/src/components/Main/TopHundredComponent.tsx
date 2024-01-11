import AlbumComponent from "./AlbumComponent";

function TopHundredComponent() {
  return (
    <div>
      <div className="flex flex-col mt-10">
        <div className="text-xxl font-400">TOP 100</div>
        <div className="flex flex-row self-center text-xxl">
          <div className="mr-3">2024.01.09</div>
          <div className="text-primaryDark font-500">15:00</div>
        </div>
        <div className="flex flex-row justify-between py-5 mt-5 border-t-2 border-b-2 text-md text-textBlack border-whiteDark">
          <input type="checkbox" />
          <div>순위</div>
          <div>곡정보</div>
          <div>앨범</div>
          <div>좋아요</div>
          <div>듣기</div>
          <div>담기</div>
        </div>
        <div className="flex-col">
          <AlbumComponent />
          <AlbumComponent />
        </div>
      </div>
    </div>
  );
}

export default TopHundredComponent;
