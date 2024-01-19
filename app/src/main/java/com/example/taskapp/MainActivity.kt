package com.example.taskapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.example.taskapp.data.database.TaskDatabase
import com.example.taskapp.data.entity.Task
import com.example.taskapp.data.entity.User
import com.example.taskapp.presentation.ui.theme.TaskAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.ZoneOffset

const val mainTag= "taskApp:main"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(
            applicationContext,
            TaskDatabase::class.java,
            "task_database"
        ).build()
        runBlocking {
            launch (Dispatchers.IO){
                /*
                db.userDao().insertUser(User(

                    uid=0,
                    password = "hola",
                    email = "jumpy"
                ))
                Log.d(mainTag, db.userDao().getAllUser().toString())
                 */
                db.taskDao().insertTask(Task(
                    tid=0,
                    uid=1,
                    dueDate = LocalDateTime.ofEpochSecond(1705559483,0, ZoneOffset.UTC),
                    description = "Bath"
                ))
                Log.d(mainTag,db.taskDao().getAllTasks().toString())
            }
        }
        setContent {
            TaskAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}

