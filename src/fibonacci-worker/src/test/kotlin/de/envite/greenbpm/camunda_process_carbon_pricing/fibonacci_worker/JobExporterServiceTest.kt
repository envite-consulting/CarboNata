package de.envite.greenbpm.camunda_process_carbon_pricing.fibonacci_worker

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JobExporterServiceTest {
    private lateinit var meterRegistry: MeterRegistry
    private lateinit var classUnderTest: JobExporterService
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

        classUnderTest = JobExporterService(meterRegistry)
    }

    @Test
    fun `reportJobStarted should increase jobsStartedCounter`() {
        val id = "testId"

        classUnderTest.reportJobStarted(id)

        verify { jobsStartedCounter.increment() }

        assertEquals(1, classUnderTest.getJobsInExecution())
    }

    @Test
    fun `reportJobFinished should increase jobsFinishedCounter`() {
        val id = "testId"

        classUnderTest.reportJobFinished(id)

        verify { jobsFinishedCounter.increment() }

        assertEquals(-1, classUnderTest.getJobsInExecution())

    }

    @Test
    fun `increase and decrease jobsInExecution`() {
        val id = "testId"

        classUnderTest.reportJobStarted(id)

        assertEquals(1, classUnderTest.getJobsInExecution())

        classUnderTest.reportJobFinished(id)

        assertEquals(0, classUnderTest.getJobsInExecution())

    }
}