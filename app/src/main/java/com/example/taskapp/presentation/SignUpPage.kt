package com.example.taskapp.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.taskapp.domain.UserViewModel
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SignUpPage(userViewModel: UserViewModel,onClick:()->Unit){
    val userState = userViewModel.userState.collectAsState()
    val loginEntryModifier = Modifier
    val spacerModifier = Modifier.height(20.dp)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        Text(text = "Sign Up",
            fontSize = 30.sp,
            fontWeight = FontWeight(800)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Column(
            loginEntryModifier
        ){
            LoginLabel(string = "Name")
            OutlinedTextField(
                value = userState.value.name ?:"",
                onValueChange = {userViewModel.updateState("name",it)}
            )
        }

        Spacer(modifier = spacerModifier)

        Column(
            loginEntryModifier
        ) {
            LoginLabel(string = "Email")
            OutlinedTextField(
                value = userState.value.email ?: "",
                onValueChange = { userViewModel.updateState("email", it) }
            )
        }

        Spacer(modifier = spacerModifier)

        Column(
            loginEntryModifier
        ) {
            LoginLabel(string = "Password")
            OutlinedTextField(
                value = userState.value.password ?: "",
                onValueChange = { userViewModel.updateState("password", it) }
            )
        }

        Spacer(modifier = spacerModifier)


        Button(onClick = {userViewModel.signUpUser()}) {
            Text("Sign Up")
        }

        Text(
            text = "Login Instead",
            modifier = Modifier.clickable { onClick() }
        )


    }
}


