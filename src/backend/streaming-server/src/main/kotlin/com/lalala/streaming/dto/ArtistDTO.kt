package com.lalala.streaming.dto

import java.time.LocalDateTime

data class ArtistDTO (
    val id: Long,
    val name: String,
    val genderType: GenderType,
    val type: ArtistType,
    val agency: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {}
