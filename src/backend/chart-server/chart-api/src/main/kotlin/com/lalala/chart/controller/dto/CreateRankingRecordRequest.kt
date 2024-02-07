package com.lalala.chart.controller.dto

import com.lalala.chart.entity.mongo.DifferenceType
import com.lalala.chart.entity.mongo.RankingRecord

data class CreateRankingRecordRequest(
    val ranking: Int,
    val albumId: Int,
    val albumName: String,
    val albumCoverUrl: String,
    val musicId: Int,
    val musicTitle: String,
    val artistId: Int,
    val artistName: String,
    val likeCount: Int,
    val rankingDifference: Int,
    val differenceType: DifferenceType,
) {
    companion object {
        fun of(record: RankingRecord) =
            CreateRankingRecordRequest(
                ranking = record.ranking,
                albumId = record.album.albumId,
                albumName = record.album.albumName,
                albumCoverUrl = record.album.albumCoverUrl,
                musicId = record.music.musicId,
                musicTitle = record.music.musicTitle,
                artistId = record.artist.artistId,
                artistName = record.artist.artistName,
                likeCount = record.statistics.likeCount,
                rankingDifference = record.statistics.rankingDifference,
                differenceType = record.statistics.differenceType,
            )
    }
}
