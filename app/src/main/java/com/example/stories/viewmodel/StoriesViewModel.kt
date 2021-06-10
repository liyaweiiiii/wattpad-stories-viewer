package com.example.stories.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.stories.data.DataRepository
import com.example.stories.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class StoriesViewModel @Inject constructor(repository: DataRepository) : ViewModel() {

    private val storiesResult = repository.fetchStories()
        .shareIn(
            replay = 1,
            scope = viewModelScope,
            started = SharingStarted.Eagerly
        )

    val stories = storiesResult
        .filter { it.status == Result.Status.SUCCESS }
        .map { it.data }
        .asLiveData()

    val isLoading = storiesResult
        .map {
            it.status == Result.Status.LOADING
        }
        .asLiveData()

    val showError = storiesResult
        .filter { it.status == Result.Status.ERROR }
        .map { it.message }
        .asLiveData()

    val showEvent = storiesResult
        .filter { it.status == Result.Status.EVENT }
        .map { it.message }
        .asLiveData()
}