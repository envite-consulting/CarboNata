package de.envite.greenbpm.camunda_process_carbon_pricing.fibonacci_worker

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.Gauge
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicInteger

private const val BASE_NAME = "fibonacciworker_jobs"
private const val JOB_KEY_TAG = "jobKey"

@Service
class JobExporterService(private val registry: MeterRegistry) {

    private val jobsInExecution = AtomicInteger(0)

    fun getJobsInExecution() = jobsInExecution.get()

    init {
        Gauge.builder("${BASE_NAME}_in_execution") {
            jobsInExecution.toDouble()
        }
            .description("Number of jobs currently in execution")
            .register(registry)
    }

    fun reportJobStarted(jobId: String) {
        Counter.builder("${BASE_NAME}_started")
            .tag(JOB_KEY_TAG, jobId)
            .register(registry)
            .increment()
        jobsInExecution.incrementAndGet()
    }

    fun reportJobFinished(jobId: String) {
        Counter.builder("${BASE_NAME}_finished")
            .description("Increases for every job handling finished")
            .tag(JOB_KEY_TAG, jobId)
            .register(registry)
            .increment()
        jobsInExecution.decrementAndGet()
    }
}
