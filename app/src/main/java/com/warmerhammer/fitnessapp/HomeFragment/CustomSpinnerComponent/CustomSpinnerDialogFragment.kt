package com.warmerhammer.fitnessapp.HomeFragment.CustomSpinnerComponent

import android.app.Dialog
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils.indexOf
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.warmerhammer.fitnessapp.HomeFragment.HomeFragmentViewModel
import com.warmerhammer.fitnessapp.HomeFragment.HomeFragmentViewModelFactory
import com.warmerhammer.fitnessapp.HomeFragment.WorkOutSummaryComponent.Workout
import com.warmerhammer.fitnessapp.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class CustomSpinnerDialogFragment(private val workout: Workout, private val loadWorkoutsListener: (String) -> Unit) :
    DialogFragment() {

    private var filteredHardCodedWorkoutList = ArrayList<Pair<String, ArrayList<String>>>()
    private lateinit var adapter: CustomSpinnerRecyclerViewAdapter
    private lateinit var viewModelFactory: HomeFragmentViewModelFactory
    private lateinit var viewModel: HomeFragmentViewModel
    private lateinit var onClickListener: CustomSpinnerRecyclerViewAdapter.OnClickListener


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // set up ViewModel
        viewModelFactory = HomeFragmentViewModelFactory(requireContext())
        viewModel =
            ViewModelProvider(this, HomeFragmentViewModelFactory(requireContext())).get(
                HomeFragmentViewModel::class.java
            )


        val builder = AlertDialog.Builder(requireContext())

        // set spinner view within dialog
        val view =
            LayoutInflater.from(context).inflate(R.layout.fragment_custom_spinner_dialog, null)


        var customSpinnerRecyclerView: RecyclerView = view.findViewById(R.id.custom_spinner_rv)
        customSpinnerRecyclerView.layoutManager = LinearLayoutManager(requireContext())


        // delete item onClick listener
        val deleteWorkoutButton = view.findViewById<TextView>(R.id.spinner_dialog_delete_tv)
        deleteWorkoutButton.setOnClickListener {
            viewModel.deleteWorkout(workout)
            loadWorkoutsListener("delete")
            parentFragmentManager.beginTransaction().remove(this@CustomSpinnerDialogFragment)
                .commit()
        }

//        // retrieve hardcoded workouts
//        retrieveHardCodedWorkouts()

        // init filtered list and adapter
//        initFilterHardCodedWorkouts()

        adapter = CustomSpinnerRecyclerViewAdapter(
            filteredHardCodedWorkoutList
        )
        customSpinnerRecyclerView.adapter = adapter

//        //editText search functionality
//        val editText: AutoCompleteTextView = view.findViewById(R.id.autoComplete_SpinnerTextView)
//        editText.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                val lowerCaseString = s.toString().lowercase()
//                filteredHardCodedWorkoutList.clear()
//
//                if (lowerCaseString.isNotEmpty()) {
//                    for (workout in listOfHardCodedWorkouts) {
//                        var lowerCaseWorkout = workout.first.lowercase()
//                        if (lowerCaseWorkout.contains(lowerCaseString)
//                            && workout.first != "Please select workout..."
//                        ) {
//                            filteredHardCodedWorkoutList.add(workout)
//                        }
//                    }
//
//                    filteredHardCodedWorkoutList.sortBy { it.first }
//                    adapter.notifyDataSetChanged()
//
//
//                } else if (lowerCaseString.isEmpty() || lowerCaseString == " ") {
//                    initFilterHardCodedWorkouts()
//                }
//            }
//
//            override fun afterTextChanged(s: Editable?) {}
//
//        })

        builder.setMessage("Are you sure you want to delete this workout?")
        builder.setView(view)
        return builder.create()
    }

//    private fun retrieveHardCodedWorkouts() {
//        listOfHardCodedWorkouts = viewModel.retrieveHardCodedWorkouts()
//    }

//    private fun initFilterHardCodedWorkouts() {
//        // list first three items in list
//        for (workout in listOfHardCodedWorkouts) {
//            if (workout.first != "Please select workout...") {
//                filteredHardCodedWorkoutList.add(workout)
//            }
//        }

//        filteredHardCodedWorkoutList.sortBy { it.first }
//        Log.i(TAG, "filter:: $filteredHardCodedWorkoutList")
//
//    }


    companion object {
        val TAG = "CustomSpinnerDialogFragment"
    }
}