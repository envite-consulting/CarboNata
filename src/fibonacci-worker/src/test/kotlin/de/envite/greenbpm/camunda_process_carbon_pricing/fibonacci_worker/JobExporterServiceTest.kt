package de.envite.greenbpm.camunda_process_carbon_pricing.fibonacci_worker

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.Gauge
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.context.SpringBootTest

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.*

@SpringBootTest
class JobExporterServiceTest {
    private lateinit var meterRegistry: MeterRegistry
    private lateinit var jobExporterService: JobExporterService
    private lateinit var jobsStartedCounter: Counter
    private lateinit var jobsFinishedCounter: Counter

    @BeforeEach
    fun setup(){
        meterRegistry = mockk(relaxed = true)
        jobsStartedCounter = mockk(relaxed = true)
        jobsFinishedCounter = mockk(relaxed = true)

        every { Counter.builder("fibonacciworker_jobs_started")
                .tag("jobKey","testId")
                .register(meterRegistry)
        } returns  jobsStartedCounter

        every { Counter.builder("fibonacciworker_jobs_finished")
                .tag("jobKey","testId")
                .register(meterRegistry)
        } returns  jobsFinishedCounter

        jobExporterService = JobExporterService(meterRegistry)
    }

    @Test
    fun `reportJobStarted should increase jobsStartedCounter`() {
        val id = "testId"

        jobExporterService.reportJobStarted(id)

        verify { jobsStartedCounter.increment() }

        assertEquals(1, jobExporterService.jobsInExecution.get())
    }

    @Test
    fun `reportJobFinished should increase jobsFinishedCounter`() {
        val id = "testId"

        jobExporterService.reportJobFinished(id)

        verify { jobsStartedCounter.increment() }

        assertEquals(-1, jobExporterService.jobsInExecution.get())

    }

    @Test
    fun `increase and decrease jobsInExecution`() {
        val id = "testId"

        jobExporterService.reportJobStarted(id)

        assertEquals(1, jobExporterService.jobsInExecution.get())

        jobExporterService.reportJobFinished(id)

        assertEquals(0, jobExporterService.jobsInExecution.get())

    }




}