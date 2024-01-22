import { createBrowserRouter } from "react-router-dom";
import MainChartPage from "../pages/MainChartPage";
import LoginPage from "../pages/LoginPage";
import SignupPage from "../pages/SignupPage";
import ProfilePage from "../pages/ProfilePage";
import PlaylistPage from "../pages/PlaylistPage";
import PlayerPage from "../pages/PlayerPage";

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
    path: "/profile",
    element: <ProfilePage />,
  },
]);
