package de.envite.greenbpm.camunda_process_carbon_pricing.fibonacci_worker

import de.envite.greenbpm.camunda_process_carbon_pricing.fibonacci_worker.utils.Logging
import de.envite.greenbpm.camunda_process_carbon_pricing.fibonacci_worker.utils.log
import io.camunda.zeebe.client.ZeebeClient
import io.camunda.zeebe.client.api.response.ActivatedJob
import io.camunda.zeebe.spring.client.annotation.JobWorker
import org.springframework.stereotype.Component

@Component
class FibonacciCalculatorWorker(private val zeebeClient: ZeebeClient, private val jobExporter: JobExporterService): Logging{

    @JobWorker(type="calculate-fibonacci")
    fun handle(job: ActivatedJob){
        jobExporter.reportJobStarted(job.key.toString())
        val fibonacciCount  = job.getVariable("fibonacci-count")?.toString()?.toInt()

        if(fibonacciCount == null){
            zeebeClient
                .newFailCommand(job)
                .retries(0)
                .send()
        }

        log().debug("Calculating the first {} fibonacci-numbers", fibonacciCount)
        fibonacciRec(fibonacciCount!!)
        log().debug("Done calculating the first {} fibonacci-numbers", fibonacciCount)

        jobExporter.reportJobFinished(job.key.toString())
        zeebeClient.newCompleteCommand(job).send()
    }
}