package com.warmerhammer.fitnessapp.HomeFragment

import android.content.Context
import android.util.Log
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import com.google.android.material.internal.ContextUtils.getActivity
import com.warmerhammer.fitnessapp.HomeFragment.WorkOutSummaryComponent.Workout
import java.io.FileReader
import java.io.IOException


public var listOfHardCodedWorkouts =
    arrayListOf(
        Pair("Bicep Curls", arrayListOf("biceps", "arms", "forearms")),
        Pair("Crunches", arrayListOf("abs", "lower back")),
        Pair("Bench press", arrayListOf("chest", "triceps")),
    )

val liveListOfWorkouts = MutableLiveData<ArrayList<Workout>>()
val listOfWorkouts = ArrayList<Workout>()

class HomeFragmentRepository (private val context: Context) {

    // Load items by reading every line in local file
    fun loadWorkouts() : ArrayList<Workout> {
       try {

           context.openFileInput("data.text").bufferedReader().useLines { lines ->
               lines.forEach { workoutBlock ->
                   val splitLine = workoutBlock.split(",")

                   val index = splitLine[0].trim().toInt()
                   val workout = listOfHardCodedWorkouts[index]
                   val newWorkout = Workout(index)
                   newWorkout.title = workout.first
                   newWorkout.muscles = workout.second

                   Log.i("Repository", "$newWorkout")

                   listOfWorkouts.add(newWorkout)
               }
           }

        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }

        liveListOfWorkouts.postValue(listOfWorkouts)
        return listOfWorkouts

    }

    // Save items by writing them into our data file
    fun saveTitle(workout: Workout) {
        // update list of items
        val workoutIndex = listOfWorkouts.indexOf(workout)
        listOfWorkouts[workoutIndex] = workout

        try {
            val fos = context.openFileOutput("data.txt", Context.MODE_PRIVATE).use {
                for (workout in listOfWorkouts) {
                    it.write("${workout.getIndex()}\n".toByteArray())
                }
            }

        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }

        loadWorkouts()
    }


//    val workoutSelectedListener =
//        object : WorkoutItemRecyclerViewAdapter.OnWorkOutSelectedListener {
//            override fun onWorkoutSelected(workoutIdx: Int, itemPosition: Int) {
//                // if incoming workout is not "Choose workout..."
//                // update selected item
//                Log.i("HomeFragment", "$workoutIdx")
//                if (workoutIdx != 0) {
//                    val workout = listOfHardCodedWorkouts[workoutIdx]
//                    val itemToBeUpdated = listOfWorkouts[itemPosition]
//                    Log.i("HomeFragment", "itemToBeUpdated :: $itemToBeUpdated")
//                    itemToBeUpdated.setIndex(workoutIdx)
//                    itemToBeUpdated.title = workout.first
//                    itemToBeUpdated.muscles = workout.second
//                    Log.i("HomeFragment", "index :: ${itemToBeUpdated.getIndex()}")
//                    adapter.notifyItemChanged(itemPosition)
//                }
//
//            }
//        }
}