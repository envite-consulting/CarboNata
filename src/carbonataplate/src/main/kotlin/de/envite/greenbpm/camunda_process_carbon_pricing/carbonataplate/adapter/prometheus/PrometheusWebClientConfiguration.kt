package de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.adapter.prometheus

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class PrometheusWebClientConfiguration(@Value("\${carbonata-plate.prometheus.base-path}")
                                       private val basePath: String) {

    @Bean
    fun prometheusWebClient() : WebClient {
        return WebClient.builder().baseUrl(basePath).build()
    }



}