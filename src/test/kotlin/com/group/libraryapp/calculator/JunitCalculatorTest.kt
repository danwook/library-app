package com.group.libraryapp.calculator

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class JunitCalculatorTest {

    @Test
    fun add(){
        val calculator = Calculator(5)
        calculator.add(3)
        assertThat(calculator.number).isEqualTo(8)
    }



}