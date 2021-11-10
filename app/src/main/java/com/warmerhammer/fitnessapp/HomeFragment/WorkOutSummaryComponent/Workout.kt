package com.warmerhammer.fitnessapp.HomeFragment.WorkOutSummaryComponent

data class Workout(
    var _index: Int,
) {

    var title = "Please select workout..."
        set(value) {
            field = value
        }

    var index: Int = _index
        set(value) {
            field = value
        }
        get() = _index

    var muscles: ArrayList<String> = arrayListOf()
        get() = field
        set(value) {
            field = value
        }

}