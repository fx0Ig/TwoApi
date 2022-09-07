package com.example.twoapi.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(

    val name: String,
    val picture: String,
    val source: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
