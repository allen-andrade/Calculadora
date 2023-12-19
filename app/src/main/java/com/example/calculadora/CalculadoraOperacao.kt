package com.example.calculadora

sealed class CalculadoraOperacao(val symbol: String) {
    object Add: CalculadoraOperacao("+")
    object Subtract: CalculadoraOperacao("-")
    object Multiply: CalculadoraOperacao("\u00D7")
    object Divide: CalculadoraOperacao("\u00F7")
}
