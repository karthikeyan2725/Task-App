package com.example.taskapp.presentation

import android.text.Editable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskapp.domain.UserViewModel
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskPage(userViewModel: UserViewModel,addClick:()->Unit) {
    Box(
        modifier = Modifier
            .background(Color(0xFF5F95FF))
            .drawBehind {
                //Background White Circle
                drawCircle(
                    color = Color.White,
                    radius = size.width / (1.5F),
                    center = Offset(size.width / 2, size.height*(4/3))
                )
            }
    ){
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(10.dp)
        ){
            Icon(Icons.Filled.ArrowBack,
                tint = Color.White,
                contentDescription = "Go Back To Tasks",
                modifier = Modifier.clickable {addClick() }
                )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Text("\"Plan Your Work, and",
                    fontWeight = FontWeight(700),
                    fontSize = 30.sp,
                    color = Color.White
                )

                Text("work your plan\"",
                    fontWeight = FontWeight(650),
                    fontSize = 24.sp,
                    color = Color.White

                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            TaskEditCard(userViewModel, addClick = addClick)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditCard(userViewModel: UserViewModel,modifier:Modifier = Modifier,addClick: () -> Unit){

    val textModifier = Modifier.padding(10.dp)
    val editableRowModifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
    val iconModifier = Modifier
        .fillMaxWidth()

    var dateString by remember { mutableStateOf("12/01/2023") }
    var timeString by remember { mutableStateOf("12:00 PM") }

    //Description Part
    var taskDescription by remember { mutableStateOf("") }

    //Calendar part
    var calendarState = rememberSheetState()
    var clockState = rememberSheetState()
    var selectedDate by remember {mutableStateOf(LocalDate.now())}
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }

    CalendarDialog(
        state = calendarState,
        selection = CalendarSelection.Date{date->
            selectedDate = date
        }
    )

    ClockDialog(
        state = clockState,
        selection = ClockSelection.HoursMinutes{hour,minute->
            selectedTime =  LocalTime.of(hour,minute)
        }
    )

    //Adding Task Part
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp  ),
            modifier = modifier
                .padding(20.dp)
        ) {
            Column(
                modifier = modifier
                    .padding(vertical = 30.dp, horizontal = 10.dp)
            ){

                //Description of Task
                EditableLabel(
                    label = "Task Description",
                    modifier = textModifier
                )
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ) {
                    OutlinedTextField(
                        value = taskDescription,
                        onValueChange = { taskDescription = it },
                        modifier = modifier
                    )
                }

                Column(
                    modifier = modifier
                        .fillMaxWidth(0.5F)
                        //.background(color = Color.Gray)
                ){
                    //Edit Due Date Of task
                    EditableLabel(
                        label = "Due Date",
                        modifier = textModifier
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = editableRowModifier
                    ) {
                        Text(
                            text = selectedDate.format(DateTimeFormatter.ofPattern("DD/MM/yyyy"))
                        )
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            tint = Color(0xFF5F95FF),
                            contentDescription = "",
                            modifier = iconModifier
                                .clickable {calendarState.show()}
                        )
                    }

                    //Edit Due Time Of Task
                    EditableLabel(
                        label = "Due Date",
                        modifier = textModifier
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = editableRowModifier
                    ) {
                        Text(
                            text = selectedTime.format(DateTimeFormatter.ofPattern("KK:mm a"))
                        )
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            tint = Color(0xFF5F95FF),
                            contentDescription = "",
                            modifier = iconModifier
                                .clickable {clockState.show()}
                        )

                    }
                }

                Spacer(
                    modifier = modifier
                        .height(60.dp)
                )


                //Add Task Button
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            userViewModel.addTask(
                                dueDate = LocalDateTime.of(selectedDate,selectedTime),
                                description = taskDescription
                            )
                            addClick()
                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFF5F95FF))
                    ) {
                        Text(
                            "Add",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(800),
                            modifier = Modifier
                                .padding(vertical = 10.dp, horizontal = 30.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EditableLabel(label:String,modifier: Modifier){
    Text(
        text = label,
        fontWeight = FontWeight(800),
        modifier = modifier
    )
}