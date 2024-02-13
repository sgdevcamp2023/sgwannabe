package com.lalala.chart.repository

import com.lalala.chart.entity.mongo.ChartEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface ChartRepository : ReactiveMongoRepository<ChartEntity, String> {
    fun findTopByOrderByTimeStampDesc(): Mono<ChartEntity>

    fun findByTimeStamp(timeStamp: Int): Mono<ChartEntity>
}
