package com.lalala.streaming.config.async

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor


@Configuration
@EnableAsync
internal class AsyncConfig {
    @Bean(name = ["asyncProducerExecutor"])
    fun asyncProducerExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 10
        executor.maxPoolSize = 100
        executor.queueCapacity = 100
        executor.setThreadNamePrefix("Easel-Produce-Thread")
        executor.keepAliveSeconds = 180
        executor.setAllowCoreThreadTimeOut(false)
        executor.setPrestartAllCoreThreads(true)
        executor.initialize()
        return executor
    }
}
