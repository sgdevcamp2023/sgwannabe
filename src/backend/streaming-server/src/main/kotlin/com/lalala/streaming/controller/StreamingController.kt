package com.lalala.streaming.controller

import org.springframework.core.io.UrlResource
import org.springframework.http.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/streaming")
class StreamingController(
    val audioLocation: String = "static"
) {
    @GetMapping
    fun getAudio(@RequestParam("name") name: String): ResponseEntity<UrlResource> {
        val audio = UrlResource("file:$audioLocation/$name")
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
            .contentType(MediaTypeFactory
                .getMediaType(audio)
                .orElse(MediaType.APPLICATION_OCTET_STREAM))
            .body(audio)
    }
}
