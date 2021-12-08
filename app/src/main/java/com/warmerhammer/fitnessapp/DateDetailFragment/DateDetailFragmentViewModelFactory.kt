package com.warmerhammer.fitnessapp.DateDetailFragment

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.warmerhammer.fitnessapp.HomeFragment.HomeFragmentRepository
import com.warmerhammer.fitnessapp.HomeFragment.HomeFragmentViewModel

class DateDetailFragmentViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val repository = HomeFragmentRepository(context)

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailFragmentViewModel(repository) as T
    }

}