package com.example.mvvmaplication

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmaplication.database.Subscriber
import com.example.mvvmaplication.database.SubscriberReposirory
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SubscriberViewModel(private val reposirory: SubscriberReposirory) : ViewModel(), Observable {

    val subscribers = reposirory.subscribers
    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete: Subscriber



    @Bindable
    val inputName = MutableLiveData<String>()

    @Bindable
    val inputEmail = MutableLiveData<String>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"

    }

    fun saveOrUpdate(){

        if(isUpdateOrDelete) {
            subscriberToUpdateOrDelete.name = inputName.value!!
            subscriberToUpdateOrDelete.email = inputEmail.value!!
            update(subscriberToUpdateOrDelete)
        } else {
            val name = inputName.value!!
            val email = inputEmail.value!!

            insert(Subscriber(0, name, email))

            inputName.value = null
            inputEmail.value = null

        }

    }

    fun clearALLOrDelete() {

        if(isUpdateOrDelete) {
            delete(subscriberToUpdateOrDelete)
        } else{
            clearAll()
        }
    }

    fun insert(subscriber: Subscriber) : Job = viewModelScope.launch() {
        reposirory.insert(subscriber)
        val noOfRows = reposirory.update(subscriber)
        if (noOfRows > 0) {
            inputName.value = null
            inputEmail.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
        }

    }

    fun update(subscriber: Subscriber) : Job = viewModelScope.launch {
        reposirory.update(subscriber)

            inputName.value = null
            inputEmail.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"

    }

    fun delete(subscriber: Subscriber) : Job = viewModelScope.launch {
        reposirory.delete(subscriber)

        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear all"
    }

    fun clearAll() : Job = viewModelScope.launch {
        reposirory.deleteAll()
    }

    fun initUpdateandDelete(subscriber: Subscriber){

        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}