package com.example.taskapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.taskapp.data.Converters.DateTimeConverter
import com.example.taskapp.data.dao.TaskDao
import com.example.taskapp.data.dao.UserDao
import com.example.taskapp.data.entity.Task
import com.example.taskapp.data.entity.User

@Database(entities = [User::class, Task::class],version = 1)
@TypeConverters(DateTimeConverter::class)
abstract class TaskDatabase:RoomDatabase( ) {
    abstract fun userDao():UserDao
    abstract fun taskDao():TaskDao
}