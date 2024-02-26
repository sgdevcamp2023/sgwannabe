import FeedListComponent from "../components/Main/FeedListComponent";
import TopHundredComponent from "../components/Main/TopHundredComponent";
import Header from "../components/shared/Header";

function MainChartPage() {
  return (
    <div className="flex justify-center w-full">
      <div className="flex flex-col w-full">
        <Header />
        <FeedListComponent />
        <TopHundredComponent />
      </div>
    </div>
  );
}

export default MainChartPage;
