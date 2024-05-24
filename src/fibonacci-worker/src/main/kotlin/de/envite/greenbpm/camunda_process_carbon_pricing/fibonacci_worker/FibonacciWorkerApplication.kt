package de.envite.greenbpm.camunda_process_carbon_pricing.fibonacci_worker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FibonacciWorkerApplication

fun main(args: Array<String>) {
	runApplication<FibonacciWorkerApplication>(*args)
}
