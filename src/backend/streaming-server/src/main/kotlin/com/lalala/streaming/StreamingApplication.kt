package com.lalala.streaming

import com.lalala.mvc.config.CommonMvcModuleConfig
import lombok.extern.slf4j.Slf4j
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@Import(
	CommonMvcModuleConfig::class,
)
@Slf4j
@SpringBootApplication
class StreamingApplication

fun main(args: Array<String>) {
	runApplication<StreamingApplication>(*args)
}
