package com.warmerhammer.fitnessapp.HomeFragment

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HomeFragmentViewModelFactory(context: Context) : ViewModelProvider.Factory {


    private val repository = HomeFragmentRepository(context)

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeFragmentViewModel(repository) as T
    }
}