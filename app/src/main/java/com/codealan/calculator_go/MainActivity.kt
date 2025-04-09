package com.codealan.calculator_go

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codealan.calculator_go.ui.theme.Calculator_GoTheme
import calculator.Calculator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Calculator_GoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen() {
    var input by remember { mutableStateOf("0") }
    var operation by remember { mutableStateOf("") }
    var firstNumber by remember { mutableStateOf(0.0) }
    var clearOnNextDigit by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Display
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = input,
                fontSize = 36.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp),
                textAlign = TextAlign.End
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Calculator buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton(text = "C", modifier = Modifier.weight(1f)) {
                input = "0"
                operation = ""
                firstNumber = 0.0
            }
            CalculatorButton(text = "+/-", modifier = Modifier.weight(1f)) {
                if (input != "0") {
                    input = if (input.startsWith("-")) input.substring(1) else "-$input"
                }
            }
            CalculatorButton(text = "%", modifier = Modifier.weight(1f)) {
                val value = input.toDoubleOrNull() ?: 0.0
                input = (value / 100).toString()
            }
            OperationButton(text = "÷", modifier = Modifier.weight(1f)) {
                handleOperation("/", input, operation, firstNumber) { num1, num2 ->
                    Calculator.divide(num1, num2)
                }.also { result ->
                    firstNumber = result.first
                    operation = result.second
                    input = result.third
                    clearOnNextDigit = true
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NumberButton(text = "7", modifier = Modifier.weight(1f)) {
                input = updateInput(input, "7", clearOnNextDigit)
                clearOnNextDigit = false
            }
            NumberButton(text = "8", modifier = Modifier.weight(1f)) {
                input = updateInput(input, "8", clearOnNextDigit)
                clearOnNextDigit = false
            }
            NumberButton(text = "9", modifier = Modifier.weight(1f)) {
                input = updateInput(input, "9", clearOnNextDigit)
                clearOnNextDigit = false
            }
            OperationButton(text = "×", modifier = Modifier.weight(1f)) {
                handleOperation("*", input, operation, firstNumber) { num1, num2 ->
                    Calculator.multiply(num1, num2)
                }.also { result ->
                    firstNumber = result.first
                    operation = result.second
                    input = result.third
                    clearOnNextDigit = true
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NumberButton(text = "4", modifier = Modifier.weight(1f)) {
                input = updateInput(input, "4", clearOnNextDigit)
                clearOnNextDigit = false
            }
            NumberButton(text = "5", modifier = Modifier.weight(1f)) {
                input = updateInput(input, "5", clearOnNextDigit)
                clearOnNextDigit = false
            }
            NumberButton(text = "6", modifier = Modifier.weight(1f)) {
                input = updateInput(input, "6", clearOnNextDigit)
                clearOnNextDigit = false
            }
            OperationButton(text = "-", modifier = Modifier.weight(1f)) {
                handleOperation("-", input, operation, firstNumber) { num1, num2 ->
                    Calculator.subtract(num1, num2)
                }.also { result ->
                    firstNumber = result.first
                    operation = result.second
                    input = result.third
                    clearOnNextDigit = true
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NumberButton(text = "1", modifier = Modifier.weight(1f)) {
                input = updateInput(input, "1", clearOnNextDigit)
                clearOnNextDigit = false
            }
            NumberButton(text = "2", modifier = Modifier.weight(1f)) {
                input = updateInput(input, "2", clearOnNextDigit)
                clearOnNextDigit = false
            }
            NumberButton(text = "3", modifier = Modifier.weight(1f)) {
                input = updateInput(input, "3", clearOnNextDigit)
                clearOnNextDigit = false
            }
            OperationButton(text = "+", modifier = Modifier.weight(1f)) {
                handleOperation("+", input, operation, firstNumber) { num1, num2 ->
                    Calculator.add(num1, num2)
                }.also { result ->
                    firstNumber = result.first
                    operation = result.second
                    input = result.third
                    clearOnNextDigit = true
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NumberButton(text = "0", modifier = Modifier.weight(2f)) {
                input = updateInput(input, "0", clearOnNextDigit)
                clearOnNextDigit = false
            }
            NumberButton(text = ".", modifier = Modifier.weight(1f)) {
                if (!input.contains(".")) {
                    input += "."
                }
            }
            OperationButton(text = "=", modifier = Modifier.weight(1f)) {
                if (operation.isNotEmpty()) {
                    val secondNumber = input.toDoubleOrNull() ?: 0.0
                    val result = when (operation) {
                        "+" -> Calculator.add(firstNumber, secondNumber)
                        "-" -> Calculator.subtract(firstNumber, secondNumber)
                        "*" -> Calculator.multiply(firstNumber, secondNumber)
                        "/" -> Calculator.divide(firstNumber, secondNumber)
                        else -> secondNumber
                    }
                    input = formatResult(result)
                    operation = ""
                    firstNumber = 0.0
                    clearOnNextDigit = true
                }
            }
        }
    }
}

fun updateInput(currentInput: String, digit: String, clear: Boolean): String {
    return when {
        clear -> digit
        currentInput == "0" -> digit
        else -> currentInput + digit
    }
}

fun formatResult(result: Double): String {
    val intValue = result.toInt()
    return if (result == intValue.toDouble()) {
        intValue.toString()
    } else {
        String.format("%.2f", result).trimEnd('0').trimEnd('.')
    }
}

fun handleOperation(
    newOp: String,
    currentInput: String,
    currentOp: String,
    firstNum: Double,
    calculate: (Double, Double) -> Double
): Triple<Double, String, String> {
    val inputNum = currentInput.toDoubleOrNull() ?: 0.0
    
    return when {
        currentOp.isEmpty() -> {
            // No previous operation, just store the number and operation
            Triple(inputNum, newOp, currentInput)
        }
        else -> {
            // Perform the calculation with the previous operation
            val result = when (currentOp) {
                "+" -> Calculator.add(firstNum, inputNum)
                "-" -> Calculator.subtract(firstNum, inputNum)
                "*" -> Calculator.multiply(firstNum, inputNum)
                "/" -> Calculator.divide(firstNum, inputNum)
                else -> inputNum
            }
            val formattedResult = formatResult(result)
            Triple(result, newOp, formattedResult)
        }
    }
}

@Composable
fun NumberButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun OperationButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun CalculatorButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondary)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    Calculator_GoTheme {
        CalculatorScreen()
    }
}