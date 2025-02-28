package br.edu.ifsp.scl.calculadorasdmkt.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.get
import androidx.fragment.app.Fragment
import br.edu.ifsp.scl.calculadorasdmkt.R
import br.edu.ifsp.scl.calculadorasdmkt.model.Configuracao
import br.edu.ifsp.scl.calculadorasdmkt.model.Separador
import br.edu.ifsp.scl.calculadorasdmkt.utils.Calculadora
import br.edu.ifsp.scl.calculadorasdmkt.utils.Operador
import kotlinx.android.synthetic.main.fragment_calculadora_basica.*

open class CalculadoraBasicaFragment: Fragment(), View.OnClickListener {
    var concatenaLcd: Boolean = true

    companion object {
        private const val separatorKey = "separator"

        fun getInstance(setup: Configuracao): CalculadoraBasicaFragment {
            val instance = CalculadoraBasicaFragment()

            val args = Bundle()
            args.putSerializable(separatorKey, setup.separador)
            instance.arguments = args

            return instance
        }
    }

    protected fun currentSeparator() = arguments?.getSerializable(separatorKey) as Separador

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calculadora_basica, container, false)
    }

    override fun onClick(p: View?) {
        when (p) {
            // Números
            umBt, doisBt, tresBt, quatroBt, cincoBt,
            seisBt, seteBt, oitoBt, noveBt, zeroBt -> {
                // Limpa LCD se último clicado foi um operador
                if (!concatenaLcd) {
                    lcdTv.text = ""
                }
                lcdTv.append((p as Button).text.toString())
                concatenaLcd = true
            }
            // Ponto
            pontoBt -> {
                val separator = pontoBt.text.toString()
                if (!lcdTv.text.toString().contains(separator)){
                    if (!concatenaLcd) {
                        lcdTv.text = "0"
                    }
                    lcdTv.append(separator)
                    concatenaLcd = true
                }
            }
            // Operadores
            adicaoBt -> cliqueOperador(Operador.ADICAO)
            subtracaoBt -> cliqueOperador(Operador.SUBTRACAO)
            multiplicacaoBt -> cliqueOperador(Operador.MULTIPLICACAO)
            divisaoBt -> cliqueOperador(Operador.DIVISAO)
            resultadoBt -> cliqueOperador(Operador.RESULTADO)
        }
    }

    fun cliqueOperador(operador: Operador) {
        val text = lcdTv.text.toString()
        val value = when (currentSeparator()) {
            Separador.VIRGULA -> text.replace(",", ".").toFloat()
            Separador.PONTO -> text.toFloat()
        }
        val result = Calculadora.calcula(value, operador).toString()
        lcdTv.text = when (currentSeparator()) {
            Separador.VIRGULA -> result.replace(".", ",")
            Separador.PONTO -> result
        }
        concatenaLcd = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (i in 0..(view as ViewGroup).childCount - 1) {
            val v: View = view.get(i)
            if (v is Button) {
                v.setOnClickListener(::onClick)
            }
        }

        pontoBt.text = when (currentSeparator()) {
            Separador.VIRGULA -> ","
            Separador.PONTO -> "."
        }
    }

}