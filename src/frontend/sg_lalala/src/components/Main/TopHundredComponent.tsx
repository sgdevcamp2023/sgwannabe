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
        <div className="grid grid-flow-col py-5 mt-5 border-t-2 border-b-2 grid-cols-15 text-md text-textBlack border-whiteDark">
          <div className="col-span-1">
            <input type="checkbox" />
          </div>
          <div className="col-span-2">순위</div>
          <div className="col-span-2"></div>
          <div className="col-span-3">곡정보</div>
          <div className="col-span-3">앨범</div>
          <div className="col-span-1">좋아요</div>
          <div className="col-span-1">듣기</div>
          <div className="col-span-1">담기</div>
        </div>
        <div className="grid">
          <AlbumComponent />
          <AlbumComponent />
        </div>
      </div>
    </div>
  );
}

export default TopHundredComponent;
