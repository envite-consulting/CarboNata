package de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.adapter.camundaoptimize

import de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.domain.model.ProcessReport
import java.util.*

data class CamundaOptimizeIngestion(
    val id: String,
    val name: String,
    val type: String,
    val value: Any,
    val processInstanceId: String,
    val processDefinitionKey: String
)

fun mapProcessReport(processReport: ProcessReport): CamundaOptimizeIngestion {
    return CamundaOptimizeIngestion(
        UUID.randomUUID().toString(),
        "CostsInKiloWattPerHour",
        "double",
        processReport.costsInKilowattPerHour,
        processReport.processInstanceId,
        processReport.processDefinitionKey
    )
}