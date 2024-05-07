package com.lalala.streaming.config.async

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy


@Configuration
@EnableAsync
internal class AsyncConfig() {
    @Bean
    fun asyncProducerExecutor(
        @Value("\${spring.async.producer.core-pool-size}")
        corePoolSize: Int
    ): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = corePoolSize
        executor.maxPoolSize = corePoolSize * 2
        executor.queueCapacity = corePoolSize * 4
        executor.setThreadNamePrefix("Producer-Thread-")
        executor.keepAliveSeconds = 30
        executor.setAllowCoreThreadTimeOut(false)
        executor.setPrestartAllCoreThreads(true)
        executor.setRejectedExecutionHandler(CallerRunsPolicy())
        executor.initialize()
        return executor
    }

    @Bean
    fun preprocessingExecutor(
        @Value("\${spring.async.preprocessing.core-pool-size}")
        corePoolSize: Int
    ): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = corePoolSize
        executor.maxPoolSize = corePoolSize * 2
        executor.queueCapacity = corePoolSize * 4
        executor.setThreadNamePrefix("PreProcessing-Thread-")
        executor.keepAliveSeconds = 30
        executor.setAllowCoreThreadTimeOut(false)
        executor.setPrestartAllCoreThreads(true)
        executor.setRejectedExecutionHandler(CallerRunsPolicy())
        executor.initialize()
        return executor
    }
}
