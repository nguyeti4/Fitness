package com.warmerhammer.fitnessapp.HomeFragment.WorkOutSummaryComponent

data class Workout(
    private var _index: Int,
) {

    var title = "Please select workout..."
        set(value) {
            field = value
        }

    fun setIndex(value: Int) {_index = value}
    fun getIndex() = _index

    var muscles: ArrayList<String> = arrayListOf()
        get() = field
        set(value) {
            field = value
        }

}