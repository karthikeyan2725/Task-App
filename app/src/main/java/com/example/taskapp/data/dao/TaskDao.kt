package com.example.taskapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.taskapp.data.entity.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert
    fun insertTask(task: Task)

    @Query("DELETE FROM task WHERE tid = :tid")
    fun deleteTask(tid:Int)

    @Query("SELECT * from task WHERE uid=:uid")
    fun getTasksOf(uid:Int): Flow<List<Task>>

    @Query("SELECT * FROM task")
    fun getAllTasks():List<Task>
}