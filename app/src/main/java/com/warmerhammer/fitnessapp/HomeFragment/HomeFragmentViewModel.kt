package com.warmerhammer.fitnessapp.HomeFragment

import android.util.Log
import androidx.lifecycle.*
import com.warmerhammer.fitnessapp.HomeFragment.MuscleDiagramComponent.MuscleDiagram
import com.warmerhammer.fitnessapp.HomeFragment.WorkOutSummaryComponent.Workout
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@ExperimentalCoroutinesApi
@FlowPreview
class HomeFragmentViewModel(
    private val repository: HomeFragmentRepository
) : ViewModel() {

    fun saveSelectedWorkout(workout: Workout) {
        repository.saveWorkouts(workout)
    }

    fun fetchMuscleDiagram(muscleSet: MutableSet<String>): Pair<MuscleDiagram, MuscleDiagram> =
        repository.retrieveMuscleDiagram(muscleSet)

//    fun retrieveHardCodedWorkouts(): ArrayList<Pair<String, ArrayList<String>>> =
//        repository.retrieveHardCodedWorkouts()


    fun deleteWorkout(workout: Workout) {  // delete workout
        repository.deleteWorkout(workout)
    }

    fun retrieveDates(monthYear: String): HashMap<String, ArrayList<Workout>> =
        repository.buildListOfDates(monthYear)

    // observe and fetch workouts
    val workouts = MutableLiveData<ArrayList<Workout>>()

    fun loadWorkouts(selectedDate: String?) {
        viewModelScope.launch {
            repository.loadWorkouts(selectedDate).collect { listOfWorkouts ->
                Log.i("ViewModel", "viewmodel: loadWorkouts()")
                workouts.postValue(listOfWorkouts)
            }
        }
    }

}