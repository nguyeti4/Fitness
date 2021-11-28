package com.warmerhammer.fitnessapp.HomeFragment.CustomSpinnerComponent

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.warmerhammer.fitnessapp.HomeFragment.HomeFragmentViewModel
import com.warmerhammer.fitnessapp.HomeFragment.HomeFragmentViewModelFactory
import com.warmerhammer.fitnessapp.HomeFragment.WorkOutSummaryComponent.Workout
import com.warmerhammer.fitnessapp.R

class CustomSpinnerDialogFragment(private val listOfWorkouts: ArrayList<Workout>) :
    DialogFragment() {

    private var filteredWorkoutList = ArrayList<Workout>()
    private lateinit var adapter: CustomSpinnerRecyclerViewAdapter
    private lateinit var viewModelFactory: HomeFragmentViewModelFactory
    private lateinit var viewModel: HomeFragmentViewModel


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

        // spinner item onclick listener
        val onClickListener = object : CustomSpinnerRecyclerViewAdapter.OnClickListener {
            override fun onItemClicked(position: Int) {
                viewModel.saveCurrentTitle(filteredWorkoutList[position])
            }
        }

        adapter = CustomSpinnerRecyclerViewAdapter(filteredWorkoutList, onClickListener)
        customSpinnerRecyclerView.adapter = adapter

        // init filtered list
        initFilteredList()

        //editText search functionality
        val editText: AutoCompleteTextView = view.findViewById(R.id.autoComplete_SpinnerTextView)
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val lowerCaseString = s.toString().lowercase()
                filteredWorkoutList.clear()

                if (lowerCaseString.isNotEmpty()) {
                    Log.i(TAG, "isNotEmpty: $s")

                    for (workout in listOfWorkouts) {
                        if (workout.title.lowercase().contains(lowerCaseString)) {
                            filteredWorkoutList.add(workout)
                        }
                    }

                    filteredWorkoutList.sortBy{ it.title }
                    adapter.notifyDataSetChanged()


                } else if (lowerCaseString.isEmpty() || lowerCaseString == " ") {
                    initFilteredList()
                }
            }

            override fun afterTextChanged(s: Editable?) {}

        })

        builder.setMessage(R.string.add_workout)
        builder.setView(view)
        return builder.create()
    }

    private fun initFilteredList() {
        filteredWorkoutList.clear()

        // list first three items in list
        for (idx in 0..2) {
            filteredWorkoutList.add(listOfWorkouts[idx])
        }

        filteredWorkoutList.sortBy{ it.title }
        adapter.notifyDataSetChanged()
    }

    companion object {
        val TAG = "CustomSpinnerDialogFragment"
    }
}