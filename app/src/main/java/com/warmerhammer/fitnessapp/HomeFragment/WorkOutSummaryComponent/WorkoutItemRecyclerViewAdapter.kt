package com.warmerhammer.fitnessapp.HomeFragment.WorkOutSummaryComponent

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.warmerhammer.fitnessapp.HomeFragment.CustomSpinnerComponent.CustomSpinnerDialogFragment
import com.warmerhammer.fitnessapp.R

class WorkoutItemRecyclerViewAdapter(
    private val context: Context,
    private val workouts: ArrayList<Workout>,
    private val loadWorkoutsListener: () -> Unit
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

    override fun onBindViewHolder(
        holder: WorkoutItemRecyclerViewAdapter.ViewHolder,
        position: Int
    ) {
        val workout = workouts[position]
        Log.i("WorkoutRVAdapter", "RV: $workout")
        holder.workoutItemTextView.text = workout.title
        holder.muscleGroups.text =
            if (workout.muscles.isNotEmpty()) workout.muscles.toString().replace('[', ' ', true)
                .replace(']', ' ', true) else ""
    }

    override fun getItemCount(): Int = workouts.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //store references to elements in the layout view
        val muscleGroups: TextView = itemView.findViewById(R.id.muscle_groups_worked_out)
        val workoutItemTextView: TextView = itemView.findViewById(R.id.workout_item_tv)
        val titleLinearLayout: LinearLayoutCompat = itemView.findViewById(R.id.workout_item_title_ll)


        init {
            Log.i("WorkoutRVAdapter", "RV")

            titleLinearLayout.setOnClickListener{
                val fm : FragmentManager = (context as AppCompatActivity).supportFragmentManager
                CustomSpinnerDialogFragment(workouts[bindingAdapterPosition]) {
                    if (it == "update") loadWorkoutsListener()
                    else if (it == "delete") loadWorkoutsListener()

                }.show(fm, CustomSpinnerDialogFragment.TAG)
            }
        }
    }

    companion object {
        private const val TAG = "WorkoutItemRCView"
    }
}