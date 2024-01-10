package com.lalala.chart.entity.mongo

data class Artist(
    val artistId: Int,
    val artistName: String,
) {
    companion object {
        fun of(
            artistId: Int,
            artistName: String,
        ): Artist {
            return Artist(
                artistId = artistId,
                artistName = artistName,
            )
        }
    }
}
