import LoginPage from "./pages/LoginPage";
import MainChartPage from "./pages/MainChartPage";

function App() {
  return (
    <div className="flex justify-center h-screen">
      <div className="flex flex-col w-[1080px] items-center">
        <LoginPage />
        {/* <MainChartPage /> */}
      </div>
    </div>
  );
}

export default App;
