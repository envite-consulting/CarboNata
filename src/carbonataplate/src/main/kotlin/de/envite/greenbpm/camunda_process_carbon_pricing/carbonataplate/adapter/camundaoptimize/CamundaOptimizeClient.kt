package de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.adapter.camundaoptimize

import de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.domain.model.output.CamundaOptimizeIngestion
import de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.usecase.out.CostCommand
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class CamundaOptimizeClient(
    private val webClient: WebClient
) : CostCommand {

    override fun exportCost(ingestionValue: CamundaOptimizeIngestion): Mono<String> {
        return webClient.post().uri("/api/ingestion/variable").bodyValue(ingestionValue).retrieve()
            .bodyToMono(String::class.java).doOnNext { response -> println("Response: $response") }
            .doOnError { error -> println("Error: ${error.message}") }
    }
}