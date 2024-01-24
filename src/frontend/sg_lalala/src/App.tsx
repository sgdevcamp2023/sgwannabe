import { RouterProvider } from "react-router-dom";
import { router } from "./routes";

function App() {
  return (
    <div className="flex justify-center h-screen">
      <div className="flex flex-col w-[1080px] items-center">
        <RouterProvider router={router} />
      </div>
    </div>
  );
}

export default App;
