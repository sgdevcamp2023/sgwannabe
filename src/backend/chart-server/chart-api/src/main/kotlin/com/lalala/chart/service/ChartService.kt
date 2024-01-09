package com.lalala.chart.service

import com.lalala.chart.controller.dto.CreateRankingRecordRequest
import com.lalala.chart.entity.mongo.*
import com.lalala.chart.exception.NotFoundException
import com.lalala.chart.repository.ChartRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.util.*

@Service
class ChartService(
    private val chartRepository: ChartRepository,
) {
    fun getChart(timeStamp: Int?): Mono<ChartEntity> {
        return if (timeStamp == null) {
            getLatestChart()
        } else {
            getChartByTimeStamp(timeStamp)
        }
    }

    fun getLatestChart(): Mono<ChartEntity> =
        chartRepository.findTopByOrderByTimeStampDesc()

    fun getChartByTimeStamp(timeStamp: Int): Mono<ChartEntity> =
        chartRepository.findByTimeStamp(timeStamp)
            .switchIfEmpty { throw NotFoundException("차트를 조회할 수 없습니다.") }

    fun createChart(timeStamp: Int, records: List<CreateRankingRecordRequest>): Mono<ChartEntity> {
        val chart = ChartEntity(
            timeStamp = timeStamp,
            records = records.map {
                RankingRecord(
                    id = UUID.randomUUID().toString(),
                    ranking = it.ranking,
                    album = Album.of(it.albumId, it.albumName, it.albumCoverUrl),
                    music = Music.of(it.musicId, it.musicTitle),
                    artist = Artist.of(it.artistId, it.artistName),
                    statistics = Statistics.of(it.likeCount, it.rankingDifference, it.differenceType),
                )
            }
        )
        return chartRepository.insert(chart)
    }
}
