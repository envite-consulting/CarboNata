package de.envite.greenbpm.camunda_process_carbon_pricing.fibonacci_worker

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.Gauge
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicInteger

@Service
class JobExporterService @Autowired constructor(private val registry: MeterRegistry) {


    val jobsInExecution = AtomicInteger(0)

    init {
        Gauge.builder("fibonacciworker_jobs_in_execution") {
            jobsInExecution.toDouble()
        }
            .description("Number of jobs currently in execution")
            .register(registry)
    }

    fun reportJobStarted(jobId: String) {
        Counter.builder("fibonacciworker_jobs_started")
            .tag("jobKey", jobId)
            .register(registry)
            .increment()
        jobsInExecution.incrementAndGet()
    }

    fun reportJobFinished(jobId: String) {
        Counter.builder("fibonacciworker_jobs_finished")
            .description("Increases for every job handling finished")
            //.tag("jobKey", jobId)
            .register(registry)
            .increment()
        jobsInExecution.decrementAndGet()


    }

}
