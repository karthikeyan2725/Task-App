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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.compose.TaskAppTheme
import com.example.taskapp.data.database.TaskDatabase
import com.example.taskapp.data.entity.Task
import com.example.taskapp.data.entity.User
import com.example.taskapp.domain.UserViewModel
import com.example.taskapp.presentation.AddTaskPage
import com.example.taskapp.presentation.LoginPage
import com.example.taskapp.presentation.SignUpPage
import com.example.taskapp.presentation.TaskPage
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.ZoneOffset

const val mainTag= "taskApp:main"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TaskAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val userViewModel = hiltViewModel<UserViewModel>()
                    val navController = rememberNavController()
                    val loggedIn = userViewModel.userState.collectAsState().value.loggedIn
                    NavHost(navController=navController,startDestination = "LoginPage"){
                        composable("LoginPage"){
                            LoginPage(userViewModel = userViewModel) {
                                navController.navigate("SignUpPage")
                            }
                            if(loggedIn){
                                navController.navigate("TaskPage")
                            }
                        }
                        composable("SignUpPage"){
                            SignUpPage(userViewModel = userViewModel){
                                navController.navigate("LoginPage")
                            }
                            if(loggedIn){
                                navController.navigate("TaskPage")
                            }
                        }
                        composable("TaskPage"){
                            TaskPage(userViewModel,
                                navigateOnClick = {navController.navigate("AddTaskPage")},
                                navigateOnClickOfCard = {}
                            )
                        }
                        composable("AddTaskPage"){
                            AddTaskPage(userViewModel){
                                navController.popBackStack()
                            }
                        }
                    }
                }
            }
        }
    }
}

