package com.warmerhammer.fitnessapp.HomeFragment.WorkOutSummaryComponent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.warmerhammer.fitnessapp.R
import kotlinx.android.synthetic.main.workout_spinner_item.view.*

class SpinnerAdapter(context: Context, workouts: ArrayList<Pair<String, ArrayList<String>>>) :
    ArrayAdapter<Pair<String, ArrayList<String>>>(context, 0, workouts) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup) : View {
        val workout = getItem(position)
        val view =
            convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.workout_spinner_item, parent, false)
        view.spinnerTextView.text = workout!!.first
        return view
    }
}