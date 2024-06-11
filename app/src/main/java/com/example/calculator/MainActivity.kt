package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculator.ui.theme.CalculatorTheme
import java.lang.Exception

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculatorScreen(
                        modifier = Modifier.padding(innerPadding),
                        updateDisplayText = { text -> displayText = text }
                    )
                }
            }
        }
    }
}

var displayText by mutableStateOf("0")

@Composable
fun CalculatorScreen(modifier: Modifier = Modifier, updateDisplayText: (String) -> Unit) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = displayText,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row {
            CalculatorButton(text = "7") { onButtonClicked("7", displayText, updateDisplayText) }
            CalculatorButton(text = "8") { onButtonClicked("8", displayText, updateDisplayText) }
            CalculatorButton(text = "9") { onButtonClicked("9", displayText, updateDisplayText) }
            CalculatorButton(text = "/") { onButtonClicked("/", displayText, updateDisplayText) }
        }
        Row {
            CalculatorButton(text = "4") { onButtonClicked("4", displayText, updateDisplayText) }
            CalculatorButton(text = "5") { onButtonClicked("5", displayText, updateDisplayText) }
            CalculatorButton(text = "6") { onButtonClicked("6", displayText, updateDisplayText) }
            CalculatorButton(text = "*") { onButtonClicked("*", displayText, updateDisplayText) }
        }
        Row {
            CalculatorButton(text = "1") { onButtonClicked("1", displayText, updateDisplayText) }
            CalculatorButton(text = "2") { onButtonClicked("2", displayText, updateDisplayText) }
            CalculatorButton(text = "3") { onButtonClicked("3", displayText, updateDisplayText) }
            CalculatorButton(text = "-") { onButtonClicked("-", displayText, updateDisplayText) }
        }
        Row {
            CalculatorButton(text = ".") { onButtonClicked(".", displayText, updateDisplayText) }
            CalculatorButton(text = "0") { onButtonClicked("0", displayText, updateDisplayText) }
            CalculatorButton(text = "=") { onEqualsClicked(displayText, updateDisplayText) }
            CalculatorButton(text = "+") { onButtonClicked("+", displayText, updateDisplayText) }
        }
        Row(Modifier.weight(1f)) {
            CalculatorButton(text = "Clear", modifier = Modifier.weight(1f)) {
                updateDisplayText("0")
            }
        }
    }
}

@Composable
fun CalculatorButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(text = text)
    }
}

fun onButtonClicked(value: String, currentText: String, updateDisplayText: (String) -> Unit) {
    try {
        val newText = if (currentText == "0") value else currentText + value
        updateDisplayText(newText)
    } catch (e: Exception) {
        // Handle error
    }
}

fun onEqualsClicked(currentText: String, updateDisplayText: (String) -> Unit) {
    try {
        val result = evaluateExpression(currentText)
        updateDisplayText(result.toString())
    } catch (e: Exception) {
        updateDisplayText("Error")
    }
}

fun evaluateExpression(expression: String): Double {
    // Split the expression by arithmetic operators
    val parts = expression.split(Regex("[+\\-*/]"))
    val operator = expression.find { it in "+-*/" }
    if (parts.size != 2 || operator == null) {
        throw IllegalArgumentException("Invalid expression")
    }
    val operand1 = parts[0].toDouble()
    val operand2 = parts[1].toDouble()
    return when (operator) {
        '+' -> operand1 + operand2
        '-' -> operand1 - operand2
        '*' -> operand1 * operand2
        '/' -> operand1 / operand2
        else -> throw IllegalArgumentException("Invalid operator")
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalculatorTheme {
        CalculatorScreen { }
    }
}
