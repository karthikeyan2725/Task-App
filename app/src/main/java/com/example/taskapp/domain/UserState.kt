package com.example.taskapp.domain

data class UserState (
    val uid :Long? = null,
    val name:String? = null,
    val email : String? = null,
    val password : String? = null,
    val loggedIn : Boolean = false
)