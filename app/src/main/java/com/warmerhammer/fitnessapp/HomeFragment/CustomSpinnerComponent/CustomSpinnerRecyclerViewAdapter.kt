package com.warmerhammer.fitnessapp.HomeFragment.CustomSpinnerComponent

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.warmerhammer.fitnessapp.R

class CustomSpinnerRecyclerViewAdapter(
    private val filteredListOfHardCodedWorkouts: ArrayList<Pair<String, ArrayList<String>>>
) :
    RecyclerView.Adapter<CustomSpinnerRecyclerViewAdapter.ViewHolder>() {

    val limit = 3

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
        val workout = filteredListOfHardCodedWorkouts[position]
    }

    override fun getItemCount(): Int {
        return if (filteredListOfHardCodedWorkouts.size > limit) limit
        else filteredListOfHardCodedWorkouts.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val spinnerTextView = itemView.findViewById<TextView>(R.id.spinnerTextView)

    }
}