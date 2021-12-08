package com.warmerhammer.fitnessapp.DateDetailFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.warmerhammer.fitnessapp.HomeFragment.WorkOutSummaryComponent.Workout
import com.warmerhammer.fitnessapp.R
import com.warmerhammer.fitnessapp.databinding.FragmentDateDetailViewBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import java.util.*

@ExperimentalCoroutinesApi
@FlowPreview
class DateDetailFragmentView : Fragment() {
    private var binding : FragmentDateDetailViewBinding? = null
    private lateinit var viewModel: DetailFragmentViewModel
    private lateinit var viewModelFactory: DateDetailFragmentViewModelFactory
    private var detailFragmentListOfWorkouts = ArrayList<Workout>()
    private val args: DateDetailFragmentViewArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDateDetailViewBinding.inflate(inflater, container, false)
        //setup viewModelFactory
        viewModelFactory = DateDetailFragmentViewModelFactory(requireContext())
        // set detail fragment list of workouts
        setUpViewModel()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date_detail_view, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardViewRC : RecyclerView = view.findViewById(R.id.dateDetailFragmentRC)
        cardViewRC.layoutManager = LinearLayoutManager(requireContext())
        // observe workouts
        viewModel.workouts.observe(this as LifecycleOwner, { workouts ->
            detailFragmentListOfWorkouts = workouts
            Log.i(TAG, "detailFragmentListOfWorkouts :: $detailFragmentListOfWorkouts")
            cardViewRC.adapter = DateDetailFragmentRCAdapter(detailFragmentListOfWorkouts)
            updateDiagram()
        })
        // load workouts
        viewModel.fetchWorkoutsByDate(args.date)
    }

    private fun setUpViewModel() {
        viewModel =
            ViewModelProvider(this, DateDetailFragmentViewModelFactory(requireContext())).get(
                DetailFragmentViewModel::class.java
            )
    }

    private fun updateDiagram() {
        val frontBodyImageView = requireView().findViewById<ImageView>(R.id.frontBodyDiagram)
        val backBodyImageView = requireView().findViewById<ImageView>(R.id.backBodyDiagram)

        val muscleSet = mutableSetOf<String>()
        // builds set of each muscle worked as string
        for (workout in detailFragmentListOfWorkouts) {
            workout.muscles.forEach{ individualMuscleString ->
                muscleSet.add(individualMuscleString)
            }
        }

        Log.i("DateDetailFragmentView", "muscleSet :: $muscleSet")

        // retrieve pair of back and front MuscleDiagram objects
        val frontAndBackDiagramPair = viewModel.fetchMuscleDiagram(muscleSet)

        val frontDiagramImageResource = frontAndBackDiagramPair.first.getImageResource()
        val backDiagramImageResource = frontAndBackDiagramPair.second.getImageResource()

        Log.i("DateDetailFragmentView", "frontDiagramImageResource :: $frontDiagramImageResource")

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

    companion object {
        private const val TAG = "DateDetailFragmentView"
    }
}