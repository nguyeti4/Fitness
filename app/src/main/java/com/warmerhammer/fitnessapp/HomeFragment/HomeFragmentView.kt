package com.warmerhammer.fitnessapp.HomeFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.warmerhammer.fitnessapp.HomeFragment.WorkOutSummaryComponent.Workout
import com.warmerhammer.fitnessapp.HomeFragment.WorkOutSummaryComponent.WorkoutItemRecyclerViewAdapter
import com.warmerhammer.fitnessapp.R
import com.warmerhammer.fitnessapp.databinding.FragmentHomeBinding

class HomeFragmentView : Fragment(R.layout.fragment_home) {

    private var binding: FragmentHomeBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WorkoutItemRecyclerViewAdapter
    private lateinit var viewModel: HomeFragmentViewModel
    private lateinit var viewModelFactory: HomeFragmentViewModelFactory
    private var homeFragmentListOfWorkouts = ArrayList<Workout>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // setup HomeFragmentViewModel
        viewModelFactory = HomeFragmentViewModelFactory(requireContext())
        setupViewModel()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(requireContext())

        recyclerView = view.findViewById(R.id.workoutSummaryRecyclerView)
        recyclerView.layoutManager = layoutManager
        adapter =
            WorkoutItemRecyclerViewAdapter(
                requireContext(),
                homeFragmentListOfWorkouts,
            )
        recyclerView.adapter = adapter


//        val workoutSelectedListener =
//            object : WorkoutItemRecyclerViewAdapter.OnWorkOutSelectedListener {
//                override fun onWorkoutSelected(workoutIdx: Int, itemPosition: Int) {
//                    // if incoming workout is not "Choose workout..."
//                    // update selected item
//                    Log.i("HomeFragment", "$workoutIdx")
//                    if (workoutIdx != 0) {
//                        val workout = listOfHardCodedWorkouts[workoutIdx]
//                        val itemToBeUpdated = listOfWorkouts[itemPosition]
//                        Log.i("HomeFragment", "itemToBeUpdated :: $itemToBeUpdated")
//                        itemToBeUpdated.setIndex(workoutIdx)
//                        itemToBeUpdated.title = workout.first
//                        itemToBeUpdated.muscles = workout.second
//                        Log.i("HomeFragment", "index :: ${itemToBeUpdated.getIndex()}")
//                        adapter.notifyItemChanged(itemPosition)
//                    }
//
//                }
//            }



        // retrieve list of workouts
        viewModel.loadWorkouts()

        // set up live data observer
        observeWorkouts()

        val addWorkoutButton = view.findViewById<Button>(R.id.add_workout_text_view)

        addWorkoutButton.setOnClickListener {
            // if workout at index 0 of list is not "Choose workout..."
            // add workout else do nothing
            Log.i("HomeFragment", "listOfWorkouts :: $listOfWorkouts")

            if (homeFragmentListOfWorkouts.isEmpty() || homeFragmentListOfWorkouts[0] != Workout(
                    _index = 0
                )
            ) {
                val newWorkOut = Workout(0)

                view.findViewById<TextView>(R.id.holdingText).visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                // adds workout of index 0 "Choose workout..."
                homeFragmentListOfWorkouts.add(0, newWorkOut)
                Log.i("HomeFragment", "listOfWorkouts :: $homeFragmentListOfWorkouts")

                viewModel.saveCurrentTitle(newWorkOut)
                adapter.notifyItemChanged(0)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please select workout first...",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }

    private fun observeWorkouts() {
        viewModel.workouts.observe(this as LifecycleOwner, { listOfWorkouts ->
            homeFragmentListOfWorkouts = listOfWorkouts
            Log.i("HomeFragment", "observe: $listOfWorkouts")
            adapter.notifyDataSetChanged()

        })
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this, HomeFragmentViewModelFactory(requireContext())).get(
                HomeFragmentViewModel::class.java
            )
    }


}