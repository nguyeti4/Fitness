package com.warmerhammer.fitnessapp.HomeFragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.warmerhammer.fitnessapp.HomeFragment.WorkOutSummaryComponent.Workout
import com.warmerhammer.fitnessapp.HomeFragment.WorkOutSummaryComponent.WorkoutItemRecyclerViewAdapter
import com.warmerhammer.fitnessapp.R
import com.warmerhammer.fitnessapp.databinding.FragmentHomeBinding
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException

public var listOfHardCodedWorkouts =
    arrayListOf(
        Pair("Bicep Curls", arrayListOf("biceps", "arms", "forearms")),
        Pair("Crunches", arrayListOf("abs", "lower back")),
        Pair("Bench press", arrayListOf("chest", "triceps"))
    )

class HomeFragmentView : Fragment(R.layout.fragment_home) {

    private var binding: FragmentHomeBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WorkoutItemRecyclerViewAdapter

    val listOfWorkouts = ArrayList<Workout>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // retrieve list of workouts
        loadWorkouts()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(requireContext())

        recyclerView = view.findViewById(R.id.workoutSummaryRecyclerView)
        recyclerView.layoutManager = layoutManager

        val workoutSelectedListener =
            object : WorkoutItemRecyclerViewAdapter.OnWorkOutSelectedListener {
                override fun onWorkoutSelected(workoutIdx: Int, itemPosition: Int) {
                    val workout = listOfHardCodedWorkouts[workoutIdx]

                    listOfWorkouts[itemPosition].index = workoutIdx
                    listOfWorkouts[itemPosition].title = workout.first
                    listOfWorkouts[itemPosition].muscles = workout.second
                }
            }

        adapter =
            WorkoutItemRecyclerViewAdapter(
                requireContext(),
                listOfWorkouts,
                workoutSelectedListener
            )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val addWorkoutButton = view.findViewById<Button>(R.id.add_workout_text_view)

        addWorkoutButton.setOnClickListener {
            val newWorkOut = Workout(0)

            view.findViewById<TextView>(R.id.holdingText).visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            listOfWorkouts.add(newWorkOut)
            Log.i("MainActivity", "$newWorkOut")
            adapter.notifyDataSetChanged()
        }

    }

    // Load items by reading every line in local file
    private fun loadWorkouts() {
        try {
            val inputStream = FileReader("data.txt")
            inputStream.useLines { lines ->
                lines.forEach { workoutBlock ->
                    val splitLine = workoutBlock.split(",")

                    val index = splitLine[0].trim().toInt()
                    val workout = listOfHardCodedWorkouts[index]
                    val newWorkout = Workout(index)
                    newWorkout.title = workout.first
                    newWorkout.muscles = workout.second

                    listOfWorkouts.add(newWorkout)
                }

                if (listOfWorkouts.isNotEmpty()) {
                    binding!!.workoutSummaryRecyclerView.visibility = View.VISIBLE
                    binding!!.holdingText.visibility = View.GONE
                }
            }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

    // Save items by writing them into our data file
    private fun saveWorkouts() {
        try {
            val fileWriter = FileWriter("data.txt")
            fileWriter.use { fileWriter ->
                for (workout in listOfWorkouts) {
                    fileWriter.write("${workout.index}")
                }
            }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }


}