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
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
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

@Composable
fun AddTaskPage(userViewModel: UserViewModel,addClick:()->Unit) {
    val radiusDenominator = 1.5F
    val centerXFraction = 1/2F
    val centerYFraction = 8/7F
    val bluishColor = Color(0xFF5F95FF)
    val bgCircleColor = Color.White
    val columnModifier = Modifier.fillMaxWidth()
    Column(
        modifier = Modifier
            .background(bluishColor)
            .drawBehind {
                //Background White Circle
                drawCircle(
                    color = bgCircleColor,
                    radius = size.width / radiusDenominator,
                    center = Offset(
                        size.width * centerXFraction,
                        size.height * centerYFraction
                    )
                )
            }
    ){
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(10.dp)
        ){
            Icon(
                Icons.Filled.ArrowBack,
                tint = Color.White,
                contentDescription = "Go Back To Tasks",
                modifier = Modifier
                    .size(30.dp)
                    .clickable {addClick() }
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = columnModifier
        ) {

            QuoteRows(
                firstLine = "\"Plan Your Work, and",
                secondLine = "work your plan\"",
                modifier = columnModifier

            )

            Spacer(modifier = Modifier.height(20.dp))

            TaskEditCard(userViewModel, addClick = addClick)
        }
    }
}








@Composable
fun QuoteRows(
    firstLine : String,
    secondLine : String,
    modifier : Modifier,
    shadowStyle :TextStyle = TextStyle(
        shadow = Shadow(
            color = Color.Black,
            offset = Offset(10F,10F)
        )
    )
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,

    ){

        Text(
            text = firstLine,
            fontWeight = FontWeight(700),
            fontSize = 30.sp,
            color = Color.White,
            style = shadowStyle
        )

        Text(
            text = secondLine,
            fontWeight = FontWeight(650),
            fontSize = 24.sp,
            color = Color.White,
            style = shadowStyle
        )
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

    //Description String
    var taskDescription by remember { mutableStateOf("") }

    //Calendar
    val calendarState = rememberSheetState()
    var selectedDate by remember {mutableStateOf(LocalDate.now())}

    //Time
    val clockState = rememberSheetState()
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
                            .fillMaxWidth()
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
                            text = selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        )
                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            tint = Color(0xFF5F95FF),
                            contentDescription = "",
                            modifier = iconModifier
                                .clickable {calendarState.show()}
                        )
                    }

                    //Edit Due Time Of Task
                    EditableLabel(
                        label = "Due Time",
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
                                .padding(vertical = 10.dp, horizontal = 20.dp)
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