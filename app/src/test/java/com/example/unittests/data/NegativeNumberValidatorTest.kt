package com.example.unittests.data

import com.example.unittests.data.NegativeNumberValidator
import com.example.unittests.data.PositiveNumberValidator
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class NegativeNumberValidatorTest {

    private lateinit var SUT: NegativeNumberValidator

    @Before
    fun setup(){
        SUT = NegativeNumberValidator()
    }

    @Test
    fun test1(){
        val result = SUT.isNegative(1)
        Assert.assertEquals(result, false)
    }

    @Test
    fun test2(){
        val result = SUT.isNegative(0)
        Assert.assertEquals(result, false)
    }

    @Test
    fun test3(){
        val result = SUT.isNegative(-1)
        Assert.assertEquals(result, true)
    }
}