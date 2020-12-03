package com.example.unittests.data

import org.hamcrest.CoreMatchers.`is` as Is
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class StringDuplicatorTest {

    private lateinit var SU:StringDuplicator

    @Before
    fun setUp() {
        SU = StringDuplicator()
    }

    @Test
    fun string_duplicate_stringDuplicated() {
        val result = SU.duplicator(AVOCADO_BANANA)
        Assert.assertThat(result, Is(AVOCADO_BANANA_TOGETHER))
    }

    companion object {
        const val AVOCADO_BANANA = "avocado banana banana"
        const val AVOCADO_BANANA_TOGETHER = "avocadobananabanana"
    }
}