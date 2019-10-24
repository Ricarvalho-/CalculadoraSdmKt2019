package br.edu.ifsp.scl.calculadorasdmkt.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.edu.ifsp.scl.calculadorasdmkt.R
import br.edu.ifsp.scl.calculadorasdmkt.model.Configuracao
import br.edu.ifsp.scl.calculadorasdmkt.model.Separador
import br.edu.ifsp.scl.calculadorasdmkt.utils.AdvancedOperator
import br.edu.ifsp.scl.calculadorasdmkt.utils.Calculadora
import kotlinx.android.synthetic.main.fragment_calculadora_avancada.*

class CalculadoraAvancadaFragment: CalculadoraBasicaFragment() {
    companion object {
        private const val separatorKey = "separator"

        fun getInstance(setup: Configuracao): CalculadoraAvancadaFragment {
            val instance = CalculadoraAvancadaFragment()

            val args = Bundle()
            args.putSerializable(separatorKey, setup.separador)
            instance.arguments = args

            return instance
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calculadora_avancada, container, false)
    }
    override fun onClick(p: View?) {
        super.onClick(p)
        when (p) {
            cBt -> operatorClick(AdvancedOperator.CLEAR)
            ceBt -> operatorClick(AdvancedOperator.CANCEL_ENTRY)
            porcentageBt -> operatorClick(AdvancedOperator.PERCENT)
            raizBt -> operatorClick(AdvancedOperator.SQUARE_ROOT)
        }
    }

    private fun operatorClick(operator: AdvancedOperator) {
        val text = lcdTv.text.toString()
        val value = when (currentSeparator()) {
            Separador.VIRGULA -> text.replace(",", ".").toFloat()
            Separador.PONTO -> text.toFloat()
        }
        val result = Calculadora.calculate(value, operator).toString()
        lcdTv.text = when (currentSeparator()) {
            Separador.VIRGULA -> result.replace(".", ",")
            Separador.PONTO -> result
        }
        concatenaLcd = false
    }
}