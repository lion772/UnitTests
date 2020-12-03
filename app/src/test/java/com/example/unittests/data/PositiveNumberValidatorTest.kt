package com.example.unittests.data

import com.example.unittests.data.PositiveNumberValidator
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import org.hamcrest.CoreMatchers.`is` as Is


class PositiveNumberValidatorTest {

    private lateinit var SUT: PositiveNumberValidator

    @Before
    fun setup(){
        SUT = PositiveNumberValidator()
    }

    @Test
    fun test1(){
        val result = SUT.isPositive(-1)
        Assert.assertThat(result, Is(false))
    }

    @Test
    fun test2(){
        val result = SUT.isPositive(0)
        Assert.assertThat(result, Is(false))
    }

    @Test
    fun test3(){
        val result = SUT.isPositive(1)
        Assert.assertThat(result, Is(true))
    }
}