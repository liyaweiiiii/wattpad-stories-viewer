package com.example.stories.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.stories.MainCoroutineRule
import com.example.stories.data.IDataRepository
import com.example.stories.model.Result
import com.example.stories.model.Story
import com.example.stories.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test


class StoriesViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: StoriesViewModel

    @Test
    fun showStories() {
        viewModel = StoriesViewModel(MyFakeRepositoryForStories())
        viewModel.stories.observeForever {}
        viewModel.load()
        assertEquals(viewModel.stories.value?.size, 1)
        assertEquals(viewModel.stories.value?.first()?.id, 0)
    }
}

class MyFakeRepositoryForStories : IDataRepository {
    override fun fetchStories() = flow {
        emit(Result.success(listOf(Story(0, "", "", User("", "", "")))))
    }
}
