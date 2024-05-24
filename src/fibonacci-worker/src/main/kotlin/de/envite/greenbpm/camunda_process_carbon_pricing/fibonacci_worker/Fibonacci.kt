package de.envite.greenbpm.camunda_process_carbon_pricing.fibonacci_worker

fun fibonacciRec(n: Int): Int = when (n) {
            0 -> 0
            1 -> 1
            else -> fibonacciRec(n - 1) + fibonacciRec(n - 2)
        }

