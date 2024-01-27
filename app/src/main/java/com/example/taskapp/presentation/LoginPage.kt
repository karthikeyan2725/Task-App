package com.example.taskapp.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.ImeOptions
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskapp.domain.UserViewModel

@Composable
fun LoginPage(
    userViewModel: UserViewModel,
    onclick:()->Unit
){
    val userState = userViewModel.userState.collectAsState()
    val spacerModifier = Modifier.height(20.dp)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        // Login Page Title
        Text(text = "Login",
            fontSize = 30.sp,
            fontWeight = FontWeight(800)
        )
        Spacer(modifier = Modifier.height(40.dp))

        // Email Field
        EditableField(
            label = "Email",
            stateAttribute = "email",
            fieldValue = userState.value.email?:"",
            userViewModel = userViewModel ,
            modifier = spacerModifier
        )

        EditableField(
            label = "Password",
            stateAttribute = "password",
            fieldValue = userState.value.password?:"",
            userViewModel = userViewModel ,
            isPassword = true,
            modifier = spacerModifier
        )


        // Login button
        Button(
            onClick = { userViewModel.loginUser() }
        ) {
            Text("Login")
        }
        Spacer(modifier = spacerModifier)

        // Sign up Option text
        Text(
            text = "No Account, Sign Up Instead",
            modifier = Modifier.clickable { onclick() }
        )
    }
}

@Composable
fun EditableField(
    label : String,
    stateAttribute : String,
    fieldValue : String,
    userViewModel:UserViewModel,
    isPassword:Boolean = false,
    modifier: Modifier
){
    Column() {
        LoginLabel(string = label)
        OutlinedTextField(
            value = fieldValue,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            onValueChange = { userViewModel.updateState(stateAttribute, it) },
        )
    }
    Spacer(modifier = modifier)
}

@Composable
fun LoginLabel(string:String,modifier : Modifier = Modifier){
    Row(){
       Text(
           text = string,
           modifier = modifier
       )
    }
}
