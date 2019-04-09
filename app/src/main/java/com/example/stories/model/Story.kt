package com.example.stories.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Story(
    @PrimaryKey val id: Int,
    val title: String,
    val cover: String,
    @Embedded val user: User)