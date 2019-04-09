package com.example.stories.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.stories.model.Story

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStory(story: Story)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStories(stories: List<Story>)

    @Query("select * from Story limit 10")
    fun loadStories(): LiveData<List<Story>>
}

@Database(entities = [Story::class], version = 1, exportSchema = false)
abstract class StoryDatabase : RoomDatabase() {
    abstract val storyDao: StoryDao
}

private lateinit var INSTANCE: StoryDatabase

/**
 * Instantiate a database from a context.
 */
fun getDatabase(context: Context): StoryDatabase {
    synchronized(StoryDatabase::class) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room
                .databaseBuilder(
                    context.applicationContext,
                    StoryDatabase::class.java,
                    "stories_db"
                )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}