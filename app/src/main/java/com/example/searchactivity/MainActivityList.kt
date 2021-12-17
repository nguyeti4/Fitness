package com.example.searchactivity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

import com.example.searchactivity.databinding.ActivityMainListBinding

class MainActivityList : AppCompatActivity() {
    private var _binding: ActivityMainListBinding? = null
    private val binding get() = _binding
    private var p = 1

    //Arraylist of workout objects meant to store the users input
    private var workoutList = ArrayList<Workout>()
    //tracks the text view indice of the child layout that the user wants to input information to
    private var indice = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //creates an adapter for the activity main list xml file when the app is opened
        _binding = ActivityMainListBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        //button functonality for the "add workout button" calls the "addNewView" function
        binding!!.addbutton.setOnClickListener {
            addNewView()
        }
        //button functionality for the submit workout info button. calls the "saveData" function
        binding!!.submitworkoutinfo.setOnClickListener {
            saveData()

        }
    }

    //Called whens the add new workout  button is pressed, creates new row for the prog
    private fun addNewView() {
        //creates an inflater adapter that pulls an row_add_workout.xml view and adds it into the screen
        val inflater = LayoutInflater.from(this).inflate(R.layout.row_add_workout, null)
        binding!!.parentlayoutlinear.addView(inflater, binding!!.parentlayoutlinear.childCount)
        var v: View?
        //sets the current indice to track the expected location for user input
        indice = binding!!.parentlayoutlinear.childCount - 1
        v = binding!!.parentlayoutlinear.getChildAt(indice)
        val workout: EditText = v.findViewById(R.id.wrkt_name)
        Log.i("Indice:", indice.toString())
        //sets on click listener in the edit text fields of each inflated layout which calls a searchActivityLauncher
        workout.setOnClickListener {
            val intent = Intent(this, MainActivitySearch::class.java)
            searchActivityLauncher.launch(intent)
        }
    }

    private fun onClick(view: View) {
        var v = LayoutInflater.from(this).inflate(R.layout.row_add_workout, null)
        var workout: EditText = v.findViewById(R.id.wrkt_name)
        Toast.makeText(this, "Clicked on", Toast.LENGTH_LONG)
        workout.setOnClickListener {
            val intent = Intent(this, MainActivitySearch::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    public fun setIndice(int: Int) {
        this.indice = int
    }

    //called when user chooses to submit data
    private fun saveData() {
        workoutList.clear()
        val count = binding!!.parentlayoutlinear.childCount
        var v: View?
        for (i in 0 until count) {
            v = binding!!.parentlayoutlinear.getChildAt(i)

            //Change the following line to receiver from Onclick list view in main activity search
            val workoutname: EditText = v.findViewById(R.id.wrkt_name)
            val target: Spinner = v.findViewById(R.id.mscle_spinner)

            //create object of Workout class in Values folder
            val Workout = Workout()
            Workout.workoutname = workoutname.toString()
            Workout.target = target.toString()

            //add the user workout information to created array of Workout objects
            workoutList.add(Workout)
        }
    }

    private fun openNewActivity() {
        val intent = Intent(this, MainActivitySearch::class.java)
        startActivity(intent)


    }

    private val searchActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val intent = result.data
            var count = binding!!.parentlayoutlinear.childCount
            var v: View?
            Log.i("Function:", "OnResume")
            val Workout = intent!!.getStringExtra("Workout")!!
            Log.i("MainActivityList:", "workout" + Workout)
            for (i in 0 until count) {
                v = binding!!.parentlayoutlinear.getChildAt(i)
                if (i == indice) {
                    val workoutslot: EditText = v.findViewById((R.id.wrkt_name))
                    workoutslot.setText(Workout)
                }

            }


        }
}