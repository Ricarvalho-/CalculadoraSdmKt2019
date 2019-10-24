package br.edu.ifsp.scl.calculadorasdmkt.utils

import kotlin.math.sqrt

/* Classe de enumeração para constantes de operadores */
enum class Operador {
    RESULTADO, ADICAO, SUBTRACAO, MULTIPLICACAO, DIVISAO
}

enum class AdvancedOperator {
    CLEAR, CANCEL_ENTRY, PERCENT, SQUARE_ROOT
}

/* Singleton que calcula operações aritméticas básicas */
object Calculadora {
    // primeiro operando
    var operando: Float = 0.0f

    var isClear = true

    // operador que será aplicado entre primeiro e segundo operando
    var operador: Operador =
        Operador.RESULTADO

    /* calcula um valor de retorno com base no operando e operador já existentes, novo valor
     e atualiza valor de operando e operador */
    fun calcula(valor: Float, operador: Operador): Float {
        when (Calculadora.operador) {
            Operador.RESULTADO -> operando = valor
            Operador.ADICAO -> operando += valor
            Operador.SUBTRACAO -> operando -= valor
            Operador.MULTIPLICACAO -> operando *= valor
            Operador.DIVISAO -> operando /= valor
        }
        Calculadora.operador = operador
        isClear = false
        return operando
    }

    fun calculate(currentValue: Float, operator: AdvancedOperator): Float {
        return when (operator) {
            AdvancedOperator.CLEAR -> {
                operando = 0f
                operador = Operador.RESULTADO
                isClear = true
                0f
            }
            AdvancedOperator.CANCEL_ENTRY -> 0f
            AdvancedOperator.PERCENT -> when {
                isClear -> currentValue.percent()
                else -> currentValue percentOf operando
            }
            AdvancedOperator.SQUARE_ROOT -> sqrt(currentValue)
        }
    }
}

fun Float.percent() = this / 100
infix fun Float.percentOf(value: Float) = value * this.percent()