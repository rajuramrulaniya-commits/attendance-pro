
package com.attendance.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AttendanceApp()
            }
        }
    }
}

@Composable
fun AttendanceApp() {
    var employeeName by remember { mutableStateOf("") }
    var employeeId by remember { mutableStateOf("") }
    var loggedIn by remember { mutableStateOf(false) }
    var checkedIn by remember { mutableStateOf(false) }
    var status by remember { mutableStateOf("Absent") }
    var message by remember { mutableStateOf("Welcome to Attendance Pro") }
    var leaveReason by remember { mutableStateOf("") }
    var showLeave by remember { mutableStateOf(false) }
    var history by remember { mutableStateOf(listOf<String>()) }

    val time = {
        SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.getDefault())
            .format(Date())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "ATTENDANCE PRO",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(Modifier.height(20.dp))

        if (!loggedIn) {
            OutlinedTextField(
                value = employeeName,
                onValueChange = { employeeName = it },
                label = { Text("Employee Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = employeeId,
                onValueChange = { employeeId = it },
                label = { Text("Employee ID") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
            Button(
                onClick = {
                    if (employeeName.isNotBlank() &&
                        employeeId.isNotBlank()
                    ) {
                        loggedIn = true
                        message = "Welcome, $employeeName"
                    } else {
                        message = "Enter your name and employee ID"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }
        } else {
            Text("Employee: $employeeName")
            Text("Employee ID: $employeeId")
            Text("Status: $status")
            Spacer(Modifier.height(12.dp))

            Button(
                onClick = {
                    checkedIn = !checkedIn
                    status = if (checkedIn) "Present" else "Checked-out"
                    val action = if (checkedIn) "Check-in" else "Check-out"
                    history = history + "$action: ${time()}"
                    message = "$action successful"
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (checkedIn) "Check Out" else "Check In")
            }

            OutlinedButton(
                onClick = { message = history.joinToString("\n").ifBlank { "No attendance recorded yet" } },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("My Attendance")
            }

            OutlinedButton(
                onClick = { showLeave = !showLeave },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Apply Leave")
            }

            if (showLeave) {
                OutlinedTextField(
                    value = leaveReason,
                    onValueChange = { leaveReason = it },
                    label = { Text("Leave reason") },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(onClick = {
                    message = if (leaveReason.isNotBlank()) {
                        "Leave request prepared: $leaveReason"
                    } else {
                        "Please enter leave reason"
                    }
                }) {
                    Text("Submit Leave Request")
                }
            }

            OutlinedButton(
                onClick = {
                    loggedIn = false
                    checkedIn = false
                    status = "Absent"
                    message = "Logged out"
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Logout")
            }
        }

        Spacer(Modifier.height(20.dp))
        Text(message)
        Spacer(Modifier.height(12.dp))
        Text("Holiday Calendar: Coming soon")
    }
}
