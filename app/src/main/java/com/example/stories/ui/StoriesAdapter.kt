package com.example.stories.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stories.R
import com.example.stories.model.Story
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.viewholder_story_item.view.*

class StoriesAdapter : RecyclerView.Adapter<StoryViewHolder>() {

    private var mStoryList = listOf<Story>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        return StoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.viewholder_story_item, parent, false))
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

class StoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun onBind(story: Story) {
        itemView.apply {
            itemView.tv_title.text = story.title
            itemView.tv_author_name.text = story.user.name
            Picasso.get().load(story.cover).into(iv_cover)
            Picasso.get().load(story.user.avatar).into(iv_author_avatar)
        }

    }

}