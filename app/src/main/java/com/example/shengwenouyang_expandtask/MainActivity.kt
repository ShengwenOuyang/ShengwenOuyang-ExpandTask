package com.example.shengwenouyang_expandtask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shengwenouyang_expandtask.ui.theme.ShengwenOuyangExpandTaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShengwenOuyangExpandTaskTheme {
                TaskManagerApp()
            }
        }
    }
}

@Composable
fun TaskManagerApp() {
    var taskDescription by remember { mutableStateOf("") }
    var taskList by remember { mutableStateOf(mutableListOf<Task>()) }

    Column(modifier = Modifier.padding(16.dp)) {
        // TextField to input task
        TextField(
            value = taskDescription,
            onValueChange = { taskDescription = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            label = { Text("Task Description") }
        )

        // Button to add task
        Button(onClick = {
            if (taskDescription.isNotEmpty()) {
                taskList.add(Task(taskDescription))
                taskDescription = ""
            }
        }) {
            Text("Add Task")
        }

        // Display list of tasks
        Column {
            taskList.forEachIndexed { index, task ->
                TaskRow(
                    task = task,
                    onCheckedChange = { isChecked ->
                        // Create a new list and update task
                        taskList = taskList.toMutableList().apply {
                            this[index] = task.copy(isCompleted = isChecked)
                        }
                    }
                )
            }
        }

        // Button to clear completed tasks
        Button(onClick = {
            taskList = taskList.filter { !it.isCompleted }.toMutableList()
        }, modifier = Modifier.padding(top = 16.dp)) {
            Text("Clear Completed")
        }
    }
}

@Composable
fun TaskRow(task: Task, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = task.description,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = { onCheckedChange(it) }
        )
    }
}

data class Task(val description: String, val isCompleted: Boolean = false)

@Preview(showBackground = true)
@Composable
fun TaskManagerAppPreview() {
    ShengwenOuyangExpandTaskTheme {
        TaskManagerApp()
    }
}
