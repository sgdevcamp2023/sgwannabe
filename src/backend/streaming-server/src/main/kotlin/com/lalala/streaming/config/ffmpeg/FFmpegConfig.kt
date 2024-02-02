package com.lalala.streaming.config.ffmpeg;

import java.io.IOException;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.File
import java.nio.file.Paths

@Configuration
class FFmpegConfig {
    @Value("\${ffmpeg.location.mpeg}")
    private lateinit var ffmpegLocation: String

    @Value("\${ffmpeg.location.probe}")
    private lateinit var ffprobeLocation: String

    @Bean(name = ["ffMpeg"])
    fun ffMpeg(): FFmpeg = FFmpeg(ffmpegLocation)

    @Bean(name = ["ffProbe"])
    fun ffProbe(): FFprobe = FFprobe(ffprobeLocation)

    @Bean
    fun ffMpegExecutor(ffMpeg: FFmpeg, ffProbe: FFprobe): FFmpegExecutor = FFmpegExecutor(ffMpeg, ffProbe)
}
