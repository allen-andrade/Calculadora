package com.example.calculadora

import android.icu.text.RelativeDateTimeFormatter.RelativeDateTimeUnit
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculadoraViewModel : ViewModel() {

    var state by mutableStateOf(CalculadoraEstado())
        private set

    fun onAction(action: CalculadoraAcao) {
        when (action) {
            is CalculadoraAcao.Number -> enterNumber(action.number)
            is CalculadoraAcao.Decimal -> enterDecimal()
            is CalculadoraAcao.Clear -> state = CalculadoraEstado()
            is CalculadoraAcao.Operation -> enterOperation(action.operation)
            is CalculadoraAcao.Calculate -> performCalculation()
            is CalculadoraAcao.Delete -> performDeletion()
        }
    }

    private fun performDeletion() {
        when {
            state.number2.isNotBlank() -> state = state.copy(
                number2 = state.number2.dropLast(1)
            )

            state.operation != null -> state = state.copy(
                operation = null
            )

            state.number1.isNotBlank() -> state = state.copy(
                number1 = state.number1.dropLast(1)
            )
        }
    }

    private fun performCalculation() {
        val number1 = state.number1.toDoubleOrNull()
        val number2 = state.number2.toDoubleOrNull()
        if (number1 != null && number2 != null) {
            val result = when (state.operation) {
                is CalculadoraOperacao.Add -> number1 + number2
                is CalculadoraOperacao.Subtract -> number1 - number2
                is CalculadoraOperacao.Multiply -> number1 * number2
                is CalculadoraOperacao.Divide -> number1 / number2
                null -> return
            }
            state = state.copy(
                number1 = result.toString().take(15),
                number2 = "",
                operation = null
            )
        }
    }

    private fun enterOperation(operation: CalculadoraOperacao) {
        if (state.number1.isNotBlank()) {
            state = state.copy(operation = operation)
        }
    }

    private fun enterDecimal() {
        if (state.operation == null && !state.number1.contains(".")
            && state.number1.isNotBlank()
        ) {
            state = state.copy(
                number1 = state.number1 + "."
            )
            return
        }
        if (!state.number2.contains(".")
            && state.number2.isNotBlank()
        ) {
            state = state.copy(
                number1 = state.number2 + "."
            )
            return
        }
    }

    private fun enterNumber(number: Int) {
        if (state.operation == null) {
            if (state.number1.length >= MAX_NUM_LENGTH) {
                return
            }
            state = state.copy(
                number1 = state.number1 + number
            )
            return
        }
        if (state.number2.length >= MAX_NUM_LENGTH) {
            return
        }
        state = state.copy(
            number2 = state.number2 + number
        )
    }

    companion object {
        private const val MAX_NUM_LENGTH = 8
    }
}