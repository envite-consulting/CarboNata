package de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.domain.model.output

data class CamundaOptimizeIngestion(
    val id: String,
    val name: String,
    val type: String,
    val value: String,
    val processInstanceId: String,
    val processDefinitionKey: String
)