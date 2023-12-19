package com.example.calculadora

sealed class CalculadoraAcao {
    data class Number(val number: Int): CalculadoraAcao()
    object Clear: CalculadoraAcao()
    object Delete: CalculadoraAcao()
    object Decimal: CalculadoraAcao()
    object Calculate: CalculadoraAcao()
    data class Operation(val operation: CalculadoraOperacao): CalculadoraAcao()
}
