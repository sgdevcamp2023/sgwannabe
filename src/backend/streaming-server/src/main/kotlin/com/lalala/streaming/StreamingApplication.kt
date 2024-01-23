package com.lalala.streaming

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StreamingApplication

fun main(args: Array<String>) {
	runApplication<StreamingApplication>(*args)
}
