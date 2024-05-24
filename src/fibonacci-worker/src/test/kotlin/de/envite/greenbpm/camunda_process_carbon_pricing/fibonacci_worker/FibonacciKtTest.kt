package de.envite.greenbpm.camunda_process_carbon_pricing.fibonacci_worker

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class FibonacciKtTest{
    @ParameterizedTest(name= "For {0}, the Fibonacci is {1}")
    @MethodSource("getData")
    fun `should return correct fibonacci values`(input: Int, expected: Int){
        assertEquals(fibonacciRec(input), expected)
    }

    companion object{
        @JvmStatic
        fun getData(): List<Arguments> = listOf(
            Arguments.of(0,0),
            Arguments.of(1,1),
            Arguments.of(2,1),
            Arguments.of(3,2),
            Arguments.of(4,3),
            Arguments.of(5,5),
            Arguments.of(6,8),
            Arguments.of(7,13),
            Arguments.of(8,21),
            Arguments.of(9,34)
        )

    }
}
