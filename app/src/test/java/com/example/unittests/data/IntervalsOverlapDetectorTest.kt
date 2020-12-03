package com.example.unittests.data

import Interval
import org.hamcrest.CoreMatchers.`is` as Is
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class IntervalsOverlapDetectorTest{

    private lateinit var SUT:IntervalsOverlapDetector

    @Before
    fun setup(){
        SUT = IntervalsOverlapDetector()
    }

    //Interval1 is before Interval2.
    @Test
    fun isOverlap_interval1BeforeInterval2_falseReturned() {
        val interval1 = Interval(-1, 5)
        val interval2 = Interval(8, 12)
        val result = SUT.isOverlap(interval1, interval2)
        assertThat(result, Is(false))
    }

    //Interval1 overlaps Interval2 on start.

    @Test
    fun isOverlap_interval1OverlapsInterval2OnStart_trueReturned() {
        val interval1 = Interval(-1, 9)
        val interval2 = Interval(8, 12)
        val result = SUT.isOverlap(interval1, interval2)
        assertThat(result, Is(true))
    }

    //Interval1 is contained within Interval2.

    @Test
    fun isOverlap_interval1ContainedWithinInterval2_trueReturned() {
        val interval1 = Interval(5, 9)
        val interval2 = Interval(4, 12)
        val result = SUT.isOverlap(interval1, interval2)
        assertThat(result, Is(true))
    }

    //Interval1 contains Interval2.

    @Test
    fun isOverlap_interval1ContainsInterval2_trueReturned() {
        val interval1 = Interval(1, 10)
        val interval2 = Interval(4, 8)
        val result = SUT.isOverlap(interval1, interval2)
        assertThat(result, Is(true))
    }

    //Interval1 overlaps Interval2 on end.

    @Test
    fun isOverlap_interval1OverlapsInterval2OnEnd_trueReturned() {
        val interval1 = Interval(11, 20)
        val interval2 = Interval(2, 12)
        val result = SUT.isOverlap(interval1, interval2)
        assertThat(result, Is(true))
    }

    //Interval1 is after Interval2.

    @Test
    fun isOverlap_interval1ContainedWithinInterval2_falseReturned() {
        val interval1 = Interval(14, 20)
        val interval2 = Interval(4, 12)
        val result = SUT.isOverlap(interval1, interval2)
        assertThat(result, Is(false))
    }

    //Before Adjacent

    @Test
    fun isOverlap_interval1BeforeAdjacentInterval2_falseReturned() {
        val interval1 = Interval(-1, 5)
        val interval2 = Interval(5, 8)
        val result = SUT.isOverlap(interval1, interval2)
        assertThat(result, Is(false))
    }

    //After Adjacent

    @Test
    fun isOverlap_interval1AfterAdjacentInterval2_falseReturned() {
        val interval1 = Interval(-1, 5)
        val interval2 = Interval(-3, -1)
        val result = SUT.isOverlap(interval1, interval2)
        assertThat(result, Is(false))
    }





}