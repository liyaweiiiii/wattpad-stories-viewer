package com.example.stories.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stories.databinding.ActivityMainBinding
import com.example.stories.viewmodel.StoriesViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: StoriesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.storiesList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.storiesList.adapter = StoriesAdapter()

        subscribeUi(viewModel)
    }

    private fun subscribeUi(viewModel: StoriesViewModel) {
        viewModel.stories.observe(this, { stories ->
            stories?.let {
                (binding.storiesList.adapter as StoriesAdapter).setStoryList(it)
            }
        })

        viewModel.isLoading.observe(this, {
            binding.loadingSpinner.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.showError.observe(this, {
            it?.let {
                Snackbar.make(findViewById(android.R.id.content), it, Snackbar.LENGTH_INDEFINITE)
                    .show()
            }
        })

        viewModel.showEvent.observe(this, {
            it?.let {
                Snackbar.make(findViewById(android.R.id.content), it, Snackbar.LENGTH_SHORT).show()
            }
        })
    }
}
