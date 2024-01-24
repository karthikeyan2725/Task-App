package com.example.taskapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.taskapp.data.entity.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user:User):Long

    @Query("DELETE FROM user WHERE uid = :uid")
    suspend fun deleteUser(uid:Int)

    @Query("SELECT * FROM user WHERE uid = :uid")
    suspend fun getUserWithUid(uid:Int):User?

    @Query("SELECT * FROM user WHERE email=:email")
    suspend fun getUserWithEmail(email:String):User?

    @Query("SELECT * FROM user")
    suspend fun getAllUser():List<User>
}