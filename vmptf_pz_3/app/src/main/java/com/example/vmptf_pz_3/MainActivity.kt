package com.example.vmptf_pz_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vmptf_pz_3.ui.theme.Vmptf_pz_3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Vmptf_pz_3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )

                    Calculator()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Vmptf_pz_3Theme {
        Greeting("Android")
    }
}

@Composable
fun Calculator() {
    var number1 by remember { mutableStateOf(TextFieldValue("")) }
    var number2 by remember { mutableStateOf(TextFieldValue("")) }
    var result by remember { mutableStateOf("") }
    var operation by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = number1,
            onValueChange = { number1 = it },
            label = { Text("Number 1") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = number2,
            onValueChange = { number2 = it },
            label = { Text("Number 2") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                operation = "+"
                result = calculateResult(number1.text, number2.text, operation)
            }) {
                Text("+")
            }
            Button(onClick = {
                operation = "-"
                result = calculateResult(number1.text, number2.text, operation)
            }) {
                Text("-")
            }
            Button(onClick = {
                operation = "*"
                result = calculateResult(number1.text, number2.text, operation)
            }) {
                Text("*")
            }
            Button(onClick = {
                operation = "/"
                result = calculateResult(number1.text, number2.text, operation)
            }) {
                Text("/")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Result: $result",
            fontSize = 24.sp,
            modifier = Modifier.padding(8.dp)
        )
    }
}

fun calculateResult(number1: String, number2: String, operation: String): String {
    val num1 = number1.toDoubleOrNull()
    val num2 = number2.toDoubleOrNull()

    if (num1 == null || num2 == null) {
        return "Invalid input"
    }

    return when (operation) {
        "+" -> (num1 + num2).toString()
        "-" -> (num1 - num2).toString()
        "*" -> (num1 * num2).toString()
        "/" -> {
            if (num2 != 0.0) {
                (num1 / num2).toString()
            } else {
                "Cannot divide by zero"
            }
        }
        else -> "Unknown operation"
    }
}