package com.example.taskapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "task"
)
data class Task(
    @PrimaryKey(autoGenerate = true)
    val tid:Int,

    val uid:Long,

    val done:Int,

    @ColumnInfo(name = "due_date")
    val dueDate :LocalDateTime,

    val description:String
)