package com.lalala.streaming

import com.lalala.aop.AuthenticationContext
import com.lalala.aop.PassportAspect
import com.lalala.config.CommonModuleConfig
import lombok.extern.slf4j.Slf4j
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@Import(CommonModuleConfig::class)
@SpringBootApplication
@Slf4j
class StreamingApplication

fun main(args: Array<String>) {
	runApplication<StreamingApplication>(*args)
}
