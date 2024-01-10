package com.lalala.chart.controller.dto

data class CreateChartRequest(
    val timeStamp: Int,
    val records: List<CreateRankingRecordRequest>,
)
