package com.example.unittests.data

import org.hamcrest.CoreMatchers.`is` as Is
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class StringReverserTest {

    private lateinit var SUT:StringReverser

    @Before
    fun setup(){
        SUT = StringReverser()
    }

    @Test
    fun reverse_emptyString_emptyStringReturned() {
        val result = SUT.reverse("")
        Assert.assertThat(result, Is(""))
    }

    @Test
    fun reverse_singleCharacter_sameStringReturned() {
        val result = SUT.reverse("a")
        Assert.assertThat(result, Is("a"))
    }

    @Test
    fun reverse_longString_reversedStringReturned(){
        val result = SUT.reverse(ABACATE)
        Assert.assertThat(result, Is(ETACABA))
    }

    @Test
    fun stringRevertedSecond() {
        val result = SUT.reverse(FULL_NAME)
        Assert.assertEquals(result, FULL_NAME_INVERTED)
    }

    companion object {
        const val ABACATE = "abacate"
        const val ETACABA = "etacaba"
        const val FULL_NAME = "William Steinke"
        const val FULL_NAME_INVERTED = "eknietS mailliW"
    }
}