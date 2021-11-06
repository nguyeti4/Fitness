package com.example.searchactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Spinner

import com.example.searchactivity.databinding.ActivityMainListBinding

class MainActivityList : AppCompatActivity() {
    private var _binding : ActivityMainListBinding? = null
    private val binding get() = _binding
    //Arraylist of workout objects meant to store the users input
    private var workout_list = ArrayList<Workout>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainListBinding.inflate(layoutInflater)
        setContentView(binding!!.root)


        //button functonality
        binding!!.addbutton.setOnClickListener{
            addNewView()
        }

        binding!!.submitworkoutinfo!!.setOnClickListener {
            saveData()

        }
    }
    //Called whens the add new workout  button is pressed, creates new row for the prog
    private fun addNewView()
    {
        val inflater = LayoutInflater.from(this).inflate(R.layout.row_add_workout, null)
        binding!!.parentlayoutlinear.addView(inflater, binding!!.parentlayoutlinear.childCount)
    }

    //called when user chooses to submit data
    private fun saveData(){
        workout_list.clear()
        val count = binding!!.parentlayoutlinear.childCount
        var v : View?

        for(i in 0 until count) {
            v = binding!!.parentlayoutlinear.getChildAt(i)

            //Change the following line to receiver from Onclick list view in main activity search
            val workoutname: EditText = v.findViewById(R.id.wrkt_name)
            val target: Spinner = v.findViewById(R.id.mscle_spinner)

            //create object of Workout class in Values folder
            val Workout = Workout()
            Workout.workoutname = workoutname.toString()
            Workout.target = target.toString()

            //add the user workout information to created array of Workout objects
            workout_list.add(Workout)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}