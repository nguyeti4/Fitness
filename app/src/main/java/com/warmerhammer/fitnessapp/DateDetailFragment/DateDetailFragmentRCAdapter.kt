package com.warmerhammer.fitnessapp.DateDetailFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.warmerhammer.fitnessapp.HomeFragment.WorkOutSummaryComponent.Workout
import com.warmerhammer.fitnessapp.R

class DateDetailFragmentRCAdapter(private val listOfWorkouts: ArrayList<Workout>) :
    RecyclerView.Adapter<DateDetailFragmentRCAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var workoutText: TextView = itemView.findViewById(R.id.spinnerTextView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DateDetailFragmentRCAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.workout_spinner_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: DateDetailFragmentRCAdapter.ViewHolder, position: Int) {
        holder.workoutText.text = listOfWorkouts[position].title
    }

    override fun getItemCount() = listOfWorkouts.size
}