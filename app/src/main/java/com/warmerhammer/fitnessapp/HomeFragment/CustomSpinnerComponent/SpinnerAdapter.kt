package com.warmerhammer.fitnessapp.HomeFragment.CustomSpinnerComponent

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.warmerhammer.fitnessapp.R
import kotlinx.android.synthetic.main.workout_spinner_item.view.*
import kotlin.ClassCastException

class SpinnerAdapter(
    context: Context,
    var workouts: ArrayList<Pair<String, ArrayList<String>>>,
    val listener: OnSpinnerClickedListener
) :
    ArrayAdapter<Pair<String, ArrayList<String>>>(context, 0, workouts) {


    interface OnSpinnerClickedListener {
        fun onSpinnerClicked()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val workout = getItem(position)
        val view =
            convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.workout_spinner_item, parent, false)
        view.spinnerTextView.text = workout!!.first

        if (position > 0 && position == workouts.lastIndex) {
//            view.spinner_delete_icon?.visibility = View.VISIBLE
            view.spinnerTextView.setTextColor(Color.parseColor("#C70000"))
        }

        view.setOnClickListener {
            try {
                listener.onSpinnerClicked()
            } catch (e: ClassCastException) {
                throw ClassCastException(context.toString() + "Must implement listener")
            }
        }

        return view
    }
}