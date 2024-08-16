package de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.adapter.camundaoptimize

import de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.domain.model.ProcessReport
import de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.usecase.out.CostCommand
import de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.usecase.out.CostCommandException
import de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.utils.Logging
import de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.utils.log
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class CamundaOptimizeClient(
    @Qualifier("optimizeIngestionWebClient")
    private val webClient: WebClient
) : CostCommand, Logging {

    private val errorMessage = "Could not post data to Optimize"

    @Throws(CostCommandException::class)
    override fun exportCost(processReport: ProcessReport) {
        val ingestionData = mapProcessReport(processReport)
        webClient
            .post()
            .uri("/api/ingestion/variable")
            .bodyValue(ingestionData)
            .retrieve()
            .onStatus(HttpStatusCode::isError) { _ ->
                Mono.error { throw CostCommandException(errorMessage) }
            }
            .bodyToMono(String::class.java).doOnNext { response -> log().debug("Response: $response") }
            .doOnError { error -> log().error("Error: ${error.message}") }
            .blockOptional()
            .orElseThrow { CostCommandException(errorMessage) }
    }
}