package com.lalala.streaming

import lombok.extern.slf4j.Slf4j
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@Slf4j
class StreamingApplication

fun main(args: Array<String>) {
	runApplication<StreamingApplication>(*args)
}
