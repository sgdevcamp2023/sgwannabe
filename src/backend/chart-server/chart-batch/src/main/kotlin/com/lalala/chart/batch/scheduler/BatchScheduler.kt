package com.lalala.chart.batch.scheduler

import com.lalala.chart.batch.config.SampleJobConfiguration
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.batch.core.JobParameter
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersInvalidException
import org.springframework.batch.core.Step
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.repository.JobRestartException
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class BatchScheduler(
    private val jobLauncher: JobLauncher,
    private val batchConfig: SampleJobConfiguration,
    private val repo: JobRepository,
    private val step: Step,
) {
    private val log = KotlinLogging.logger {}

    @Scheduled(cron = "* * * * * *")
    fun runJob() {
        println("hi")
        // job parameter 설정
        val confMap: MutableMap<String, JobParameter<*>> = HashMap()
        confMap["time"] = JobParameter(System.currentTimeMillis(), Long::class.java)
        val jobParameters = JobParameters(confMap)

        try {
            jobLauncher.run(batchConfig.job2(repo, step), jobParameters)
        } catch (e: JobExecutionAlreadyRunningException) {
            log.error { e.message }
        } catch (e: JobInstanceAlreadyCompleteException) {
            log.error { e.message }
        } catch (e: JobParametersInvalidException) {
            log.error { e.message }
        } catch (e: JobRestartException) {
            log.error { e.message }
        }
    }
}
