package com.example.unittests.data

import Interval

class IntervalsOverlapDetector {

    fun isOverlap(interval1: Interval, interval2: Interval) =
        interval1.end > interval2.start && interval1.start < interval2.end



}