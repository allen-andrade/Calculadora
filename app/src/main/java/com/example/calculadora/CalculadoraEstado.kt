package com.example.calculadora

data class CalculadoraEstado(
    val number1: String = "",
    val number2: String = "",
    val operation: CalculadoraOperacao? = null
)
