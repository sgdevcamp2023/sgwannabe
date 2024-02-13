package com.lalala.chart.entity.mongo

enum class DifferenceType(
    private val description: String,
) {
    UP("상승"),
    KEEP("유지"),
    DOWN("하락"),
}
