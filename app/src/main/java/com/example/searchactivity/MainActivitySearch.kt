package com.example.searchactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SearchView
import com.example.searchactivity.databinding.ActivityMainBinding

class MainActivitySearch : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var workout = arrayOf("Push-ups", "Squats", "Pull-ups", "Bicep Curl", "Chest Dip", "Dips", "Tricep Extension", "Standing Barbell Curl",
        "Leg Press", "Calf-raise", "Calf raise", "Tricep Kickback", "Bent over row", "Seated row", "Lunge", "Bicycle Crunches", "Leg Raise",
        "Chest Fly", "Incline Push-ups", "Decline Push-Ups", "Chin-ups", "Russian Twist", "Wrist Curls", "Overhead Press", "Military Press",
        "Front Raise", "Lateral Raise", "Bench Press", "Rear Delt Raise", "Rear Deltoid Raise", "Delt Raise")

        val userAdapter : ArrayAdapter<String> = ArrayAdapter(
            this,android.R.layout.simple_list_item_1,
            workout
        )

        binding.list1.adapter = userAdapter

        binding.list1.setOnClickListener{

        }


        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {


            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchBar.clearFocus()
                if(workout.contains(query)){
                    userAdapter.filter.filter(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                userAdapter.filter.filter(newText)
                return false
            }



             } )
    }
    }

