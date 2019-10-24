package br.edu.ifsp.scl.calculadorasdmkt.utils

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CalculatorUnitTest {
    private infix fun Float.shouldBe(expectedValue: Float) = assertEquals(expectedValue, this)

    @Before
    fun reset() {
        Calculadora.operando = 0f
        Calculadora.operador = Operador.RESULTADO
        Calculadora.isClear = true
    }

    @Test
    fun subsequentAdditions() {
        // Keystrokes: 8 + 5.5 + 0 + 7 =
        Calculadora.calcula(8f, Operador.ADICAO) shouldBe 8f
        Calculadora.calcula(5.5f, Operador.ADICAO) shouldBe 13.5f
        Calculadora.calcula(0f, Operador.ADICAO) shouldBe 13.5f
        Calculadora.calcula(7f, Operador.RESULTADO) shouldBe 20.5f
    }

    @Test
    fun subsequentSubtractions() {
        // Keystrokes: 8 - 5.5 - 0 - 7 =
        Calculadora.calcula(8f, Operador.SUBTRACAO) shouldBe 8f
        Calculadora.calcula(5.5f, Operador.SUBTRACAO) shouldBe 2.5f
        Calculadora.calcula(0f, Operador.SUBTRACAO) shouldBe 2.5f
        Calculadora.calcula(7f, Operador.RESULTADO) shouldBe -4.5f
    }

    @Test
    fun additionAfterSubtraction() {
        // Keystrokes: 8 - 5.5 + 0 + 7 =
        Calculadora.calcula(8f, Operador.SUBTRACAO) shouldBe 8f
        Calculadora.calcula(5.5f, Operador.ADICAO) shouldBe 2.5f
        Calculadora.calcula(0f, Operador.ADICAO) shouldBe 2.5f
        Calculadora.calcula(7f, Operador.RESULTADO) shouldBe 9.5f
    }

    @Test
    fun subtractionAfterAddition() {
        // Keystrokes: 8 + 5.5 - 0 - 7 =
        Calculadora.calcula(8f, Operador.ADICAO) shouldBe 8f
        Calculadora.calcula(5.5f, Operador.SUBTRACAO) shouldBe 13.5f
        Calculadora.calcula(0f, Operador.SUBTRACAO) shouldBe 13.5f
        Calculadora.calcula(7f, Operador.RESULTADO) shouldBe 6.5f
    }

    @Test
    fun subsequentMultiplications() {
        // Keystrokes: 8 * 5 * 0.5 * 0 =
        Calculadora.calcula(8f, Operador.MULTIPLICACAO) shouldBe 8f
        Calculadora.calcula(5f, Operador.MULTIPLICACAO) shouldBe 40f
        Calculadora.calcula(0.5f, Operador.MULTIPLICACAO) shouldBe 20f
        Calculadora.calcula(0f, Operador.RESULTADO) shouldBe 0f
    }

    @Test
    fun subsequentDivisions() {
        // Keystrokes: 8 / 5 / 0.5 =
        Calculadora.calcula(8f, Operador.DIVISAO) shouldBe 8f
        Calculadora.calcula(5f, Operador.DIVISAO) shouldBe 1.6f
        Calculadora.calcula(0.5f, Operador.RESULTADO) shouldBe 3.2f
    }

    @Test
    fun percents() {
        5f.percent() shouldBe 0.05f
        250f.percent() shouldBe 2.5f
        (-80f).percent() shouldBe -0.8f

        25f percentOf 8f shouldBe 2f
        5f percentOf 100f shouldBe 5f
        10f percentOf 250f shouldBe 25f

        // Keystrokes: 8 % 5 %
        Calculadora.calculate(8f, AdvancedOperator.PERCENT) shouldBe 0.08f
        Calculadora.calculate(5f, AdvancedOperator.PERCENT) shouldBe 0.05f

        // Keystrokes: 8 + 25 % =
        Calculadora.calcula(8f, Operador.ADICAO) shouldBe 8f
        Calculadora.calculate(25f, AdvancedOperator.PERCENT) shouldBe 2f
        Calculadora.calcula(2f, Operador.RESULTADO) shouldBe 10f

        // Keystrokes: 8 + 25 % + 50 % =
        Calculadora.calcula(8f, Operador.ADICAO) shouldBe 8f
        Calculadora.calculate(25f, AdvancedOperator.PERCENT) shouldBe 2f
        Calculadora.calcula(2f, Operador.ADICAO) shouldBe 10f
        Calculadora.calculate(50f, AdvancedOperator.PERCENT) shouldBe 5f
        Calculadora.calcula(5f, Operador.RESULTADO) shouldBe 15f
    }

    @Test
    fun squareRoots() {
        // Keystrokes: 4 √ 9 √
        Calculadora.calculate(4f, AdvancedOperator.SQUARE_ROOT) shouldBe 2f
        Calculadora.calculate(9f, AdvancedOperator.SQUARE_ROOT) shouldBe 3f

        // Keystrokes: 10 + 9 √ + 2 =
        Calculadora.calcula(10f, Operador.ADICAO) shouldBe 10f
        Calculadora.calculate(9f, AdvancedOperator.SQUARE_ROOT) shouldBe 3f
        Calculadora.calcula(3f, Operador.ADICAO) shouldBe 13f
        Calculadora.calcula(2f, Operador.RESULTADO) shouldBe 15f

        // Keystrokes: 10 + 9 √ + 4 √ =
        Calculadora.calcula(10f, Operador.ADICAO) shouldBe 10f
        Calculadora.calculate(9f, AdvancedOperator.SQUARE_ROOT) shouldBe 3f
        Calculadora.calcula(3f, Operador.ADICAO) shouldBe 13f
        Calculadora.calculate(4f, AdvancedOperator.SQUARE_ROOT) shouldBe 2f
        Calculadora.calcula(2f, Operador.RESULTADO) shouldBe 15f
    }

    @Test
    fun cancelEntry() {
        // Keystrokes: 8 + 5.5 CE 14.3 - 4 CE 1 + 7 =
        Calculadora.calcula(8f, Operador.ADICAO) shouldBe 8f
        Calculadora.calculate(5.5f, AdvancedOperator.CANCEL_ENTRY) shouldBe 0f
        Calculadora.calcula(14.3f, Operador.SUBTRACAO) shouldBe 22.3f
        Calculadora.calculate(4f, AdvancedOperator.CANCEL_ENTRY) shouldBe 0f
        Calculadora.calcula(1f, Operador.ADICAO) shouldBe 21.3f
        Calculadora.calcula(7f, Operador.RESULTADO) shouldBe 28.3f
    }

    @Test
    fun clear() {
        // Keystrokes: 8 + 5.5 C 14.3 - 4 C 1 + 7 =
        Calculadora.calcula(8f, Operador.ADICAO) shouldBe 8f
        Calculadora.calculate(5.5f, AdvancedOperator.CLEAR) shouldBe 0f
        Calculadora.calcula(14.3f, Operador.SUBTRACAO) shouldBe 14.3f
        Calculadora.calculate(4f, AdvancedOperator.CLEAR) shouldBe 0f
        Calculadora.calcula(1f, Operador.ADICAO) shouldBe 1f
        Calculadora.calcula(7f, Operador.RESULTADO) shouldBe 8f
    }
}
