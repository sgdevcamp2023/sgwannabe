import { useLocation } from "react-router-dom";
import PlaylistSongComponent from "./PlaylistSongComponent";

export interface Song {
  title: string;
  artist: string;
  thumbnail: string;
}

function PlaylistComponent() {
  const { state } = useLocation();
  return (
    <div className="flex flex-col items-center w-1/2 px-10 py-5">
      {state &&
        state.playlist.musics.map((music: Song, index: number) => (
          <PlaylistSongComponent
            key={index}
            title={music.title}
            artist={music.artist}
            thumbnail={music.thumbnail}
          />
        ))}
    </div>
  );
}

export default PlaylistComponent;
