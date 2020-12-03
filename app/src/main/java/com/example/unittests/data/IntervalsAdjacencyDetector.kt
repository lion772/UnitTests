package com.example.unittests.data

import Interval

class IntervalsAdjacencyDetector {

    fun isAdjacent(interval1: Interval, interval2: Interval) =
        interval1.start == interval2.end || interval2.start == interval1.end ||
                isSameIntervals(interval1, interval2)

    fun isSameIntervals(interval1: Interval, interval2: Interval):Boolean {
         if (interval1 == interval2) {
            return true
        }
        return false
    }
}