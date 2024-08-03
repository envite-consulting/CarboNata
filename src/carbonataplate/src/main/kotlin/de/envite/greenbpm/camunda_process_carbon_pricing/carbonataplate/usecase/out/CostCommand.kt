package de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.usecase.out

import de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.domain.model.output.CamundaOptimizeIngestion
import reactor.core.publisher.Mono

interface CostCommand {
    fun exportCost(ingestionValue: CamundaOptimizeIngestion): Mono<String>
}