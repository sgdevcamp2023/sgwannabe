package com.lalala.chart.entity.mongo;

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "charts")
data class ChartEntity(
    @Id
    var id: String? = null,
    val timeStamp: Int,
    val records: List<RankingRecord> = mutableListOf(),
) : BaseTimeEntity()
