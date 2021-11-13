package com.warmerhammer.fitnessapp.HomeFragment.CustomSpinnerComponent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.warmerhammer.fitnessapp.HomeFragment.WorkOutSummaryComponent.Workout
import com.warmerhammer.fitnessapp.R

class CustomSpinnerRecyclerViewAdapter(
    private val listOfWorkouts: ArrayList<Workout>,
    val onClickListener: OnClickListener
) :
    RecyclerView.Adapter<CustomSpinnerRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.workout_spinner_item, parent, false)
        return ViewHolder(contactView)
    }

    interface OnClickListener {
        fun onItemClicked(position: Int)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val workout = listOfWorkouts[position]
        holder.spinnerTextView.text = workout.title

    }

    override fun getItemCount(): Int = listOfWorkouts.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val spinnerTextView = itemView.findViewById<TextView>(R.id.spinnerTextView)

        init {
            spinnerTextView.setOnClickListener {
                onClickListener.onItemClicked(adapterPosition)
            }
        }
    }
}