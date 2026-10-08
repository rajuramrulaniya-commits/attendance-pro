package com.attendance.mobile
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity:ComponentActivity(){override fun onCreate(b:Bundle?){super.onCreate(b);setContent{App()}}}
@Composable fun App(){
 var inOffice by remember{mutableStateOf(false)};var status by remember{mutableStateOf("Absent")}
 MaterialTheme{Column(Modifier.padding(24.dp),verticalArrangement=Arrangement.spacedBy(14.dp)){
 Text("Attendance Pro",style=MaterialTheme.typography.headlineMedium)
 Text("Status: $status")
 Button(onClick={inOffice=!inOffice;status=if(inOffice)"Present":"Checked-out"},Modifier.fillMaxWidth()){Text(if(inOffice)"Check-out":"Face + GPS Check-in")}
 OutlinedButton(onClick={},Modifier.fillMaxWidth()){Text("My Attendance")}
 OutlinedButton(onClick={},Modifier.fillMaxWidth()){Text("Apply Leave")}
 OutlinedButton(onClick={},Modifier.fillMaxWidth()){Text("Holiday Calendar")}
 }}}
