package com.lalala.chart.consumer

import com.lalala.config.KafkaConsumerConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@Import(
    KafkaConsumerConfig::class,
)
@SpringBootApplication
class ChartBatchApplication

fun main(args: Array<String>) {
    runApplication<ChartBatchApplication>(*args)
}
