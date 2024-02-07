import FeedSlide from "./FeedSlide";
import { FiChevronRight } from "react-icons/fi";

function FeedListComponent() {
  return (
    <div>
      <div className="flex flex-row items-center mb-3 cursor-pointer">
        <div className="mr-2 text-xl text-black font-400">
          오늘의 플레이리스트
        </div>
        <FiChevronRight size={25} />
      </div>
      <FeedSlide />
    </div>
  );
}

export default FeedListComponent;
