package com.lalala.chart.entity.mongo

data class Statistics(
    val likeCount: Int,
    val rankingDifference: Int,
    val differenceType: DifferenceType,
) {
    companion object {
        fun of(
            likeCount: Int,
            rankingDifference: Int,
            differenceType: DifferenceType,
        ): Statistics {
            return Statistics(
                likeCount = likeCount,
                rankingDifference = rankingDifference,
                differenceType = differenceType,
            )
        }
    }
}
