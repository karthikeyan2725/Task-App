package com.example.taskapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.taskapp.data.entity.User

@Dao
interface UserDao {
    @Insert
    fun insertUser(user:User)

    @Query("DELETE FROM user WHERE uid = :uid")
    fun deleteUser(uid:Int)

    @Query("SELECT * FROM user WHERE uid = :uid")
    fun getUser(uid:Int):User?

    @Query("SELECT * FROM user")
    fun getAllUser():User?
}