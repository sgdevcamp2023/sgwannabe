import play_icon from "../../assets/play_white.png";
import download_icon from "../../assets/download.png";
import plus_icon from "../../assets/plus.png";
import bin_icon from "../../assets/delete.png";
import { PlaylistMusicType } from "../../types/playlist";

interface MusicProps {
  item: PlaylistMusicType;
}

function PlaylistTrack({ item }: MusicProps) {
  const { img_url, title, artist, albumName, time } = item;

  return (
    <tr className="grid items-center w-full grid-flow-col py-2 border-b group border-whiteDark grid-cols-16">
      <td className="col-span-1 item-center">
        <img src={img_url} className="w-[65px]" />
      </td>
      <td className="col-span-4 pl-5">{title}</td>
      <img
        src={play_icon}
        className="w-5 col-span-1 transition-opacity opacity-0 cursor-pointer group-hover:opacity-100"
      />
      <img
        src={plus_icon}
        className="w-5 col-span-1 transition-opacity opacity-0 cursor-pointer group-hover:opacity-100"
      />
      <img
        src={bin_icon}
        className="w-5 col-span-1 transition-opacity opacity-0 cursor-pointer group-hover:opacity-100"
      />
      <td className="col-span-3">{artist}</td>
      <td className="col-span-3">{albumName}</td>
      <td className="col-span-2">{time}</td>
    </tr>
  );
}

export default PlaylistTrack;
