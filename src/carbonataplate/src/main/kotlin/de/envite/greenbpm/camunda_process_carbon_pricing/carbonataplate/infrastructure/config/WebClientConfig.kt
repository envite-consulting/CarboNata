package de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.infrastructure.config

import de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.adapter.camundaoptimize.config.OptimizeIngestionProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.*
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {

    @Bean
    fun authorizedClientManager(
        clientRegistrationRepository: ClientRegistrationRepository
    ): OAuth2AuthorizedClientManager {
        val service = InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository)
        val authorizedClientProvider: OAuth2AuthorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
            .clientCredentials()
            .build()
        val authorizedClientManager = AuthorizedClientServiceOAuth2AuthorizedClientManager(
            clientRegistrationRepository, service
        )
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider)
        return authorizedClientManager
    }

    @Bean
    fun webClient(
        authorizedClientManager: OAuth2AuthorizedClientManager?,
        optimizeIngestionProperties: OptimizeIngestionProperties
    ): WebClient {
        val oauth2Client = ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager)
        return WebClient.builder()
            .apply(oauth2Client.oauth2Configuration())
            .build()
    }

}