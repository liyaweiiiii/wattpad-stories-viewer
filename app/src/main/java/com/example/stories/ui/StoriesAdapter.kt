package com.example.stories.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stories.databinding.ViewholderStoryItemBinding
import com.example.stories.model.Story
import com.squareup.picasso.Picasso

class StoriesAdapter : RecyclerView.Adapter<StoryViewHolder>() {

    private var mStoryList = listOf<Story>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ViewholderStoryItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun getItemCount() = mStoryList.size

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.onBind(mStoryList[position])
    }

    fun setStoryList(list: List<Story>) {
        mStoryList = list
        notifyDataSetChanged()
    }

}

class StoryViewHolder(private val binding: ViewholderStoryItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(story: Story) {
        binding.apply {
            tvTitle.text = story.title
            tvAuthorName.text = story.user.name
            Picasso.get().load(story.cover).into(ivCover)
            Picasso.get().load(story.user.avatar).into(ivAuthorAvatar)
        }

    }
}
