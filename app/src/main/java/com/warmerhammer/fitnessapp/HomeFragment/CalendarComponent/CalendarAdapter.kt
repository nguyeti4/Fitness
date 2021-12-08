package com.warmerhammer.fitnessapp.HomeFragment.CalendarComponent

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.recyclerview.widget.RecyclerView
import com.warmerhammer.fitnessapp.R

open class CalendarAdapter(
    private val daysOfMonth: ArrayList<String>,
    private val setOfDates: Set<String>,
    private val listener: (String) -> Unit
) : RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.custom_calendar_cell, parent, false)
        val layoutParams = view.layoutParams
        layoutParams.height = (parent.height * 0.16666666666).toInt()
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = daysOfMonth[position]
        holder.dayOfMonth.text = day

        // if day contains workouts mark it
        setOfDates.forEach { monthDayYear ->
            val splitDateString = monthDayYear.split("/")
            val monthDayYearDay = splitDateString[1].trim()
            // if day of splitString equals daysOfMonth
            if (day != "" && monthDayYearDay.toInt() == day.toInt()) {
                holder.itemView.setBackgroundColor(Color.parseColor("#de5a54"))
                holder.dayOfMonth.setTextColor(Color.parseColor("#ffffff"))
                // set onclick listener
                Log.i("CalendarAdapter", "monthDayYearDay :: $monthDayYearDay")
                holder.itemView.setOnClickListener {  listener(position.toString())  }
            }
        }
    }

    override fun getItemCount(): Int {
        return daysOfMonth.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var dayOfMonth: TextView = itemView.findViewById(R.id.cellDayText)
    }
}