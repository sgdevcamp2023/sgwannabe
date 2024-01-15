import FeedListComponent from "../components/Main/FeedListComponent";
import TopHundredComponent from "../components/Main/TopHundredComponent";
import Header from "../components/shared/Header";

function MainChartPage() {
  return (
    <div className="flex justify-center">
      <div className="flex flex-col w-[1080px]">
        <Header />
        <FeedListComponent />
        <TopHundredComponent />
      </div>
    </div>
  );
}

export default MainChartPage;
