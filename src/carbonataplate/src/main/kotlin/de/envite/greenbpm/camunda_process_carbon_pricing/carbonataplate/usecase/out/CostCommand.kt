package de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.usecase.out

import de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.domain.model.ProcessReport

fun interface CostCommand {

    @Throws(CostCommandException::class)
    fun exportCost(processReport: ProcessReport)
}