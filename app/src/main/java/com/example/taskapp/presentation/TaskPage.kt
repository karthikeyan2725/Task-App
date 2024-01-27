package com.example.taskapp.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskapp.data.entity.Task
import com.example.taskapp.domain.UserViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val taskPageTag = "taskApp:taskPage"
@Composable
fun TaskPage(
    userViewModel: UserViewModel,
    navigateOnClick: ()->Unit,
    navigateOnClickOfCard: () -> Unit
) {

    //States and collections
    val tasks = userViewModel.tasks.collectAsState().value
    val userState = userViewModel.userState.collectAsState().value
    val userName = userState.name.toString()

    //Styling Variables
    val bluishColor = Color(0xFF5F95FF)
    val circleCenterFraction  = 4F/ 3F

    Column(
        //Background blue Circle
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawCircle(
                    color = bluishColor,
                    center = Offset(size.width / circleCenterFraction, 0F),
                    radius = size.width
                )
            }
    ){

        //Hello-Name Row
        HelloUserRow(userName)

        //Tasks List
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .weight(2f)
        ){
            items(tasks){task->
                // A single Task row with done, Date and TaskCard
                TaskRow(task,userViewModel,navigateOnClickOfCard)
            }
        }

        //Add Task Button to AddTaskPage
        AddTaskButtonRow(
            navigateOnClick = navigateOnClick,
            buttonColor = bluishColor,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
















/**
 * Adds A Task Row With RadioButton, Date and A Card With Information
 */
@Composable
fun TaskRow(task: Task, userViewModel: UserViewModel,navigateClick:()->Unit){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        //States
        var isTaskCompleted by remember { mutableStateOf(task.done) }

        TaskRadioButton(
            isTaskCompleted  = task.done,
            onClick = {
                    isTaskCompleted = !isTaskCompleted
                    userViewModel.updateTask(task.copy(done = isTaskCompleted))
            },
            modifier = Modifier
                .padding(10.dp)
                .size(15.dp)
        )

        TaskDate(
            dueDate = task.dueDate
        )

        TaskCard(
            task = task,
            userViewModel = userViewModel,
            cardColor = Color.White,
            onClick = {navigateClick()}
        )
    }
}


/**
 * A Custom RadioButton that takes the state isTaskCompleted.
 * The primaryColor applied to Stroke and inner circle.
 * The secondaryColor is the fill colour.
 */
@Composable
fun TaskRadioButton(
    isTaskCompleted:Boolean,
    onClick:()->Unit,
    modifier: Modifier = Modifier,
    primaryColor : Color = Color.Black,
    secondaryColor : Color = Color.White
    ){

        Canvas(
            modifier = modifier
                .clickable {
                    onClick()
                }
        ){

            //Circle's Stroke
            drawCircle(
                color = primaryColor,
                style = Stroke(size.width/10)
            )

            //Circle Fill
            drawCircle(
                color=secondaryColor,
                center=center,
            )

            //inner Circle
            if(isTaskCompleted){
                drawCircle(
                    color=primaryColor,
                    radius = size.width / 3 ,
                    center=center,
                )
            }
        }

}

@Composable
fun HelloUserRow(name:String){
    Row(
        modifier = Modifier.padding(8.dp)
    ){
        Column{
            Text(
                "Hello,",
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    fontSize = 24.sp,

                ),
            )
            Text(
                name,
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize=30.sp
                )
            )
        }
    }
}

/**
 * A Simple Card with description, time and delete button.
 * Adds "!" to show due time
 */
@Composable
fun TaskCard(
    task:Task,
    userViewModel: UserViewModel,
    cardColor : Color,
    textStyleNormal: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight(600)
    ),
    textStyleCompleted: TextStyle = TextStyle(
        fontSize = 16.sp,
        color = Color.Gray,
        fontWeight = FontWeight(500),
        textDecoration = TextDecoration.LineThrough
    ),
    onClick :()->Unit,
){
    val taskDueIn2Days by remember {
        mutableStateOf(isTaskWithin(2, task.dueDate))
    }
    val dateColor = when(isTaskWithin(2, task.dueDate) ){
        true-> Color(0xFFA09B9B)
        false-> when(task.done){
            true -> Color(0xFFA09B9B)
            false -> Color.Red
        }
    }
    val grayishColor = Color(0xFF676767)
    val rowModifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
    Card(
        colors= CardDefaults
            .cardColors(containerColor = cardColor),
        elevation = CardDefaults
            .cardElevation(defaultElevation = 10.dp),
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        //1) Description Text and !
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = rowModifier
        ) {

            // Description Text
            Text(
                text = task.description,
                style = if (task.done) textStyleCompleted else textStyleNormal
            )

            // Task Due ! alert in red
            if( !taskDueIn2Days and !task.done ) {
                Text(
                    text = "!",
                    color = Color.Red,
                    style = textStyleNormal,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                )
            }
        }

        //2) Time and Delete
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = rowModifier
        ) {
            //Time
            Text(
                task.dueDate.format(DateTimeFormatter.ofPattern("KK:mm a")),
                color = dateColor
            )

            //Delete button for task
            Icon(
                Icons.Filled.Delete,
                contentDescription = null,
                modifier = Modifier.
                        clickable { userViewModel.removeTask(task.tid) }
            )

        }
    }
}

/**
 * A Column which Shows the Day And Month Of the given LocalDateTime
 */
@Composable
fun TaskDate(
    dueDate:LocalDateTime,
    dateTextStyle: TextStyle = TextStyle(
        fontWeight = FontWeight(600),
        fontSize = 20.sp
    )
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        //Day
        Text(
            text=dueDate
                .format(
                    DateTimeFormatter
                        .ofPattern("dd")
                )
                .toString(),
            style = dateTextStyle
        )

        //Month
        Text(
            text=dueDate
                .format(
                    DateTimeFormatter
                        .ofPattern("MMM")
                )
                .toString(),
            style = dateTextStyle
        )

    }
}


/**
 * A Rectangular button that navigates to pages given by navigateOnClick lambda function
 */
@Composable
fun AddTaskButtonRow(
    navigateOnClick: () -> Unit,
    buttonColor : Color,
    modifier :Modifier = Modifier
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ){
        Button(
            onClick = navigateOnClick,
            colors = ButtonDefaults.buttonColors(buttonColor),
            shape = RectangleShape,
            modifier= modifier
                .padding(
                    horizontal = 24.dp,
                    vertical = 10.dp
                )
        ){
            Text("Add Task")
        }
    }
}


/**
 * A Utility function that returns true if task is due in numDays
 */
fun isTaskWithin(
    numDays:Long,
    dueDateTime: LocalDateTime
): Boolean {
    val minDue = dueDateTime.minusDays(numDays)
    return minDue > LocalDateTime.now()
}