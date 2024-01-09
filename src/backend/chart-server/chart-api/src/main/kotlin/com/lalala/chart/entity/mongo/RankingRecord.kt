package com.lalala.chart.entity.mongo;

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Unwrapped

@TypeAlias("record")
data class RankingRecord(
    @Id
    var id: String? = null,
    val ranking: Int,
    @Unwrapped(onEmpty = Unwrapped.OnEmpty.USE_EMPTY)
    val album: Album,
    @Unwrapped(onEmpty = Unwrapped.OnEmpty.USE_EMPTY)
    val music: Music,
    @Unwrapped(onEmpty = Unwrapped.OnEmpty.USE_EMPTY)
    val artist: Artist,
    @Unwrapped(onEmpty = Unwrapped.OnEmpty.USE_EMPTY)
    val statistics: Statistics,
)
