package com.lalala.chart.entity.mongo

data class Album(
    val albumId: Int,
    val albumName: String,
    val albumCoverUrl: String,
) {
    companion object {
        fun of(
            albumId: Int,
            albumName: String,
            albumCoverUrl: String
        ): Album {
            return Album(
                albumId = albumId,
                albumName = albumName,
                albumCoverUrl = albumCoverUrl
            )
        }
    }
}
