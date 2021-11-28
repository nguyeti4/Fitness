package com.warmerhammer.fitnessapp.HomeFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.warmerhammer.fitnessapp.HomeFragment.WorkOutSummaryComponent.Workout
import kotlinx.coroutines.launch

class HomeFragmentViewModel(
    private val repository: HomeFragmentRepository
) : ViewModel() {

    fun saveCurrentTitle(workout: Workout) {
        repository.saveTitle(workout)
    }

    val workouts = MutableLiveData<ArrayList<Workout>>()

    fun loadWorkouts() {

        val newWorkouts = repository.loadWorkouts()

       workouts.postValue(newWorkouts)
    }

}