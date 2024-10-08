package de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.adapter.camundaoptimize

import de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.domain.model.ProcessReport
import de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate.usecase.out.CostCommandException
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldNotContain
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.springframework.web.reactive.function.client.WebClient
import kotlin.test.Test

class CamundaOptimizeClientTest{

    companion object {

        lateinit var mockWebServer: MockWebServer

        @JvmStatic
        @BeforeAll
        fun setUp() {
            mockWebServer = MockWebServer()
            mockWebServer.start()
        }

        @JvmStatic
        @AfterAll
        fun tearDown() {
            mockWebServer.shutdown()
        }
    }

    private lateinit var classUnderTest: CamundaOptimizeClient
    private lateinit var baseUrl: String

    private val processReport = ProcessReport(22.0, "processInstanceId", "processDefinitionKey")

    @BeforeEach
    fun setUpClassUnderTest() {
        baseUrl = "http://localhost:${mockWebServer.port}"
        classUnderTest = CamundaOptimizeClient(
            WebClient.builder()
                .baseUrl(baseUrl)
                .build()
        )
    }

    @Test
    fun should_post_data_without_error() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
        )

        classUnderTest.exportCost(processReport)

        val request = mockWebServer.takeRequest()
        val bodyString = request.body.readUtf8()
        assertSoftly {
            request.requestUrl?.toUrl().toString() shouldBe "$baseUrl/api/ingestion/variable"
            bodyString shouldContain """
                  "name": "CostsInKiloWattPerHour",
                  "type": "double",
                  "value": 22.0,
                  "processInstanceId": "processInstanceId",
                  "processDefinitionKey": "processDefinitionKey"
            """.trimIndent().lines().joinToString("").filter { !it.isWhitespace() }
            // Test random Id is present and no value is  null
            bodyString shouldContain "\"id\":\""
            bodyString shouldNotContain "null"
        }
    }

    @Test
    fun should_throw_on_500() {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        val exception = shouldThrow<CostCommandException> {
            classUnderTest.exportCost(processReport)
        }
        exception.message?.shouldBeEqual("Could not post data to Optimize")
    }
}