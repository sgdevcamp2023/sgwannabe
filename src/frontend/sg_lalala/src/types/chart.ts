export type ChartType = {
  timeStamp: number;
  records: {
    id: string;
    ranking: number;
    albumId: number;
    albumName: string;
    albumCoverUrl: string;
    musicId: number;
    musicTitle: string;
    artistId: number;
    artistName: string;
    likeCount: number;
    rankingDifference: number;
    differenceType: string;
  }[];
};
