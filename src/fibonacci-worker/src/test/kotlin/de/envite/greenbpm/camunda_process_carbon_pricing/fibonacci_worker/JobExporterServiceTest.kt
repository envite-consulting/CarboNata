package de.envite.greenbpm.camunda_process_carbon_pricing.fibonacci_worker

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import io.micrometer.core.instrument.simple.SimpleMeterRegistry
import org.junit.jupiter.api.Test


class JobExporterServiceTest {

    private val meterRegistry: SimpleMeterRegistry = SimpleMeterRegistry()
    private val classUnderTest: JobExporterService = JobExporterService(meterRegistry)

    @Test
    fun `reportJobStarted should increase jobsStartedCounter and update gauge`() {
        val id = "testId"

        classUnderTest.reportJobStarted(id)

        assertSoftly {
            meterRegistry.get("${BASE_NAME}_started").counter().count() shouldBe 1.0
            meterRegistry.get("${BASE_NAME}_in_execution").gauge().value() shouldBe 1.0
        }
    }

    @Test
    fun `reportJobStarted should decrease jobsStartedCounter and update gauge`() {
        val id = "testId"

        classUnderTest.reportJobFinished(id)

        assertSoftly {
            meterRegistry.get("${BASE_NAME}_finished").counter().count() shouldBe 1.0
            meterRegistry.get("${BASE_NAME}_in_execution").gauge().value() shouldBe -1.0
        }
    }
}