package com.example.stories.data

import androidx.room.*
import com.example.stories.model.Story

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStory(story: Story)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStories(stories: List<Story>)

    @Query("select * from Story limit 10")
    fun loadStories(): List<Story>?

    @Query("delete from Story")
    fun deleteAll()
}

@Database(entities = [Story::class], version = 1, exportSchema = false)
abstract class StoryDatabase : RoomDatabase() {
    abstract val storyDao: StoryDao
}
