package de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.adapter.camundaoptimize.config

import de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.adapter.camundaoptimize.config.PropertyPrefix.CAMUNDA_OPTIMIZE
import de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.technology.Constants.CONFIG_PROPERTY_PREFIX
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("${CONFIG_PROPERTY_PREFIX}.${CAMUNDA_OPTIMIZE}")
data class OptimizeIngestionProperties(
    val basePath: String
)