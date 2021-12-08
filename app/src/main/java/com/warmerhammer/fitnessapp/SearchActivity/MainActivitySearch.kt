package com.example.searchactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.warmerhammer.fitnessapp.databinding.MainActivitySearchBinding

class MainActivitySearch : AppCompatActivity() {

    lateinit var binding: MainActivitySearchBinding
    private lateinit var list: SearchActivityList
    private var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val workout = arrayOf("Push-ups", "Squats", "Pull-ups", "Bicep Curl", "Chest Dip", "Dips", "Tricep Extension", "Standing Barbell Curl",
        "Leg Press", "Calf-raise", "Calf raise", "Tricep Kickback", "Bent over row", "Seated row", "Lunge", "Bicycle Crunches", "Leg Raise",
        "Chest Fly", "Incline Push-ups", "Decline Push-Ups", "Chin-ups", "Russian Twist", "Wrist Curls", "Overhead Press", "Military Press",
        "Front Raise", "Lateral Raise", "Bench Press", "Rear Delt Raise", "Rear Deltoid Raise", "Delt Raise", "Deadlift")

        val userAdapter : ArrayAdapter<String> = ArrayAdapter(
            this, android.R.layout.simple_list_item_1,
            workout)

        binding.list1.adapter = userAdapter
        binding.list1.setOnItemClickListener { _, _, index,_->
            Toast.makeText(this, "Clicked on ${workout[index]} ", Toast.LENGTH_LONG).show()
            true
            val intent = Intent(this, SearchActivityList::class.java)
                Log.i("MainAcivitySearch:", "Workout: ${workout[index]}")
                intent.putExtra("Workout", workout[index])
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            setResult(RESULT_OK,intent)
            this.finish()
        }


            binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {


                override fun onQueryTextSubmit(query: String?): Boolean {
                    binding.searchBar.clearFocus()
                    if (workout.contains(query)) {
                        userAdapter.filter.filter(query)
                    }else{
                        Toast.makeText(this@MainActivitySearch, "Workout not found", Toast.LENGTH_LONG)
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    userAdapter.filter.filter(newText)
                    return false
                }

            })
        }
    }





