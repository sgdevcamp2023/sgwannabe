import { createBrowserRouter } from "react-router-dom";
import MainChartPage from "../pages/MainChartPage";
import LoginPage from "../pages/LoginPage";
import SignupPage from "../pages/SignupPage";
import ProfilePage from "../pages/ProfilePage";
import PlaylistPage from "../pages/PlaylistPage";
import PlayerPage from "../pages/PlayerPage";
import ChattingListPage from "../pages/ChattingListPage";
import ChattingPage from "../pages/ChattingPage";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <MainChartPage />,
  },
  {
    path: "/login",
    element: <LoginPage />,
  },
  {
    path: "/signup",
    element: <SignupPage />,
  },
  {
    path: "/playlist/now",
    element: <PlaylistPage />,
  },
  {
    path: "/player",
    element: <PlayerPage />,
  },
  {
    path: "/chat/list",
    element: <ChattingListPage />,
  },
  {
    path: "/chat/:roomId",
    element: <ChattingPage />,
  },
  {
    path: "/profile",
    element: <ProfilePage />,
  },
]);
