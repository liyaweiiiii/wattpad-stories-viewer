package com.example.stories.model

import com.google.gson.annotations.SerializedName

data class User(val name: String,
                val avatar: String,
                @SerializedName("fullname") val fullName: String)
