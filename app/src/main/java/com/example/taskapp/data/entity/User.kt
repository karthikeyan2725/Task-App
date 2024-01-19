package com.example.taskapp.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName="user",
    indices = [Index(value=["email"], unique = true)]
)
data class User(
    @PrimaryKey(autoGenerate = true)
    val uid : Int,

    val email : String,

    val password: String
)