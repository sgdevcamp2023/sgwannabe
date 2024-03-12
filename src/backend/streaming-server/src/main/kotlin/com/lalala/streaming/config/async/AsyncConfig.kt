package com.lalala.streaming.config.async

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy


@Configuration
@EnableAsync
internal class AsyncConfig {
    @Bean(name = ["asyncProducerExecutor"])
    fun asyncProducerExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 10
        executor.maxPoolSize = 20
        executor.queueCapacity = 40
        executor.setThreadNamePrefix("Producer-Thread")
        executor.keepAliveSeconds = 30
        executor.setAllowCoreThreadTimeOut(false)
        executor.setPrestartAllCoreThreads(true)
        executor.setRejectedExecutionHandler(CallerRunsPolicy())
        executor.initialize()
        return executor
    }

    @Bean(name = ["preprocessingExecutor"])
    fun preprocessingExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 10
        executor.maxPoolSize = 20
        executor.queueCapacity = 40
        executor.setThreadNamePrefix("PreProcessing-Thread")
        executor.keepAliveSeconds = 30
        executor.setAllowCoreThreadTimeOut(false)
        executor.setPrestartAllCoreThreads(true)
        executor.setRejectedExecutionHandler(CallerRunsPolicy())
        executor.initialize()
        return executor
    }
}
