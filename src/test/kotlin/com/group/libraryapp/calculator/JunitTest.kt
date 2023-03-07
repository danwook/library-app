package com.group.libraryapp.calculator

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 생명 주기 조절, 디폴트값은 PER_METHOD
class JunitTest {

    @BeforeAll
    fun beforeAll(){
        println("beforeAll")
    }

    @AfterAll
    fun afterAll(){
        println("afterAll")
    }

    @BeforeEach
    fun beforeEach(){
        println("each~~~")
    }

    @AfterEach
    fun afterEach(){
        println("end")
    }

    @Test
    fun test1(){
        println("test1")
    }

    @Test
    fun test2(){
        println("test2")
    }
}