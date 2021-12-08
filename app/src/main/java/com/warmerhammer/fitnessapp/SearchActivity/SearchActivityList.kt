package com.example.searchactivity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.result.contract.ActivityResultContracts

import com.warmerhammer.fitnessapp.HomeFragment.WorkOutSummaryComponent.Workout
import com.warmerhammer.fitnessapp.R
import com.warmerhammer.fitnessapp.databinding.ActivityMainListBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SearchActivityList : AppCompatActivity() {
    private var _binding: ActivityMainListBinding? = null
    private val binding get() = _binding
    private var p = 1

    //Arraylist of workout objects meant to store the users input
    private var workoutList = ArrayList<Pair<String, String>>()
    private var indice = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainListBinding.inflate(layoutInflater)
        setContentView(binding!!.root)


        //button functionality
        binding!!.addbutton.setOnClickListener {
            Log.i("SearchActivityList", "addButton.onClickListener")
            addNewView()
        }

        findViewById<Button>(R.id.submitworkoutinfo).setOnClickListener {
            Log.i("SearchActivityList", "submitButton.onClickListener")
            saveData()
        }
    }

    //Called whens the add new workout  button is pressed, creates new row for the prog
    private fun addNewView() {
        val inflater = LayoutInflater.from(this).inflate(R.layout.row_add_workout, null)
        binding!!.parentlayoutlinear.addView(inflater, binding!!.parentlayoutlinear.childCount)
        var v: View?
        indice = binding!!.parentlayoutlinear.childCount - 1
        v = binding!!.parentlayoutlinear.getChildAt(indice)
        val workout: EditText = v.findViewById(R.id.wrkt_name)
        workout.setOnClickListener {
            val intent = Intent(this, MainActivitySearch::class.java)
            searchActivityLauncher.launch(intent)
        }
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
            val workout = Workout(Calendar.getInstance().time)
            workout.title = workoutname.text.toString()
            workout.muscles = arrayListOf(target.selectedItem.toString())

            // add the user workout information to created array of Workout objects
            workoutList.add(Pair(workout.title, target.selectedItem.toString()))
        }

        Log.i("SearchActivityList", "workoutList :: $workoutList")
        val intent = Intent()
        intent.putExtra("workoutList", workoutList)

        setResult(RESULT_OK, intent)
        this.finish()
    }

//    private fun onClick(view: View) {
//        var v = LayoutInflater.from(this).inflate(R.layout.row_add_workout, null)
//        var workout: EditText = v.findViewById(R.id.wrkt_name)
//        Toast.makeText(this, "Clicked on", Toast.LENGTH_LONG)
//        workout.setOnClickListener {
//            val intent = Intent(this, MainActivitySearch::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
//            startActivity(intent)
//        }
//    }
//
//    public fun setIndice(int: Int) {
//        this.indice = int
//    }


//    private fun openNewActivity() {
//        val intent = Intent(this, MainActivitySearch::class.java)
//        startActivity(intent)
//
//
//    }


//override fun onResume() {
//super.onResume()
//val count = binding!!.parentlayoutlinear.childCount
//var v: View?
//var intent = intent
//
//
//
//Log.i("Function:", "OnResume")
//val Workout = intent.getStringExtra("Workout")!!
//Log.i("MainActivityList:", "workout" +Workout)
//for (i in 0 until count) {
//    v = binding!!.parentlayoutlinear.getChildAt(i)
//    if (i == indice) {
//        val workoutslot: EditText = v.findViewById((R.id.wrkt_name))
//            workoutslot.setText(Workout)
//
//    }
//}
//}


    /* override fun onDestroy() {
super.onDestroy()
_binding = null
} */


}