package de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.usecase.out

interface MeasurementQuery {

    fun queryJobEnergyConsumption(jobId: String)

    fun queryJobsEnergyConsumption()

    fun queryJobIds(): Collection<String>
}