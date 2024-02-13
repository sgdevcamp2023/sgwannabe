package com.lalala.chart

import com.lalala.aop.AuthenticationContext
import com.lalala.aop.PassportAspect
import com.lalala.config.CommonModuleConfig
import com.lalala.config.KafkaConsumerConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(
    CommonModuleConfig::class,
    AuthenticationContext::class,
    PassportAspect::class,
    KafkaConsumerConfig::class,
)
class ChartApplication

fun main(args: Array<String>) {
    runApplication<ChartApplication>(*args)
}
