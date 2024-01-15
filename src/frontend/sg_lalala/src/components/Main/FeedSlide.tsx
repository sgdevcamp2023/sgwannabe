import { useState } from "react";
import Feed from "./Feed";

interface IImageList {
  images: { id: number; url: string }[];
  index: number;
}

function FeedSlide() {
  // const [current, setCurrent] = useState(imageList.index);

  // const moveStyle: { [key: number]: string } = {
  //   0: "translate-x-0",
  //   1: "translate-x-[-100vw]",
  //   2: "translate-x-[-200vw]",
  //   3: "translate-x-[-300vw]",
  //   4: "translate-x-[-400vw]",
  //   5: "translate-x-[-500vw]",
  //   6: "translate-x-[-600vw]",
  //   7: "translate-x-[-700vw]",
  //   8: "translate-x-[-800vw]",
  //   9: "translate-x-[-900vw]",
  //   10: "translate-x-[-1000vw]",
  // };

  // const nextHandler = () => {
  //   setCurrent(() => {
  //     if (current === imageList.images.length - 1) {
  //       return 0;
  //     } else {
  //       return current + 1;
  //     }
  //   });
  // };

  // const prevHandler = () => {
  //   setCurrent(() => {
  //     if (current === 0) {
  //       return imageList.images.length - 1;
  //     } else {
  //       return current - 1;
  //     }
  //   });
  // };

  return (
    <div className="flex flex-row justify-evenly">
      <Feed />
      <Feed />
    </div>
  );
}

export default FeedSlide;
