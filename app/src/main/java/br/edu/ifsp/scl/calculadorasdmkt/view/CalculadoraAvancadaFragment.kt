package br.edu.ifsp.scl.calculadorasdmkt.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.edu.ifsp.scl.calculadorasdmkt.R
import br.edu.ifsp.scl.calculadorasdmkt.model.Configuracao

class CalculadoraAvancadaFragment: Fragment() {
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
}