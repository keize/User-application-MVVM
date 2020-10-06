package com.example.mvvmaplication.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmaplication.SubscriberViewModel

class SubscriberViewModelFactory (private val reposirory: SubscriberReposirory) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SubscriberViewModel::class.java)){
            return SubscriberViewModel(reposirory) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }


}