package com.warmerhammer.fitnessapp.HomeFragment.MuscleDiagramComponent

import android.graphics.drawable.Drawable

data class MuscleDiagram(
    private var _imageSource : Int
) {

    private lateinit var _muscles : ArrayList<String>

    fun getImageResource() = _imageSource
    fun setImageResource(value: Int) {_imageSource = value}

    fun getMuscles() = _muscles
    fun setMuscles(value: ArrayList<String>){_muscles = value}

}
