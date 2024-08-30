package de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.adapter.prometheus

import de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.usecase.out.CostCommandException
import de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.usecase.out.MeasurementQuery
import de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.usecase.out.MeasurementQueryException
import de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.utils.Logging
import de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.utils.log
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatusCode
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Mono

class PrometheusClient(@Qualifier("prometheusWebClient")
                       private val webClient: WebClient): MeasurementQuery, Logging {

                           private val errorMessage = "Couldn't execute query"

    private val jobIdQuery = "plathalter"

    override fun queryJobEnergyConsumption(jobId: String) {
        TODO("Not yet implemented")
    }

    override fun queryJobsEnergyConsumption() {
        TODO("Not yet implemented")
    }

    @Throws(MeasurementQueryException::class)
    override fun queryJobIds(): Collection<String> {
        webClient.get().uri{uriBuilder : UriBuilder -> uriBuilder.path("/api/v1/query").queryParam("query", "{query}").build(jobIdQuery)}.retrieve().onStatus(
            HttpStatusCode::isError) { _ ->
            Mono.error { throw MeasurementQueryException(errorMessage) }
        }.bodyToMono(String::class.java)
            .doOnNext { response -> log().debug("Response: $response") }
            .defaultIfEmpty("")
            .doOnError { error -> log().error("Error: ${error.message}") }
            .blockOptional()
            .orElseThrow { CostCommandException(errorMessage) }
    }
}