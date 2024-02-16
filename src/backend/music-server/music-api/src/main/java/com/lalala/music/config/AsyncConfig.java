package com.lalala.music.config;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
class AsyncConfig {
    @Bean(name = "asyncProducerExecutor")
    public Executor asyncProducerExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("Lalala-Produce-Thread");
        executor.setKeepAliveSeconds(180);
        executor.setAllowCoreThreadTimeOut(false);
        executor.setPrestartAllCoreThreads(true);
        executor.initialize();
        return executor;
    }
}
