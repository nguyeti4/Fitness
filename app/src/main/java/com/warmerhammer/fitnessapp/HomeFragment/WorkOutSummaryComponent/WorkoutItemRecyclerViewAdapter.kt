package com.warmerhammer.fitnessapp.HomeFragment.WorkOutSummaryComponent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.SpinnerAdapter
import androidx.recyclerview.widget.RecyclerView
import com.warmerhammer.fitnessapp.HomeFragment.listOfHardCodedWorkouts
import com.warmerhammer.fitnessapp.R

class WorkoutItemRecyclerViewAdapter(
    private val context: Context,
    private val workouts: ArrayList<Workout>,
    private val onWorkoutSelectedListener: OnWorkOutSelectedListener
) : RecyclerView.Adapter<WorkoutItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): WorkoutItemRecyclerViewAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView =
            inflater.inflate(R.layout.workout_item, parent, false)
        return ViewHolder(contactView)
    }

    interface OnWorkOutSelectedListener {
        fun onWorkoutSelected(workoutIdx: Int, itemPosition: Int)
    }

    override fun onBindViewHolder(
        holder: WorkoutItemRecyclerViewAdapter.ViewHolder,
        position: Int
    ) {
        val workout = workouts[position]
        holder.workoutTitle.setSelection(workout.index)
        holder.muscleGroups.text = if (workout.muscles.isNotEmpty()) workout.muscles.toString() else ""
    }

    override fun getItemCount(): Int = workouts.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //store references to elements in the layout view
        val workoutTitle: Spinner = itemView.findViewById(R.id.workout_spinner)
        val muscleGroups: TextView = itemView.findViewById(R.id.muscle_groups_worked_out)

        init {
             // create an instance of custom spinner adapter and listener
            val adapter = SpinnerAdapter(context, listOfHardCodedWorkouts)
            workoutTitle.adapter = adapter

//            ArrayAdapter.createFromResource(
//                context,
//                R.array.workouts,
//                R.layout.workout_spinner_item
//            ).also { adapter ->
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//                workoutTitle.adapter = adapter
//            }

            workoutTitle.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    pos: Int,
                    id: Long
                ) {
                    onWorkoutSelectedListener.onWorkoutSelected(pos, adapterPosition)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
        }


    }
}