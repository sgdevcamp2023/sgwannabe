package com.lalala.streaming.listener

import com.lalala.streaming.constant.StreamingConstant
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.io.File

@Component
class TempFileDeleteListener {
    private val logger = KotlinLogging.logger {}

    @EventListener(ApplicationReadyEvent::class)
    fun DeleteTempFilesAfterStartUp() {
        File(StreamingConstant.TEMP_FOLDER)
            .walkTopDown()
            .maxDepth(1)
            .forEach(File::delete)

        logger.info { "임시 폴더의 모든 음원 파일을 청소했습니다." }
    }
}
