package com.warmerhammer.fitnessapp.HomeFragment

import android.app.Activity
import android.app.Activity.*
import android.app.appsearch.AppSearchResult.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.searchactivity.SearchActivityList
import com.warmerhammer.fitnessapp.HomeFragment.CalendarComponent.CalendarAdapter
import com.warmerhammer.fitnessapp.HomeFragment.WorkOutSummaryComponent.Workout
import com.warmerhammer.fitnessapp.HomeFragment.WorkOutSummaryComponent.WorkoutItemRecyclerViewAdapter
import com.warmerhammer.fitnessapp.R
import com.warmerhammer.fitnessapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

@ExperimentalCoroutinesApi
@FlowPreview
class HomeFragmentView : Fragment(R.layout.fragment_home) {

    private var binding: FragmentHomeBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WorkoutItemRecyclerViewAdapter
    private lateinit var viewModel: HomeFragmentViewModel
    private lateinit var viewModelFactory: HomeFragmentViewModelFactory
    private var homeFragmentListOfWorkouts = ArrayList<Workout>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // setup HomeFragmentViewModel
        viewModelFactory = HomeFragmentViewModelFactory(requireContext())

        setupViewModel()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(requireContext())

        recyclerView = view.findViewById(R.id.workoutSummaryRecyclerView)
        recyclerView.layoutManager = layoutManager

        Log.i("HomeFragment", "Click: $recyclerView")

        adapter = WorkoutItemRecyclerViewAdapter(
            requireContext(),
            homeFragmentListOfWorkouts
        ) {
            viewModel.loadWorkouts(null)
        }

        recyclerView.adapter = adapter

        observeWorkouts()  // set up live data observer for new workouts

        viewModel.loadWorkouts(null)  // retrieve workouts on init
        setupAddWorkoutButton()  // setup addWorkoutButton
        setupDate() // initialize date display
        setUpCalendarComponent()  // loads calendar
    }

    private fun observeWorkouts() {
        viewModel.workouts.observe(this as LifecycleOwner, { listOfWorkouts ->
            homeFragmentListOfWorkouts = listOfWorkouts
            updateDiagram()  // always update diagram
            Log.i("HomeFragmentView", "observe: $listOfWorkouts")

            recyclerView.adapter = WorkoutItemRecyclerViewAdapter(
                requireContext(),
                homeFragmentListOfWorkouts
            ) {
                viewModel.loadWorkouts(null)
            }

            // show list of workouts if list is not empty
            // else show holding text
            if (!homeFragmentListOfWorkouts.isEmpty()) {
                requireView().findViewById<TextView>(R.id.holdingText).visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            } else {
                requireView().findViewById<TextView>(R.id.holdingText).visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }
        })
    }

    val searchActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data

                val workoutList =
                    intent?.extras?.get("workoutList") as ArrayList<Pair<String, String>>
                workoutList.forEach { pair ->
                    val workout = Workout(Calendar.getInstance().time)
                    workout.title = pair.first
                    workout.muscles = arrayListOf(pair.second)
                    viewModel.saveSelectedWorkout(workout)
                }
                viewModel.loadWorkouts(null)
                setUpCalendarComponent()
            }
        }

    private fun setupAddWorkoutButton() {
        val addWorkoutButton = requireView().findViewById<Button>(R.id.add_workout_text_view)

        addWorkoutButton.setOnClickListener {
            val i = Intent(requireContext(), SearchActivityList::class.java)
            searchActivityForResult.launch(i)
        }
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this, HomeFragmentViewModelFactory(requireContext())).get(
                HomeFragmentViewModel::class.java
            )
    }

    private fun setupDate() {
        val dateTextview = requireView().findViewById<TextView>(R.id.fragment_home_date_tv)
        val dateFormatter = object : SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()) {}
        "Workouts on ${dateFormatter.format(Date())}".also { dateTextview.text = it }
    }

    private fun updateDiagram() {
        val frontBodyImageView = requireView().findViewById<ImageView>(R.id.frontBodyDiagram)
        val backBodyImageView = requireView().findViewById<ImageView>(R.id.backBodyDiagram)

        val muscleSet = mutableSetOf<String>()
        // builds set of each muscle worked as string
        for (workout in homeFragmentListOfWorkouts) {
            workout.muscles.forEach { individualMuscleString ->
                muscleSet.add(individualMuscleString)
            }
        }

        Log.i("HomeFragmentView", "muscleSet :: $muscleSet")

        // retrieve pair of back and front MuscleDiagram objects
        val frontAndBackDiagramPair = viewModel.fetchMuscleDiagram(muscleSet)

        val frontDiagramImageResource = frontAndBackDiagramPair.first.getImageResource()
        val backDiagramImageResource = frontAndBackDiagramPair.second.getImageResource()

        Log.i("HomeFragmentView", "frontDiagramImageResource :: $frontDiagramImageResource")

        frontBodyImageView.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                frontDiagramImageResource
            )
        )

        backBodyImageView.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                backDiagramImageResource
            )
        )
    }

    // calendar component - BEGIN

    private fun setUpCalendarComponent() {

        val backButton: ImageButton =
            requireActivity().findViewById(R.id.custom_calendar_left_chevron)
        val forwardButton: ImageButton =
            requireActivity().findViewById(R.id.custom_calendar_right_chevron)

        var selectedDate = LocalDate.now()
        setUpCalendarComponentRC(selectedDate)

        // set up buttons
        backButton.setOnClickListener {
            selectedDate = selectedDate.minusMonths(1)
            setUpCalendarComponentRC(selectedDate)
        }

        // set up buttons
        forwardButton.setOnClickListener {
            selectedDate = selectedDate.plusMonths(1)
            setUpCalendarComponentRC(selectedDate)
        }

    }

    private fun setUpCalendarComponentRC(selectedDate: LocalDate) {
        val calendarRecyclerView: (RecyclerView) =
            requireActivity().findViewById(R.id.calendarRecyclerView)
        val monthYearTextView: (TextView) =
            requireActivity().findViewById(R.id.customCalendarMonthYear_tv)

        // format date
        val dateFormatter = DateTimeFormatter.ofPattern("MMM yyy")
        val monthYear = dateFormatter.format(selectedDate).toString()
        monthYearTextView.text = monthYear.uppercase()

        // retrieve list of stored dates
        val listOfAllWorkoutsByDate = viewModel.retrieveDates(monthYear)
        val setOfDates = mutableSetOf<String>()

        // add one date per day that has workouts
        listOfAllWorkoutsByDate.forEach { mapOfWorkoutsByDate ->
            setOfDates.add(mapOfWorkoutsByDate.key)
        }

        val daysInMonth = daysInMonthArray(selectedDate)
        val calendarAdapter = CalendarAdapter(daysInMonth, setOfDates) { dayOfMonth ->
            Log.i("HomeFragmentView", "dayOfMonth :: $dayOfMonth")
            val detailDateFormat = DateTimeFormatter.ofPattern("MM/${dayOfMonth}/yyyy")
            val action =
                HomeFragmentViewDirections.actionFirstFragmentToDateDetailFragmentView(
                    detailDateFormat.format(selectedDate)
                )
            findNavController().navigate(action) // navigate to details page
        }

        val layoutManager = GridLayoutManager(requireContext(), 7)
        calendarRecyclerView.layoutManager = layoutManager
        calendarRecyclerView.adapter = calendarAdapter
    }

    private fun daysInMonthArray(date: LocalDate): ArrayList<String> {
        val daysInMonthArray = ArrayList<String>()
        val yearMonth = YearMonth.from(date)
        val daysInMonth = yearMonth.lengthOfMonth()

        val firstOfMonth = date.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value

        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("")
            } else {
                daysInMonthArray.add((i - dayOfWeek).toString())
            }
        }

        return daysInMonthArray
    }
    // calendar component - END

}