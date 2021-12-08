package com.warmerhammer.fitnessapp.HomeFragment.WorkOutSummaryComponent

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

data class Workout(
    private var _time_stamp: Date?,
) {

    var hardCodedWorkoutIdx = 0

    var title = "Please select workout..."
        set(value) {
            field = value
        }

    fun setTimeStamp(value: Date) {_time_stamp = value}
    fun hasTimeStamp() = _time_stamp != null
    fun getTimeStamp() = _time_stamp

    var muscles: ArrayList<String> = arrayListOf()
        get() = field
        set(value) {
            field = value
        }

}