package com.lalala.chart.consumer.service

import com.lalala.chart.consumer.entity.StreamingLog
import com.lalala.chart.consumer.repository.StreamingLogRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StreamingLogService(
    private val repository: StreamingLogRepository,
) {
    @Transactional
    fun create(musicId: Long): StreamingLog {
        val log =
            StreamingLog(
                musicId = musicId,
            )
        return repository.save(log)
    }
}
