package de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.domain.model

data class ProcessReport(
    val costsInKilowattPerHour: Double,
    val processInstanceId: String,
    val processDefinitionKey: String
) {
    init {
        require (costsInKilowattPerHour > 0) {
            "Costs in Kilowatt per Hour must not be null and greater than 0"
        }
    }
}