package com.lalala.streaming

import com.lalala.config.KafkaProducerConfig
import com.lalala.reactive.config.CommonReactiveModuleConfig
import lombok.extern.slf4j.Slf4j
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@Import(
	CommonReactiveModuleConfig::class,
	KafkaProducerConfig::class,
)
@Slf4j
@SpringBootApplication
class StreamingApplication

fun main(args: Array<String>) {
	runApplication<StreamingApplication>(*args)
}
