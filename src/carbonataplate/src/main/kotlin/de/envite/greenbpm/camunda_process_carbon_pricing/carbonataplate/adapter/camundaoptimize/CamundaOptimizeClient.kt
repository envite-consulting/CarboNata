package de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.adapter.camundaoptimize

import de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.domain.model.ProcessReport
import de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.usecase.out.CostCommand
import de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.utils.Logging
import de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.utils.log
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class CamundaOptimizeClient(
    @Qualifier("optimizeIngestionWebClient")
    private val webClient: WebClient
) : CostCommand, Logging {

    override fun exportCost(processReport: ProcessReport) {
        val ingestionData = mapProcessReport(processReport)
        webClient.post().uri("/api/ingestion/variable").bodyValue(ingestionData).retrieve()
            .bodyToMono(String::class.java).doOnNext { response -> log().debug("Response: $response") }
            .doOnError { error -> log().error("Error: ${error.message}") }
            .block()
    }
}