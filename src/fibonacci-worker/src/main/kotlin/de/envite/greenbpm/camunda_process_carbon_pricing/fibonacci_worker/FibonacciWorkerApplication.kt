package de.envite.greenbpm.camunda_process_carbon_pricing.fibonacci_worker

import io.camunda.zeebe.spring.client.annotation.Deployment
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@Deployment(resources = ["classpath*:/bpmn/**/*.bpmn"])
class FibonacciWorkerApplication

fun main(args: Array<String>) {
	runApplication<FibonacciWorkerApplication>(*args)
}
