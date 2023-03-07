package com.group.libraryapp.calculator

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

class CalculatorTest {

    @Test
    fun add() {
        val calculator = Calculator(5)
        calculator.add(3)
        assertThat(calculator.number).isEqualTo(8)
    }

    @Test
    fun minus() {
        //given
        val calculator = Calculator(5)

        //when
        calculator.minus(4)

        //then
        assertThat(calculator.number).isEqualTo(1)
    }

    @Test
    fun multiply() {
        val calculator = Calculator(10)

        calculator.multiply(10)

        assertThat(calculator.number).isEqualTo(100)
    }

    @Test
    fun division_정상케이스() {
        val calculator = Calculator(5)

        calculator.division(2)

        assertThat(calculator.number).isEqualTo(2)
    }

    @Test
    fun division_비정상케이스() {
        val calculator = Calculator(5)
        assertThrows(IllegalArgumentException::class.java) { calculator.division(0) }
    }
}