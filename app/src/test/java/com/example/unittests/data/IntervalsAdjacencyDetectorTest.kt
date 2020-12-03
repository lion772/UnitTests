package com.example.unittests.data

import Interval
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.hamcrest.CoreMatchers.`is` as Is


class IntervalsAdjacencyDetectorTest{

    private lateinit var SUT:IntervalsAdjacencyDetector

    @Before
    fun setup(){
        SUT = IntervalsAdjacencyDetector()
    }

    @Test
    fun isAdjency_interval1IsAdjacentInterval2_trueReturned() {
        val interval1 = Interval(-1, 5)
        val interval2 = Interval(-1, 5)
        val result = SUT.isAdjacent(interval1, interval2)
        assertThat(result, Is(false))
    }
}