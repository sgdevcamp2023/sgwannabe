import AlbumComponent from "./AlbumComponent";

function TopHundredComponent() {
  return (
    <div>
      <div className="flex flex-col mt-10">
        <div className="text-xxl font-400">TOP 100</div>
        <div className="flex flex-row self-center text-xxl">
          <div className="mr-3">2024.02.23</div>
          <div className="text-primaryDark font-500">14:00</div>
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
          <AlbumComponent number={1} />
          <AlbumComponent number={2} />
          <AlbumComponent number={3} />
          <AlbumComponent number={4} />
          <AlbumComponent number={5} />
          <AlbumComponent number={6} />
          <AlbumComponent number={7} />
          <AlbumComponent number={8} />
          <AlbumComponent number={9} />
          <AlbumComponent number={10} />
          <AlbumComponent number={11} />
          <AlbumComponent number={12} />
          <AlbumComponent number={13} />
        </div>
      </div>
    </div>
  );
}

export default TopHundredComponent;
