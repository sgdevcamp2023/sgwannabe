package com.lalala.chart.consumer.repository

import com.lalala.chart.consumer.entity.StreamingLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StreamingLogRepository : JpaRepository<StreamingLog, Long>
