import LoginPage from "./pages/LoginPage";
import MainChartPage from "./pages/MainChartPage";
import ProfilePage from "./pages/ProfilePage";
import SignupPage from "./pages/SignupPage";

function App() {
  return (
    <div className="flex justify-center h-screen">
      <div className="flex flex-col w-[1080px] items-center">
        {/* <LoginPage /> */}
        {/* <MainChartPage /> */}
        {/* <SignupPage /> */}
        <ProfilePage />
      </div>
    </div>
  );
}

export default App;
