package com.warmerhammer.fitnessapp.DateDetailFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.warmerhammer.fitnessapp.HomeFragment.HomeFragmentRepository
import com.warmerhammer.fitnessapp.HomeFragment.MuscleDiagramComponent.MuscleDiagram
import com.warmerhammer.fitnessapp.HomeFragment.WorkOutSummaryComponent.Workout
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailFragmentViewModel(private val repository: HomeFragmentRepository) : ViewModel() {

    val workouts = MutableLiveData<ArrayList<Workout>>()

    fun fetchWorkoutsByDate(date: String) {
        viewModelScope.launch {
            repository.loadWorkouts(date).collect { arrayList ->
                workouts.postValue(arrayList)
            }
        }
    }

    fun fetchMuscleDiagram(muscleSet: MutableSet<String>): Pair<MuscleDiagram, MuscleDiagram> =
        repository.retrieveMuscleDiagram(muscleSet)

}