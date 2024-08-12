package de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.adapter.camundaoptimize

import de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.domain.model.output.CamundaOptimizeIngestion
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test
import kotlin.test.fail

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CamundaOptimizeClientTest {


    @Autowired
    private lateinit var camundaOptimizeClient: CamundaOptimizeClient


    @Test
    fun foo() {

        var camundaOptimizeIngestion = CamundaOptimizeIngestion("id", "name", "type", "value", "processInstanceId", "processDefinitionKey")
        val response = camundaOptimizeClient.exportCost(camundaOptimizeIngestion)

            .block()

        fail()


    }
}