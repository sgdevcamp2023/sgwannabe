import { RouterProvider } from "react-router-dom";
import { router } from "./routes";
import { useRecoilValue } from "recoil";
import { playState } from "./state";

function App() {
  const nowPlaying = useRecoilValue(playState);

  return (
    <div className="flex justify-center h-screen">
      <div className="flex flex-col w-[1200px] items-center relative overflow-y-scroll">
        <RouterProvider router={router} />
      </div>
    </div>
  );
}

export default App;
