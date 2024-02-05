package com.lalala.streaming.dto

import java.time.LocalDateTime

data class MusicDetailDTO (
    val id: Long,
    val title: String,
    val playTime: Short,
    val lyrics: String,
    val album: AlbumDTO,
    val file: FileDTO,
    val participants: ParticipantsDetailDTO,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {}
