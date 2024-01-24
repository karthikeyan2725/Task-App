package com.example.taskapp.presentation

import android.app.ActivityManager.TaskDescription
import android.graphics.Outline
import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.taskapp.data.entity.Task
import com.example.taskapp.domain.UserViewModel
import com.example.taskapp.domain.viewTag
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

const val taskPageTag = "taskApp:taskPage"
@Composable
fun TaskPage(
    userViewModel: UserViewModel,
    taskButtonClick : ()->Unit
) {
    val tasks = userViewModel.tasks.collectAsState().value

    val userState = userViewModel.userState.collectAsState().value
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(-1f)
    ){
        drawCircle(
            color = Color(0xFF5F95FF),
            center = Offset(size.width/4 * 3, 0F),
            radius = size.width
        )
    }

    Column {
        WelcomeRow(name = userState.name.toString())
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .weight(2f)
        ){
            items(tasks){task->
                TaskRow(task,userViewModel)
            }
        }
        AddTask({taskButtonClick()})
        AddTask({taskButtonClick()})
        //TempAdd(userViewModel = userViewModel)
    }
}

@Composable
fun TaskRow(task: Task,userViewModel: UserViewModel){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomRadioButton({})
        TaskDate(task=task)
        TaskCard(task = task, userViewModel )
    }
}

@Composable
fun CustomRadioButton(
    onClick:()->Unit
){
    var radioSelected by remember{ mutableStateOf(false) }
    Row(
        modifier = Modifier
            .padding(10.dp)
    ){

        Canvas(
            modifier = Modifier
                .size(12.dp)
                .clickable { radioSelected = !radioSelected }
        ){


            drawCircle(
                color = Color.Black,
                style = Stroke(1.dp.toPx())
            )

            drawCircle(
                color=Color.White,
                center=center,
            )
            if(radioSelected){
                drawCircle(
                    color=Color.Black,
                    radius = 4.dp.toPx(),
                    center=center,

                )
            }
        }
    }
}

@Composable
fun WelcomeRow(name:String){
    Row(
        modifier = Modifier.padding(8.dp)
    ){
        Column{
            Text("Hello,",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 24.sp,

                ),
            )
            Text(name,
                style = TextStyle(
                    color = Color.White,
                    fontSize=30.sp
                )
            )
        }
    }
}

@Composable
fun TaskCard(task:Task,userViewModel: UserViewModel){
    Card(
        colors= CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp  ),
        modifier = Modifier.padding(8.dp)
    ) {

        //Description Text
        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(text = task.description,
                fontSize = 16.sp,
                color = Color(0xFF676767),
                fontWeight = FontWeight(600)
            )
        }

        //Time and Delete
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                task.dueDate.format(DateTimeFormatter.ofPattern("KK:mm a")),
                color = Color(0xFFA09B9B)
            )

            Icon(
                Icons.Filled.Delete,
                contentDescription = null,
                modifier = Modifier.
                        clickable { userViewModel.removeTask(task.tid) }
            )

        }
    }
}

@Composable
fun TaskDate(task:Task){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        //Day
        Text(text=task.dueDate.format(DateTimeFormatter.ofPattern("dd")).toString(),
            style = TextStyle(
                fontWeight= FontWeight(600),
                fontSize = 20.sp,
                //shadow = Shadow(color= Color.Black, offset = Offset(0F,10F), blurRadius = 36F)
            ))
        //Month
        Text(text=task.dueDate.month.toString().slice(0..2),
            style = TextStyle(
                fontWeight= FontWeight(600),
                fontSize = 20.sp,
                //shadow = Shadow(color= Color.Black, offset = Offset(0F,10F), blurRadius = 36F)
            )
        )

    }
}



@Composable
fun AddTask(onClick: () -> Unit){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier  = Modifier.fillMaxWidth()
    ){
        Button(
            onClick = {onClick()},
            colors = ButtonDefaults.buttonColors(Color(0xFF5F95FF)),
            shape = RectangleShape,
            modifier= Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 10.dp)
        ){
            Text("Add Task")
        }
    }
}


@Composable
fun TempAdd(userViewModel:UserViewModel){
    var taskString by remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        value = taskString,
        onValueChange = {taskString=it},
        Modifier.background(Color.White)
    )
    AddTaskButton(userViewModel , taskString  )
}

@Composable
fun AddTaskButton(userViewModel:UserViewModel,taskString:String){

    Button(onClick = {
        Log.d(taskPageTag,"Actual task button called")
        userViewModel.addTask(LocalDateTime.ofEpochSecond(0,0, ZoneOffset.UTC),taskString)
    }) {
        Text("Add Task")
    }
}