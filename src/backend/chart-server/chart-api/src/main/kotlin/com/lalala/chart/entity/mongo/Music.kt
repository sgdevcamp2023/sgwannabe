package com.lalala.chart.entity.mongo

data class Music(
    val musicId: Int,
    val musicTitle: String,
) {
    companion object {
        fun of(
            musicId: Int,
            musicTitle: String,
        ): Music {
            return Music(
                musicId = musicId,
                musicTitle = musicTitle,
            )
        }
    }
}
