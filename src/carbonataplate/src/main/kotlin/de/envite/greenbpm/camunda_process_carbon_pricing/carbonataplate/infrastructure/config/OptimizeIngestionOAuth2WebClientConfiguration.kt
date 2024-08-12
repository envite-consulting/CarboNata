package de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.infrastructure.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.*
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.web.reactive.function.client.WebClient

private const val CARBO_NATA_PLATE = "CarboNataPlate"

@Configuration
class OptimizeIngestionOAuth2WebClientConfiguration {

    @Value("\${carbonata-plate.camunda-optimize.base-path}")
    val basePath: String = ""

    @Value("\${spring.security.oauth2.client.provider.camunda-platform-auth.issuer-uri}")
    val issuerUri: String = ""

    @Value("\${spring.security.oauth2.client.registration.carbonata-plate-client.client-id}")
    val clientId: String = ""

    @Value("\${spring.security.oauth2.client.registration.carbonata-plate-client.client-secret}")
    val clientSecret: String = ""

    @Bean
    fun clientRegistration(): ClientRegistration {
        return ClientRegistration.withRegistrationId(CARBO_NATA_PLATE).tokenUri(issuerUri)
            .clientId(clientId).clientSecret(clientSecret)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS).build()
    }

    @Bean
    fun clientRegistrationRepository(clientRegistration: ClientRegistration): ReactiveClientRegistrationRepository {
        return InMemoryReactiveClientRegistrationRepository(clientRegistration)
    }

    @Bean
    fun authorizedClientService(clientRegistrationRepository: ReactiveClientRegistrationRepository): ReactiveOAuth2AuthorizedClientService {
        return InMemoryReactiveOAuth2AuthorizedClientService(clientRegistrationRepository)

    }

    @Bean
    fun authorizedClientManager(
        clientRegistrationRepository: ReactiveClientRegistrationRepository,
        authorizedClientService: ReactiveOAuth2AuthorizedClientService
    ): ReactiveOAuth2AuthorizedClientManager {
        val authorizedClientProvider: ReactiveOAuth2AuthorizedClientProvider =
            ReactiveOAuth2AuthorizedClientProviderBuilder.builder().clientCredentials().build()
        val authorizedClientManager = AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(
            clientRegistrationRepository, authorizedClientService
        )
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider)
        return authorizedClientManager
    }

    @Bean
    fun optimizeIngestionWebClient(
        authorizedClientManager: ReactiveOAuth2AuthorizedClientManager
    ): WebClient {
        val oauth2Client =
            ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager)
        oauth2Client.setDefaultClientRegistrationId(CARBO_NATA_PLATE)
        return WebClient.builder().filter(oauth2Client).baseUrl(basePath).build()
    }
}
