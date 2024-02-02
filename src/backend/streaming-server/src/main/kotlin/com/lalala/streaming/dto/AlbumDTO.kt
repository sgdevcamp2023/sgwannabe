package com.lalala.streaming.dto

import java.time.LocalDateTime

data class AlbumDTO (
    val id: Long,
    val type: AlbumType,
    val title: String,
    val coverUrl: String,
    val releasedAt: LocalDateTime,
    val artist: ArtistDTO,
) {}
