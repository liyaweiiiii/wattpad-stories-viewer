package com.example.stories.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stories.R
import com.example.stories.data.DataRepository
import com.example.stories.data.getDatabase
import com.example.stories.network.NetworkClient
import com.example.stories.viewmodel.StoriesViewModel
import com.example.stories.viewmodel.StoriesViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stories_list.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        stories_list.adapter = StoriesAdapter()

        val repository = DataRepository(NetworkClient, getDatabase(this).storyDao)
        val model = ViewModelProviders.of(this, StoriesViewModelFactory(repository)).get(StoriesViewModel::class.java)

        subscribeUi(model)

        if (savedInstanceState == null){
            model.loadStories()
        }
    }

    private fun subscribeUi(viewModel: StoriesViewModel) {
        viewModel.stories.observe(this, Observer { stories ->
            stories?.let {
                (stories_list.adapter as StoriesAdapter).setStoryList(it)
            }
        })

        viewModel.getIsLoading().observe(this, Observer { value ->
            value?.let { show ->
                loading_spinner.visibility = if (show) View.VISIBLE else View.GONE
            }
        })

        viewModel.shouldShowError().observe(this, Observer { value ->
            value?.let { show ->
                tv_error.visibility = if (show) View.VISIBLE else View.GONE
            }
        })

        viewModel.shouldShowOfflineWarning().observe(this, Observer { value ->
            value?.let { show ->
                tv_offline.visibility = if (show) View.VISIBLE else View.GONE
            }
        })
    }
}
