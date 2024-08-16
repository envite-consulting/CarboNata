package de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.domain.model

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ProcessReportTest {

    @Test
    fun should_not_throw_if_valid_object_creation() {
        assertDoesNotThrow{
            ProcessReport(2.0, "processInstanceId", "processDefinitionKey")
        }
    }

    @Test
    fun should_throw_on_invalid_object_creation() {
        assertThrows<IllegalArgumentException> {
            ProcessReport(0.0, "processInstanceId", "processDefinitionKey")
        }
    }
}