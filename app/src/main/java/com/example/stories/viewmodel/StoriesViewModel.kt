package com.example.stories.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stories.data.DataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class StoriesViewModel(val repository: DataRepository) : ViewModel(), CoroutineScope {
    private val viewModelJob = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + viewModelJob

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    val stories = repository.stories

    private lateinit var isLoading: MutableLiveData<Boolean>

    fun getIsLoading(): LiveData<Boolean> {
        if (!::isLoading.isInitialized) {
            isLoading = MutableLiveData()
        }
        return isLoading
    }

    private lateinit var showError: MutableLiveData<Boolean>

    fun shouldShowError(): LiveData<Boolean> {
        if (!::showError.isInitialized) {
            showError = MutableLiveData()
        }
        return showError
    }

    private lateinit var showOfflineWarning: MutableLiveData<Boolean>

    fun shouldShowOfflineWarning(): LiveData<Boolean> {
        if (!::showOfflineWarning.isInitialized) {
            showOfflineWarning = MutableLiveData()
            showOfflineWarning.value = false
        }
        return showOfflineWarning
    }

    fun loadStories() {
        launch {
            try {
                isLoading.value = true
                repository.refreshStories()
            } catch (e: Exception) {
                //Either network or db failed, incase of network failure, display cached data.
                stories.value?.let {
                    if (it.isNotEmpty()){
                        //Displaying cached result
                        showError.value = false
                        showOfflineWarning.value = true
                    } else {
                        //No data, show error message
                        showError.value = true
                        showOfflineWarning.value = false
                    }
                }
                e.printStackTrace()
            } finally {
                isLoading.value = false
            }
        }
    }
}