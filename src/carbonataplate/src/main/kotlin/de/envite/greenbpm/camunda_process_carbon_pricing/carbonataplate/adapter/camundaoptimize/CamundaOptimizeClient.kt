package de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.adapter.camundaoptimize

import com.fasterxml.jackson.databind.ObjectMapper
import de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.adapter.camundaoptimize.config.OptimizeIngestionProperties
import de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.domain.model.output.CamundaOptimizeIngestion
import de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.usecase.out.CostCommand
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class CamundaOptimizeClient(
    private val objectMapper: ObjectMapper,
    private val webClient: WebClient,
    private val camundaIngestionProperties: OptimizeIngestionProperties
) : CostCommand {

    companion object {
        private const val CAMUNDA_OPTIMIZE_CLIENT_REGISTRATION_ID = "carbonata-plate-client"
    }

    override fun exportCost(ingestionValue: CamundaOptimizeIngestion): Mono<String> {
        return webClient.post()
            .uri("${camundaIngestionProperties.basePath}/api/ingestion/variable")
            .attributes(clientRegistrationId(CAMUNDA_OPTIMIZE_CLIENT_REGISTRATION_ID))
            .bodyValue(objectMapper.writeValueAsString(ingestionValue))
            .retrieve()
            .bodyToMono(String::class.java)
            .doOnNext { response -> println("Response: $response") }
            .doOnError { error -> println("Error: ${error.message}") }
    }
}