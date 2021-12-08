package com.warmerhammer.fitnessapp.HomeFragment

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.warmerhammer.fitnessapp.HomeFragment.MuscleDiagramComponent.MuscleDiagram
import com.warmerhammer.fitnessapp.HomeFragment.WorkOutSummaryComponent.Workout
import com.warmerhammer.fitnessapp.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


private var listOfHardCodedWorkouts =
    arrayListOf(
        Pair("Please select workout...", arrayListOf()),
        Pair("Bicep Curls", arrayListOf("right bicep", "left bicep", "forearms")),
        Pair("Crunches", arrayListOf("abs", "lower back")),
        Pair("Bench press", arrayListOf("chest", "right tricep", "left tricep")),
        Pair("Seated Rows", arrayListOf("upper back", "shoulders", "right bicep", "left bicep")),
        Pair(("Tricep Curls"), arrayListOf("right tricep", "left tricep")),
        Pair(("Squats"), arrayListOf("right quad", "left quad", "glutes", "hamstrings")),
        Pair(("Shoulder Press"), arrayListOf("right shoulder", "left shoulder"))
    )

private var mapOfFrontMuscleDiagrams = hashMapOf<ArrayList<String>, MuscleDiagram>(
    // front exercises
    arrayListOf<String>() to MuscleDiagram(R.drawable.bodydiagram_blank),
    arrayListOf(
        "Biceps",
        "Abdominal"
    ) to MuscleDiagram(R.drawable.bodydiagram_right_left_bicep_abs),
    arrayListOf("left bicep") to MuscleDiagram(R.drawable.bodydiagram_right_left_bicep),
    arrayListOf("right bicep") to MuscleDiagram(R.drawable.bodydiagram_right_bicep),
    arrayListOf(
        "Biceps"
    ) to MuscleDiagram(R.drawable.bodydiagram_right_left_bicep),
    arrayListOf("right quad") to MuscleDiagram(R.drawable.bodydiagram_right_quad),
    arrayListOf("chest") to MuscleDiagram(R.drawable.bodydiagram_chest),
    arrayListOf(
        "Quadriceps"
    ) to MuscleDiagram(R.drawable.bodydiagram_right_right_left_quad),
    arrayListOf(
        "Delts/Shoulders",
        "Deltoid"
    ) to MuscleDiagram(R.drawable.bodydiagram_shoulders_back),
    arrayListOf(
        "Biceps",
        "Deltoid",
        "Abdominal",
        "Quadriceps"
    ) to MuscleDiagram(R.drawable.bodydiagram_right_left_bicep_shoulders_abs_quads),
    arrayListOf(
        "Biceps",
        "Quadriceps"
    ) to MuscleDiagram(R.drawable.bodydiagram_right_left_bicep_quads),
    arrayListOf(
        "Biceps",
        "Shoulders",
        "Abdominal",
        "Quadriceps",
        "Chest/Pecs"
    ) to MuscleDiagram(R.drawable.bodydiagram_right_left_bicep_chest_shoulders_quads_abs),
    arrayListOf(
        "Biceps",
        "Chest/Pecs",
        "Deltoid"
    ) to MuscleDiagram(R.drawable.bodydiagram_right_left_bicep_chest_right_shoulder_left_shoulder),
    arrayListOf(
        "Biceps",
        "Chest/Pecs"
    ) to MuscleDiagram(R.drawable.bodydiagram_right_left_bicep_chest),
    arrayListOf(
        "Biceps",
        "Abdominal",
        "Quadriceps"
    ) to MuscleDiagram(R.drawable.bodydiagram_right_left_bicep_chest),
    arrayListOf(
        "Deltoid"
    ) to MuscleDiagram(R.drawable.bodydiagram_shoulders),
    arrayListOf(
        "Abdominal",
        "Quadriceps"
    ) to MuscleDiagram(R.drawable.bodydiagram_right_left_quads_abs),
    arrayListOf(
        "Deltoid",
        "Abdominal",
        "Quadriceps"
    ) to MuscleDiagram(R.drawable.bodydiagram_right_left_shoulders_quads_abs),
    arrayListOf("Chest/Pecs") to MuscleDiagram(R.drawable.bodydiagram_chest),
    arrayListOf(
        "Deltoid",
        "Chest/Pecs",
        "Abdominal",
        "Quadriceps"
    ) to MuscleDiagram(R.drawable.bodydiagram_shoulders_chest_abs_quads),
    arrayListOf("Obliques") to MuscleDiagram(R.drawable.bodydiagram_obliques),
    arrayListOf(
        "Obliques",
        "Deltoid",
        "Chest/Pecs"
    ) to MuscleDiagram(R.drawable.bodydiagram_obliques_deltoid),
    arrayListOf("Obliques", "Deltoid") to MuscleDiagram(R.drawable.bodydiagram_obliques_deltoid),
    arrayListOf(
        "Obliques",
        "Deltoid",
        "Biceps"
    ) to MuscleDiagram(R.drawable.bodydiagram_obliques_deltoid_biceps),
    arrayListOf(
        "Obliques",
        "Deltoid",
        "Biceps",
        "Abdominal"
    ) to MuscleDiagram(R.drawable.bodydiagram_obliques_deltoid_biceps_abdominal),
    arrayListOf(
        "Obliques",
        "Deltoid",
        "Biceps",
        "Abdominal"
    ) to MuscleDiagram(R.drawable.bodydiagram_obliques_deltoid_biceps_abdominal_chest),
    arrayListOf(
        "Obliques",
        "Deltoid",
        "Biceps",
        "Quadriceps"
    ) to MuscleDiagram(R.drawable.bodydiagram_obliques_quadriceps_deltoid_biceps),
    arrayListOf(
        "Obliques",
        "Deltoid",
        "Abdominal"
    ) to MuscleDiagram(R.drawable.bodydiagram_obliques_deltoid_biceps_abdominal),
    arrayListOf("Deltoid", "Biceps") to MuscleDiagram(R.drawable.bodydiagram_deltoids_biceps)
)

private var mapOfBackMuscleDiagrams = hashMapOf<ArrayList<String>, MuscleDiagram>(
    // back exercises
    arrayListOf<String>() to MuscleDiagram(R.drawable.bodydiagram_blank),
    arrayListOf("upper back") to MuscleDiagram(R.drawable.bodydiagram_upper_back),
    arrayListOf(
        "Lats/Back",
    ) to MuscleDiagram(R.drawable.bodydiagram_upper_back_lower_back),
    arrayListOf("glutes", "hamstrings") to MuscleDiagram(R.drawable.bodydiagram_glutes_hamstrings),
    arrayListOf(
        "Last/Back",
        "Triceps",
        "Forearms"
    ) to MuscleDiagram(R.drawable.bodydiagram_back_lower_back_triceps_forearms),
    arrayListOf("Glutes") to MuscleDiagram(R.drawable.bodydiagram_glutes),
    arrayListOf(
        "Deltoid"
    ) to MuscleDiagram(R.drawable.bodydiagram_shoulders),
    arrayListOf(
        "Deltoid",
        "Lats/Back"
    ) to MuscleDiagram(R.drawable.bodydiagram_shoulders_back),
    arrayListOf(
        "Triceps",
    ) to MuscleDiagram(R.drawable.bodydiagram_triceps_forearms),
    arrayListOf(
        "Glutes",
        "Hamstrings"
    ) to MuscleDiagram(R.drawable.bodydiagram_hamstring_shoulders),
    arrayListOf(
        "Hamstrings",
        "Glutes",
        "Deltoid",
        "Triceps"
    ) to MuscleDiagram(R.drawable.bodydiagram_hamstrings_glutes_shoulders_triceps),
    arrayListOf(
        "Quadriceps",
        "Lats/Back",
        "Deltoid",
        "Triceps"
    ) to MuscleDiagram(R.drawable.bodydiagram_right_left_quad_upper_back_shoulders_triceps),
    arrayListOf(
        "Lats/Back",
        "Glutes"
    ) to MuscleDiagram(R.drawable.bodydiagram_lats_back_glutes),
    arrayListOf(
        "Deltoid",
        "Glutes",
        "Lats/Back"
    ) to MuscleDiagram(R.drawable.bodydiagram_deltoid_glutes_lats_back),
    arrayListOf(
        "Lats/Back",
        "Deltoid",
        "Hamstrings",
        "Glutes"
    ) to MuscleDiagram(R.drawable.bodydiagram_lats_back_delt_glutes_hamstrings),
    arrayListOf(
        "Lats/Back",
        "Hamstrings",
        "Glutes"
    ) to MuscleDiagram(R.drawable.bodydiagram_lats_back_glutes_hamstrings),
    arrayListOf("Triceps", "Calves") to MuscleDiagram(R.drawable.bodydiagram_triceps_calves)
)

private var listOfWorkouts = ArrayList<Workout>()

class HomeFragmentRepository(private val context: Context) {

    fun buildListOfDates(monthYear: String): HashMap<String, ArrayList<Workout>> {
        val listOfDates = HashMap<String, ArrayList<Workout>>()

        Log.i(TAG, "listOfWorkouts in buildListofDates() :: $listOfWorkouts")

        listOfWorkouts.filter { workout ->
            val monthFormatter = SimpleDateFormat("MMM yyyy", Locale.getDefault())
            val formattedMonth = monthFormatter.format(workout.getTimeStamp())
            Log.i(TAG, "formattedMonth :: $formattedMonth == month :: $monthYear")
            formattedMonth == monthYear  // filter
        }.forEach { filteredWorkout ->
            val dateFormatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
            val formattedDate = dateFormatter.format(filteredWorkout.getTimeStamp())

            val arrayListToUpdate = listOfDates.getOrDefault(formattedDate, arrayListOf())
            if (arrayListToUpdate.isEmpty()) {
                listOfDates.put(formattedDate, arrayListOf(filteredWorkout))
            } else {
                listOfDates[formattedDate]!!.add(filteredWorkout)
            }

            Log.i(TAG, "listOfDatesFiltered :: $listOfDates")
        }

        Log.i(TAG, "listOfDates :: $listOfDates")

        return listOfDates
    }

    fun retrieveMuscleDiagram(muscleSet: MutableSet<String>): Pair<MuscleDiagram, MuscleDiagram> {

        // filter map for front workouts correct index
        // .get(key)
        val frontDiagramKey = mapOfFrontMuscleDiagrams
            .filter { (k, _) -> k.size <= muscleSet.size && muscleSet.containsAll(k) }  // filter keys where all muscles are contained within variable muscleSet
            .toList()
            .sortedByDescending { it.first.size }

        // filter map for back workous correct index
        // .get(key)
        // filter keys where all muscles are contained within variable muscleSet
        // compare list size of Key and muscleSet
        // sizes of keys have the most muscles within muscleSet are preferred
        val backDiagramKey = mapOfBackMuscleDiagrams
            .filter { (k, _) -> k.size <= muscleSet.size && muscleSet.containsAll(k) }
            .toList()
            .sortedByDescending { it.first.size }
            .take(1)

        Log.i(TAG, "backDiagramKey :: $backDiagramKey")
        Log.i(TAG, "frontDiagramKey :: $frontDiagramKey")

        val frontDiagram = frontDiagramKey[0].second
        val backDiagram = backDiagramKey[0].second

        return Pair(frontDiagram, backDiagram) // return specified muscle diagram
    }

//    // retrieves hardcoded workouts
//    fun retrieveHardCodedWorkouts(): ArrayList<Pair<String, ArrayList<String>>> {
//        return listOfHardCodedWorkouts
//    }

    // Generates items by reading every line in local file
    @RequiresApi(Build.VERSION_CODES.O)
    fun loadWorkouts(selectedDate: String?): Flow<ArrayList<Workout>> {
        return flow {
            Log.i("HomeFragmentRepo", "loading...")
            listOfWorkouts.clear()

            try {
                val file = File(context.filesDir, "newData.txt")

                file.useLines { lines ->
                    lines.forEach { workoutBlock ->
                        val splitLine = workoutBlock.split(",")
                        Log.i(TAG, "splitline: $splitLine")

                        val formatter =
                            SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.getDefault())
                        val timeStamp =
                            formatter.parse(splitLine[0].trim())
                        val newWorkout = Workout(timeStamp)

                        // assign list of muscles
                        if (splitLine.size == 3) {
                            newWorkout.title = splitLine[1].trim()
                            newWorkout.muscles =
                                splitLine[2].trim()
                                    .replace("[", "")
                                    .replace("]", "")
                                    .split(",")
                                    .toCollection(ArrayList())
                        } else {
                            newWorkout.title =
                                listOfHardCodedWorkouts[splitLine[1].trim().toInt()].first
                            newWorkout.muscles =
                                listOfHardCodedWorkouts[splitLine[1].trim().toInt()].second
                        }

                        Log.i(
                            "HomeFragmentRepository",
                            "newWorkout.muscles = ${newWorkout.muscles}"
                        )
                        if (newWorkout.muscles.isNotEmpty() && !listOfWorkouts.contains(newWorkout)) {   // only load workouts that are not already recorded
                            listOfWorkouts.add(newWorkout)
                        }
                    }
                }

            } catch (ioException: IOException) {
                Log.i("HomeFragmentRepo", "loading exception...")

                ioException.printStackTrace()
            }

            val workoutsFilteredByDate = ArrayList<Workout>()

            // filter workouts by current date or selectedDate
            Log.i("HomeFragmentRepository", "listofWorkouts :: ${listOfWorkouts}")

            listOfWorkouts.forEach { workout ->
                val dateComparisonFormatter =
                    SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

                val comparisonDate = dateComparisonFormatter.format(workout.getTimeStamp())
                val curDate = selectedDate ?: dateComparisonFormatter.format(Date())

                if (comparisonDate == curDate) {
                    workoutsFilteredByDate.add(workout)
                }
            }

            Log.i("HomeFragmentRepository", "workoutsFilteredByDate :: $workoutsFilteredByDate")
            emit(workoutsFilteredByDate)
        }
    }

    // Save items by writing them into our data file
    fun saveWorkouts(workout: Workout = Workout(null)) {
        if (workout.hasTimeStamp()) { // only update specific workout if workout has timestamp
            val workoutIndex = listOfWorkouts.indexOf(workout)

            if (workoutIndex == -1) listOfWorkouts.add(workout)
            else listOfWorkouts[workoutIndex] = workout  // updates list of saved workouts
        }

        try {
            context.openFileOutput("newData.txt", Context.MODE_PRIVATE).use { fos ->
                for (workout in listOfWorkouts) {
                    Log.i(TAG, "workout to be saved: ${workout.title}")
                    if (workout.hasTimeStamp()) {
                        fos.write("${workout.getTimeStamp()},${workout.title},${workout.muscles}\n".toByteArray())
                    }
                }
            }
            Log.i("HomeFragmentRepo", "Saved")


        } catch (ioException: IOException) {
            Log.i("HomeFragmentRepo", "Exception")
            ioException.printStackTrace()
        }
    }

    // deletes workout
    fun deleteWorkout(workout: Workout) {
        listOfWorkouts.remove(workout)
        saveWorkouts()
    }

    companion object {
        private const val TAG = "HomeFragmentRepository"
    }
}