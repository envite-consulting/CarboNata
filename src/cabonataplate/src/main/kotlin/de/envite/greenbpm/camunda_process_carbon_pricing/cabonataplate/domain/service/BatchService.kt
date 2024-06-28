package de.envite.greenbpm.camunda_process_carbon_pricing.cabonataplate.domain.service

import de.envite.greenbpm.camunda_process_carbon_pricing.cabonataplate.usecase.`in`.TriggerCommand
import de.envite.greenbpm.camunda_process_carbon_pricing.cabonataplate.usecase.out.CostCommand
import de.envite.greenbpm.camunda_process_carbon_pricing.cabonataplate.usecase.out.MeasurementQuery

class BatchService(
    private val measurementQuery: MeasurementQuery,
    private val costCalculator: CostCalculatorService,
    private val costCommand: CostCommand,
): TriggerCommand {

    override fun runBatch() {
        // val data = measurementQuery.get()
        // val cost = costCalculator.calculate(data)
        // costCommand.send(data, cost)
        TODO("Not yet implemented")
    }
}