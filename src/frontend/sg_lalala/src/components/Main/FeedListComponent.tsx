import { useNavigate } from "react-router-dom";
import FeedSlide from "./FeedSlide";
import { FiChevronRight } from "react-icons/fi";

function FeedListComponent() {
  const navigate = useNavigate();
  return (
    <div>
      <div className="flex flex-row items-center mb-3 cursor-pointer">
        <div
          className="mr-2 text-xl text-black font-400"
          onClick={() => navigate("/chat/list")}
        >
          오늘의 <span className="font-700 text-primary">Groove!</span>
        </div>
        <FiChevronRight size={25} />
      </div>
      <FeedSlide />
    </div>
  );
}

export default FeedListComponent;
