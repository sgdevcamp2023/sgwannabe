package com.lalala.chart.controller.dto

import com.lalala.chart.entity.mongo.ChartEntity

data class ChartResponse(
    val timeStamp: Int,
    val records: List<RankingRecordResponse>,
) {
    companion object {
        fun of(chart: ChartEntity) =
            ChartResponse(
                timeStamp = chart.timeStamp,
                records = chart.records.map { RankingRecordResponse.of(it) },
            )
    }
}
