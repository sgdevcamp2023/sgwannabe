package com.lalala.streaming.controller

import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.UrlResource
import org.springframework.http.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/streaming")
class StreamingRestController(
    val audioLocation: String = "./static"
) {
    @GetMapping
    fun getAudio(@RequestParam("name") name: String): ResponseEntity<ByteArrayResource> {
        val audio = UrlResource("$audioLocation/$name")
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
            .contentType(MediaTypeFactory
                .getMediaType(audio)
                .orElse(MediaType.APPLICATION_OCTET_STREAM))
            .body(ByteArrayResource(audio.inputStream.readAllBytes()))
    }
}
